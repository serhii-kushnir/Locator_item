package locator_item.v1.item;


import locator_item.v1.Cell.Cell;
import locator_item.v1.Cell.CellService;
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
    private final CellService cellService;

    public Item createItem(ItemDTO itemDTO) {
        Room room = roomService.getRoomById(itemDTO.getRoomId());
        Cell cell = itemDTO.getCellId() != null ? cellService.getCellById(itemDTO.getCellId()) : null;

        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setQuantity(itemDTO.getQuantity());
        item.setRoom(room);
        item.setCell(cell);

        return itemRepository.save(item);
    }

    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found - " + id));

        return convertItemToItemDTO(item);
    }

    public List<Item> getItemsByRoomId(Long id) {
        return itemRepository.findByCellId(id);
    }

    public List<Item> getItemsByCellId(Long id) {
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

        if (itemDTO.getCellId() != null) {
            Cell cell = cellService.getCellById(itemDTO.getCellId());
            item.setCell(cell);
        } else {
            item.setCell(null);
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
        itemDTO.setCellId(item.getCell() != null ? item.getCell().getId() : null);
        itemDTO.setRoomId(item.getRoom() != null ? item.getRoom().getId() : null);

        return itemDTO;
    }
}
