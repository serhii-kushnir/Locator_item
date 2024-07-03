package locator_item.v1.item;

import locator_item.v1.Box.Box;
import locator_item.v1.Box.BoxService;
import locator_item.v1.room.Room;
import locator_item.v1.room.RoomService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final RoomService roomService;
    private final BoxService boxService;

    public Item create(ItemDTOCreate itemDTOCreate) {
        Item item = Item.builder()
                .name(itemDTOCreate.getName())
                .description(itemDTOCreate.getDescription())
                .quantity(itemDTOCreate.getQuantity())
                .room(roomService.getById(itemDTOCreate.getRoomId()))
                .box(boxService.getById(itemDTOCreate.getBoxId()))
                .build();

        return itemRepository.save(item);
    }

    public List<Item> getByRoomId(Long id) {
        return itemRepository.findByBoxId(id);
    }

    public List<Item> getByBoxId(Long id) {
        return itemRepository.findByRoomId(id);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item updateById(Long id, ItemDTOUpdate dto) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Item not found - " + id));
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setQuantity(dto.getQuantity());

        if (dto.getBoxId() != null) {
            Box box = boxService.getById(dto.getBoxId());
            item.setBox(box);
        }

        if (dto.getRoomId() != null) {
            Room room = roomService.getById(dto.getRoomId());
            item.setRoom(room);
        }

        return itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
