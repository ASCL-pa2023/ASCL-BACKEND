package esgi.ascl.payment;

import esgi.ascl.User.domain.entities.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface LicenseRepository extends JpaRepository<License, Long> {

    License findByNumber(Long number);
    License findByUserId(Long userId);
}
