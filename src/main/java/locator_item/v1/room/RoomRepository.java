package locator_item.v1.room;

import locator_item.v1.house.House;
import locator_item.v1.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHouse(final House house);

    List<Room> findAllByHouseUser(final User user);

    Optional<Room> findByIdAndHouseUser(final Long id, final User user);
}
