package locator_item.v1.house;

import locator_item.v1.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findByUser(User user);

    House findHouseByUser(User user);

    Optional<House> findByIdAndUser(Long id, User user);
}
