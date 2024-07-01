package locator_item.item;

import locator_item.room.RoomService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final RoomService roomService;

    public Item create(ItemDTO dto) {
        Item item = Item.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .quantity(dto.getQuantity())
                .room(roomService.getById(dto.getRoomId()))
                .build();

        return itemRepository.save(item);
    }

    public List<Item> getByRoomId(Long id) {
        return itemRepository.findByRoomId(id);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
