package locator_item.v1.cell;

import locator_item.v1.room.Room;
import locator_item.v1.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CellRepository extends JpaRepository<Cell, Long> {

    List<Cell> findAllByRoomIn(final List<Room> rooms);

    Optional<Cell> findByIdAndRoomHouseUser(final Long id, final User user);
}
