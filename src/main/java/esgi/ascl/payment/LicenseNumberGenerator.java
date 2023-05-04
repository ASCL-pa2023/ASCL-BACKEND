package esgi.ascl.payment;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LicenseNumberGenerator {
    private final LicenseRepository licenseRepository;

    public LicenseNumberGenerator(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public Long generate() {
        Long ramdomLong = new Random().nextLong(100000000000000L, 999999999999999L);
        var license = licenseRepository.findByNumber(ramdomLong);
        if (license != null) return generate();
        return ramdomLong;
    }
}
