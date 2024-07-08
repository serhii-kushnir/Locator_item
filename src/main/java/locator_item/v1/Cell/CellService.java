package locator_item.v1.Cell;

import locator_item.v1.room.Room;
import locator_item.v1.room.RoomRepository;
import locator_item.v1.room.RoomService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CellService {

    private final CellRepository cellRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;

    public Cell createCell(CellDTO cellDTO) {
        Cell cell = Cell.builder()
                .id(cellDTO.getId())
                .name(cellDTO.getName())
                .room(roomService.getRoomById(cellDTO.getRoomId()))
                .build();

        return cellRepository.save(cell);
    }

    public Cell getCellById(Long id) {
        return cellRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cell not found - " + id));
    }

    public List<Cell> getListCelles() {
        return cellRepository.findAll();
    }

    public Cell editCellById(Long id, CellDTO cellDTO) {
        Optional<Cell> cellOptional = cellRepository.findById(id);

        if (cellOptional.isPresent()) {
            Cell cell = cellOptional.get();
            cell.setName(cellDTO.getName());

            if (cellDTO.getRoomId() != null) {
                Optional<Room> roomOptional = roomRepository.findById(cellDTO.getRoomId());
                roomOptional.ifPresent(cell::setRoom);
            }

            return cellRepository.save(cell);
        }

        throw new RuntimeException("Cell not found - " + id);
    }

    public void deleteCellById(Long id) {
        cellRepository.deleteById(id);
    }
}
