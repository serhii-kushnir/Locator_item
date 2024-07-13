package locator_item.v1.cell;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/cell")
@AllArgsConstructor
@Tag(name = "Room")
public final class CellRestController {

    private final CellService cellService;

    @Operation(summary = "Create cell")
    @PostMapping("/create")
    public ResponseEntity<CellDTO> createCell(@RequestBody final CellDTO cellDTO) {
        return ResponseEntity.ok(cellService.createCell(cellDTO));
    }
}