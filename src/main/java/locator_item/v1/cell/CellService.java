package locator_item.v1.cell;

import locator_item.v1.house.HouseRepository;
import locator_item.v1.room.RoomService;
import locator_item.v1.room.RoomRepository;
import locator_item.v1.room.Room;
import locator_item.v1.room.RoomException;

import locator_item.v1.user.User;
import locator_item.v1.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import static locator_item.v1.room.RoomService.ROOM_NOT_FOUND;

@Service
@AllArgsConstructor
public class CellService {

    private static final String CEll_NOT_FOUND = "Cell not found - ";

    private final UserService userService;
    private final HouseRepository houseRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final CellRepository cellRepository;

    public CellDTO createCell(CellDTO cellDTO) {
        User user = userService.getCurrentUser();

        Room room = roomRepository.findByIdAndHouseUser(cellDTO.getRoom().getId(), user)
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + cellDTO.getRoom().getId()));

        Cell cell = Cell.builder()
                .name(cellDTO.getName())
                .room(room)
                .build();

        Cell saveCell = cellRepository.save(cell);

        return convertCellToCellDTO(saveCell);
    }

    public CellDTO convertCellToCellDTO(final Cell cell) {
        CellDTO cellDTO = new CellDTO();
        cellDTO.setId(cell.getId());
        cellDTO.setName(cell.getName());
        cellDTO.setRoom(roomService.convertRoomToRoomDTO(cell.getRoom()));

        return cellDTO;
    }
}