package locator_item.v1.item;

import locator_item.v1.cell.*;

import locator_item.v1.room.*;

import locator_item.v1.user.User;
import locator_item.v1.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static locator_item.v1.cell.CellService.CELL_NOT_FOUND;
import static locator_item.v1.room.RoomService.ROOM_NOT_FOUND;

@Service
@AllArgsConstructor
public class ItemService {

    private static final String ITEM_NOT_FOUND = "Item not found - ";

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final CellService cellService;
    private final CellRepository cellRepository;

    public ItemDTO createItem(final ItemDTO itemDTO) {
        Room room = getRoomByIdAndHouseUser(itemDTO);
        Cell cell = itemDTO.getCell() != null ?
                        getCellByIdAndRoomHouseUser(itemDTO.getCell().getId()) :
                        null;

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

    public ItemDTO getItemById(final Long id) {
        Item item = getItemByIdAndCellRoomHouseUser(id);

        return convertItemToItemDTO(item);
    }

    public ItemDTO convertItemToItemDTO(Item item) {
        Optional<CellDTO> optionalCellDTO = cellService.convertCellToCellDTO(item.getCell());
        CellDTO cellDTO = optionalCellDTO.orElse(null); // Перетворення Optional в CellDTO, якщо Optional пустий, то повертається null

        Optional<RoomDTO> optionalRoomDTO = Optional.ofNullable(roomService.convertRoomToRoomDTO(item.getRoom()));
        RoomDTO roomDTO = optionalRoomDTO.orElse(null); // Перетворення Optional в RoomDTO, якщо Optional пустий, то повертається null

        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .quantity(item.getQuantity())
                .cell(cellDTO)
                .room(roomDTO)
                .build();
    }

    private Room getRoomByIdAndHouseUser(final ItemDTO itemDTO) {
        return roomRepository.findByIdAndHouseUser(itemDTO.getRoom().getId(), getCurrentUser())
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + itemDTO.getRoom().getId()));
    }

    public List<ItemDTO> getCellsByRoom() {
        List<Room> rooms = roomRepository.findAllByHouseUser(getCurrentUser());
        List<Long> roomIds = rooms.stream()
                .map(Room::getId)
                .toList();

        List<Item> items = itemRepository.findByRoomIdIn(roomIds);

        return items.stream()
                .map(this::convertItemToItemDTO)
                .toList();
    }

    public ItemDTO editItemById(final Long id, final ItemDTO itemDTO) {
        Room room = getRoomByIdAndHouseUser(itemDTO);

        Cell cell = null;
        if (itemDTO.getCell() != null) {
            cell = getCellByIdAndRoomHouseUser(itemDTO.getCell().getId());
        }

        Item item = getItemByIdAndCellRoomHouseUser(id);
        item = Item.builder()
                .id(item.getId())
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .quantity(itemDTO.getQuantity())
                .cell(cell)
                .room(room)
                .build();

        return convertItemToItemDTO(itemRepository.save(item));
    }

    public void deleteItemById(final Long id) {
        Item item = getItemByIdAndCellRoomHouseUser(id);

        itemRepository.delete(item);
    }

    private Cell getCellByIdAndRoomHouseUser(final Long id) {
        if (id == null) {
            return null;
        }

        return cellRepository.findByIdAndRoomHouseUser(id, getCurrentUser())
                .orElseThrow(() -> new CellException(CELL_NOT_FOUND + id));
    }

    private Item getItemByIdAndCellRoomHouseUser(final Long id) {
        return itemRepository.findByIdAndRoomHouseUser(id, getCurrentUser())
                .orElseThrow(() -> new ItemException(ITEM_NOT_FOUND + id));
    }

    private User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
