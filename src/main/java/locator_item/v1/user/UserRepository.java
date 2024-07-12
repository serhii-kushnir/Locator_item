package locator_item.v1.user;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);

    boolean existsByUsername(final String username);

    boolean existsByEmail(final String email);
}