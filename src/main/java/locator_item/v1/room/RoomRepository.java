package locator_item.v1.room;

import locator_item.v1.house.House;
import locator_item.v1.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHouse(House house);

    List<Room> findAllByHouseUser(User user);

    Optional<Room> findByIdAndHouseUser(Long id, User user);
}
