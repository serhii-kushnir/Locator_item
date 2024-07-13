package locator_item.v1.item;

import locator_item.v1.cell.*;
import locator_item.v1.room.*;

import locator_item.v1.user.User;
import locator_item.v1.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import static locator_item.v1.cell.CellService.CELL_NOT_FOUND;
import static locator_item.v1.room.RoomService.ROOM_NOT_FOUND;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final CellService cellService;
    private final CellRepository cellRepository;

    public ItemDTO createItem(final ItemDTO itemDTO) {
        Room room = getRoomByIdAndHouseUser(itemDTO);
        Cell cell = itemDTO.getCell() != null ? getCellByIdAndRoomHouseUser(itemDTO.getCell().getId()) : null;

        Item item = Item.builder()
                .id(itemDTO.getId())
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .quantity(itemDTO.getQuantity())
                .room(room)
                .cell(cell)
                .build();

        return convertItemToItemDTO(itemRepository.save(item));
    }


    public ItemDTO convertItemToItemDTO(Item item) {
        ItemDTO.ItemDTOBuilder builder = ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .quantity(item.getQuantity());

        if (item.getCell() != null) {
            builder.cell(cellService.convertCellToCellDTO(item.getCell()));
        } else {
            builder.room(roomService.convertRoomToRoomDTO(item.getRoom()));
        }

        return builder.build();
    }

    private Room getRoomByIdAndHouseUser(final ItemDTO itemDTO) {
        return roomRepository.findByIdAndHouseUser(itemDTO.getRoom().getId(), getCurrentUser())
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + itemDTO.getRoom().getId()));
    }

    private Cell getCellByIdAndRoomHouseUser(final Long id) {
        if (id == null) {
            return null; // або поверніть null, якщо id є null
        }

        return cellRepository.findByIdAndRoomHouseUser(id, getCurrentUser())
                .orElseThrow(() -> new CellException(CELL_NOT_FOUND + id));
    }


    private User getCurrentUser() {
        return userService.getCurrentUser();
    }

//    public ItemDTO getItemById(Long id) {
//        Item item = itemRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Item not found - " + id));
//
//        return convertItemToItemDTO(item);
//    }
//
//    public List<Item> getItemsByRoomId(Long id) {
//        return itemRepository.findByCellId(id);
//    }
//
//    public List<Item> getItemsByCellId(Long id) {
//        return itemRepository.findByRoomId(id);
//    }
//
//    public List<ItemDTO> getListItems() {
//        List<Item> items = itemRepository.findAll();
//        return items.stream()
//                .map(this::convertItemToItemDTO)
//                .collect(Collectors.toList());
//    }
//
//    public Item editItemById(Long id, ItemDTO itemDTO) {
//        Item item = itemRepository.findById(id).orElseThrow(() ->
//                new RuntimeException("Item not found - " + id));
//
//        item.setName(itemDTO.getName());
//        item.setDescription(itemDTO.getDescription());
//        item.setQuantity(itemDTO.getQuantity());
//
//        if (itemDTO.getCellId() != null) {
//            Cell cell = cellService.getCellById(itemDTO.getCellId());
//            item.setCell(cell);
//        } else {
//            item.setCell(null);
//        }
//
//        if (itemDTO.getRoomId() != null) {
//            Room room = roomService.getRoomById(itemDTO.getRoomId());
//            item.setRoom(room);
//        } else {
//            item.setRoom(null);
//        }
//
//        return itemRepository.save(item);
//    }
//
//
//    public void deleteItemById(Long id) {
//        itemRepository.deleteById(id);
//    }
//
//    private ItemDTO convertItemToItemDTO(Item item) {
//        ItemDTO itemDTO = new ItemDTO();
//
//        itemDTO.setId(item.getId());
//        itemDTO.setName(item.getName());
//        itemDTO.setDescription(item.getDescription());
//        itemDTO.setQuantity(item.getQuantity());
//        itemDTO.setCellId(item.getCell() != null ? item.getCell().getId() : null);
//        itemDTO.setRoomId(item.getRoom() != null ? item.getRoom().getId() : null);
//
//        return itemDTO;
//    }
}
