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

    public ItemDTO getById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found - " + id));
        return convertToDTO(item);
    }

    public Item create(ItemDTO itemDTO) {
        Item item = Item.builder()
                .id(itemDTO.getId())
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .quantity(itemDTO.getQuantity())
                .room(roomService.getById(itemDTO.getRoomId()))
                .box(boxService.getById(itemDTO.getBoxId()))
                .build();

        return itemRepository.save(item);
    }

    public List<Item> getByRoomId(Long id) {
        return itemRepository.findByBoxId(id);
    }

    public List<Item> getByBoxId(Long id) {
        return itemRepository.findByRoomId(id);
    }

    public List<ItemDTO> getAll() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Item updateById(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Item not found - " + id));
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setQuantity(itemDTO.getQuantity());

        if (itemDTO.getBoxId() != null) {
            Box box = boxService.getById(itemDTO.getBoxId());
            item.setBox(box);
        }

        if (itemDTO.getRoomId() != null) {
            Room room = roomService.getById(itemDTO.getRoomId());
            item.setRoom(room);
        }

        return itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    private ItemDTO convertToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId(itemDTO.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setBoxId(item.getBox() != null ? item.getBox().getId() : null);

        return itemDTO;
    }
}
