package esgi.ascl.payment;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import esgi.ascl.User.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/license")
public class LicenseController {

    @Value("${stripe.api.key}")
    private String stripeApiKey;
    private static final Gson gson = new Gson();
    private final LicenseService licenseService;
    private final UserService userService;

    public LicenseController(LicenseService licenseService, UserService userService) {
        this.licenseService = licenseService;
        this.userService = userService;
    }

    @PostMapping("create/{userId}")
    public ResponseEntity<?> create(@PathVariable Long userId) {
        var user = userService.getById(userId);
        if (user == null) return userNotFound();

        if (licenseService.getByUserId(userId) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already have a license");

        var license = licenseService.create(user);
        return ResponseEntity.ok().body(LicenseMapper.toResponse(license));
    }


    @GetMapping("{licenseId}")
    public ResponseEntity<?> getById(@PathVariable Long licenseId) {
        var license = licenseService.getById(licenseId);
        if (license.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(LicenseMapper.toResponse(license.get()));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        var license = licenseService.getByUserId(userId);
        if (license == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(LicenseMapper.toResponse(license));
    }

    @GetMapping("number/{number}")
    public ResponseEntity<?> getByNumber(@PathVariable Long number) {
        var license = licenseService.getByNumber(number);
        if (license == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(LicenseMapper.toResponse(license));
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        var licensesResponse = licenseService
                .getAll()
                .stream()
                .map(LicenseMapper::toResponse);
        return ResponseEntity.ok().body(licensesResponse);
    }

    @PostMapping("payment")
    public ResponseEntity<?> paymentWithCheckoutPage(@RequestBody CheckoutPayment payment) throws StripeException {
        var user = userService.getById(payment.getUserId());
        if (user == null) return userNotFound();

        Stripe.apiKey = stripeApiKey ;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setCustomerEmail(user.getEmail())
                .setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(payment.getSuccessUrl())
                .setCancelUrl(payment.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(payment.getQuantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
                                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                                        .builder().setName(payment.getName()).build())
                                                .build())
                                .build())
                .build();

        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());


        return new ResponseEntity<>(gson.toJson(responseData), HttpStatus.OK);
    }


    private ResponseEntity<?> userNotFound() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

}
