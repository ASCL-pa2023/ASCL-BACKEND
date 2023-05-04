package esgi.ascl.payment;

import esgi.ascl.User.domain.entities.License;
import esgi.ascl.User.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseService {

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

}
