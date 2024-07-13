package locator_item.v1.cell;

import locator_item.v1.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CellRepository extends JpaRepository<Cell, Long> {

    Optional<Cell> findByIdAndRoomHouseUser(Long id, User user);

}
