package locator_item.v1.cell;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import locator_item.v1.room.RoomException;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cell")
@AllArgsConstructor
@Tag(name = "Cell")
public final class CellRestController {

    private final CellService cellService;

    @Operation(summary = "Create cell")
    @PostMapping("/create")
    public ResponseEntity<CellDTO> createCell(@RequestBody final CellDTO cellDTO) {
        return ResponseEntity.ok(cellService.createCell(cellDTO));
    }

    @Operation(summary = "Get Cell by id")
    @GetMapping("/{id}")
    public ResponseEntity<CellDTO> getCellById(@PathVariable final Long id) {
        return ResponseEntity.ok(cellService.getCellById(id));
    }

    @Operation(summary = "Get Cells by Room")
    @GetMapping("/list")
    public ResponseEntity<List<CellDTO>> getCellsByRoom() {
        return ResponseEntity.ok(cellService.getCellsByRoom());
    }

    @Operation(summary = "Edit Cell by id")
    @PostMapping("/edit/{id}")
    public ResponseEntity<CellDTO> editCellById(@PathVariable final Long id, @RequestBody final CellDTO cellDTO) {
        try {
            CellDTO updatedCellDTO = cellService.editCellById(id, cellDTO);
            return ResponseEntity.ok(updatedCellDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @Operation(summary = "Delete Cell by id")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id) {
        try {
            cellService.deleteCellById(id);
            return ResponseEntity.ok().build();
        } catch (RoomException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}