package locator_item.v1.item;

import locator_item.v1.Box.Box;
import locator_item.v1.Box.BoxService;
import locator_item.v1.room.Room;
import locator_item.v1.room.RoomService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final RoomService roomService;
    private final BoxService boxService;

    public Item createItem(ItemDTO itemDTO) {
        Room room = roomService.getRoomById(itemDTO.getRoomId());
        Box box = itemDTO.getBoxId() != null ? boxService.getBoxById(itemDTO.getBoxId()) : null;

        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setQuantity(itemDTO.getQuantity());
        item.setRoom(room);
        item.setBox(box);

        return itemRepository.save(item);
    }

    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found - " + id));

        return convertItemToItemDTO(item);
    }

    public List<Item> getItemsByRoomId(Long id) {
        return itemRepository.findByBoxId(id);
    }

    public List<Item> getItemsByBoxId(Long id) {
        return itemRepository.findByRoomId(id);
    }

    public List<ItemDTO> getListItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(this::convertItemToItemDTO)
                .collect(Collectors.toList());
    }

    public Item editItemById(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Item not found - " + id));

        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setQuantity(itemDTO.getQuantity());

        if (itemDTO.getBoxId() != null) {
            Box box = boxService.getBoxById(itemDTO.getBoxId());
            item.setBox(box);
        } else {
            item.setBox(null);
        }

        if (itemDTO.getRoomId() != null) {
            Room room = roomService.getRoomById(itemDTO.getRoomId());
            item.setRoom(room);
        } else {
            item.setRoom(null);
        }

        return itemRepository.save(item);
    }


    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }

    private ItemDTO convertItemToItemDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setBoxId(item.getBox() != null ? item.getBox().getId() : null);
        itemDTO.setRoomId(item.getRoom() != null ? item.getRoom().getId() : null);

        return itemDTO;
    }
}
