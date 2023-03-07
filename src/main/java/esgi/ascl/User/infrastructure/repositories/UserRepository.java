package esgi.ascl.User.infrastructure.repositories;

import esgi.ascl.User.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);
    User findByEmail(String email);
}
