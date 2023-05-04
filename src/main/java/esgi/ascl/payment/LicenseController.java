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

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/license")
public class LicenseController {
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
    public ResponseEntity<?> paymentWithCheckoutPage(@RequestBody CheckoutPayment payment) {
        var user = userService.getById(payment.getUserId());
        if (user == null) return userNotFound();

        Map<String, String> responseData;
        try {
            responseData = licenseService.payment(payment, user);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(gson.toJson(responseData), HttpStatus.OK);
    }


    private ResponseEntity<?> userNotFound() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

}
