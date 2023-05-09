package esgi.ascl.license.domain.service;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import esgi.ascl.User.domain.entities.License;
import esgi.ascl.User.domain.entities.User;
import esgi.ascl.license.infrastructure.repositories.LicenseRepository;
import esgi.ascl.license.domain.entities.CheckoutPayment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LicenseService {
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    private static final Gson gson = new Gson();

    private final LicenseRepository licenseRepository;
    private final LicenseNumberGenerator licenseNumberGenerator;

    public LicenseService(LicenseRepository licenseRepository, LicenseNumberGenerator licenseNumberGenerator) {
        this.licenseRepository = licenseRepository;
        this.licenseNumberGenerator = licenseNumberGenerator;
    }

    public License create(User user) {
        var license = new License()
                .setUser(user)
                .setNumber(licenseNumberGenerator.generate())
                .setExpirationDate(null)
                .setCreatedAt(new Date());
        return licenseRepository.save(license);
    }

    public Optional<License> getById(Long id) {
        return licenseRepository.findById(id);
    }

    public List<License> getAll() {
        return licenseRepository.findAll();
    }

    public License getByNumber(Long number) {
        return licenseRepository.findByNumber(number);
    }

    public License getByUserId(Long userId) {
        return licenseRepository.findByUserId(userId);
    }

    public License update(License license) {
        return licenseRepository.save(license);
    }

    public Map<String, String> payment(CheckoutPayment payment, User user) throws StripeException {
        Stripe.apiKey = stripeApiKey ;

        SessionCreateParams params;
        try {
            params = SessionCreateParams.builder()
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
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());

        return responseData;
    }


    public void delete(Long id) {
        licenseRepository.findById(id)
                .ifPresent(licenseRepository::delete);
    }

}
