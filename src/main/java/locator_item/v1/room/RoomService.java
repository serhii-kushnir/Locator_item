package locator_item.v1.room;

import jakarta.transaction.Transactional;

import locator_item.v1.item.Item;
import locator_item.v1.item.ItemRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ItemRepository itemRepository;

    public Room create(RoomDTOCreate roomDTOCreate) {
        Room room = Room.builder()
                .name(roomDTOCreate.getName())
                .build();

        return roomRepository.save(room);
    }

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room getById(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Room not found - " + id));
    }

    public Room update(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteById(Long id) {
        List<Item> itemsToUpdate = itemRepository.findByRoomId(id);
        for (Item item : itemsToUpdate) {
            item.setRoom(null);
            itemRepository.save(item);
        }

        roomRepository.deleteById(id);
    }
}
