package locator_item.v1.item;

import locator_item.v1.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByRoomIdIn(final List<Long> roomIds);

    Optional<Item> findByIdAndRoomHouseUser(final Long id, final User user);
}
