package esgi.ascl.license.infrastructure.web.controller;

import com.google.gson.Gson;
import com.stripe.model.*;
import esgi.ascl.User.domain.service.UserService;
import esgi.ascl.license.domain.entities.CheckoutPayment;
import esgi.ascl.license.domain.service.LicenseService;
import esgi.ascl.license.domain.mapper.LicenseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (license == null) return new ResponseEntity<>("", HttpStatus.OK);
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

    @PostMapping("webhook/payment-confirmation")
    public ResponseEntity<?> webhook(@RequestBody String stripeEvent){
        //commande stripe CLI : stripe trigger payment_intent.succeeded
        Event event = Event.GSON.fromJson(stripeEvent, Event.class);
        EventDataObjectDeserializer dataObjectDeserializer =  event.getDataObjectDeserializer();

        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
        if (paymentIntent == null) {
            System.out.println("Dans paymentIntent == null !");
            return new ResponseEntity<>("", HttpStatus.OK);
        }

        switch (event.getType()) {
            case "payment_intent.succeeded" -> {
                System.out.println("Dans payment_method.succeeded !");
                var user = userService.getByEmail(paymentIntent.getReceiptEmail());
                var license = licenseService.getByUserId(user.get().getId());
                if (license != null) {
                    licenseService.licenseExtension(license.getId());
                } else {
                    licenseService.create(user.get());
                }
            }
            case "payment_intent.payment_failed" -> {
                System.out.println("Dans payment_intent.payment_failed !");
                System.out.println("Payment intent : " + paymentIntent);
            }
            case "payment_method.attached" -> {
                System.out.println("Payment intent : " + paymentIntent);
                System.out.println("Dans payment_method.attached !");
            }
            default -> System.out.println("Unhandled event type: " + event.getType());
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }



    private ResponseEntity<?> userNotFound() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

}
