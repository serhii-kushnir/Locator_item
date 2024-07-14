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

    public static final String CELL_NOT_FOUND = "Cell not found - ";

    private final UserService userService;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final CellRepository cellRepository;

    public CellDTO createCell(final CellDTO cellDTO) {
        Room room = getRoomByIdAndHouseUser(cellDTO);

        Cell cell = Cell.builder()
                .name(cellDTO.getName())
                .room(room)
                .build();

        return convertCellToCellDTO(cellRepository.save(cell));
    }

    public CellDTO getCellById(final Long id) {
        Cell cell = getCellByIdAndRoomHouseUser(id);

        return convertCellToCellDTO(cell);
    }

    public List<CellDTO> getCellsByRoom() {
        List<Room> rooms = roomRepository.findAllByHouseUser(getCurrentUser());
        List<Cell> cells = cellRepository.findAllByRoomIn(rooms);

        return cells.stream()
                .map(this::convertCellToCellDTO)
                .toList();
    }

    public CellDTO editCellById(final Long id, final CellDTO cellDTO) {
        Room room = getRoomByIdAndHouseUser(cellDTO);

        Cell cell = getCellByIdAndRoomHouseUser(id);
        cell.setName(cellDTO.getName());
        cell.setRoom(room);

        return convertCellToCellDTO(cellRepository.save(cell));
    }

    public void deleteCellById(final Long id) {
        Cell cell = getCellByIdAndRoomHouseUser(id);

        cellRepository.delete(cell);
    }

    public CellDTO convertCellToCellDTO(final Cell cell) {
        return CellDTO.builder()
                .id(cell.getId())
                .name(cell.getName())
                .room(roomService.convertRoomToRoomDTO(cell.getRoom()))
                .build();
    }

    private Room getRoomByIdAndHouseUser(final CellDTO cellDTO) {
        return roomRepository.findByIdAndHouseUser(cellDTO.getRoom().getId(), getCurrentUser())
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + cellDTO.getRoom().getId()));
    }

    private Cell getCellByIdAndRoomHouseUser(final Long id) {
        return cellRepository.findByIdAndRoomHouseUser(id, getCurrentUser())
                .orElseThrow(() -> new CellException(CELL_NOT_FOUND + id));
    }

    private User getCurrentUser() {
        return userService.getCurrentUser();
    }
}