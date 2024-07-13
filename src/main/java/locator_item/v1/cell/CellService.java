package locator_item.v1.cell;

import locator_item.v1.room.RoomService;
import locator_item.v1.room.RoomRepository;
import locator_item.v1.room.Room;
import locator_item.v1.room.RoomException;

import locator_item.v1.user.User;
import locator_item.v1.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import static locator_item.v1.room.RoomService.ROOM_NOT_FOUND;

@Service
@AllArgsConstructor
public class CellService {

    private static final String CELL_NOT_FOUND = "Cell not found - ";

    private final UserService userService;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final CellRepository cellRepository;

    public CellDTO createCell(final CellDTO cellDTO) {
        Room room = roomRepository.findByIdAndHouseUser(cellDTO.getRoom().getId(), getCurrentUser())
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + cellDTO.getRoom().getId()));

        Cell cell = Cell.builder()
                .name(cellDTO.getName())
                .room(room)
                .build();

        Cell saveCell = cellRepository.save(cell);

        return convertCellToCellDTO(saveCell);
    }

    public CellDTO getCellById(final Long id) {
        Cell cell = cellRepository.findByIdAndRoomHouseUser(id, getCurrentUser())
                .orElseThrow(() -> new CellException(CELL_NOT_FOUND + id));

        return convertCellToCellDTO(cell);
    }

    public List<CellDTO> getCellsByRoom() {
        List<Room> rooms = roomRepository.findAllByHouseUser(getCurrentUser());
        List<Cell> cells = cellRepository.findAllByRoomIn(rooms);

        return cells.stream()
                .map(this::convertCellToCellDTO)
                .toList();
    }

    public CellDTO convertCellToCellDTO(final Cell cell) {
        CellDTO cellDTO = new CellDTO();
        cellDTO.setId(cell.getId());
        cellDTO.setName(cell.getName());
        cellDTO.setRoom(roomService.convertRoomToRoomDTO(cell.getRoom()));

        return cellDTO;
    }

    private User getCurrentUser() {
        return userService.getCurrentUser();
    }
}