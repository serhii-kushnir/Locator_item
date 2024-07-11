package locator_item.v1.Cell;

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
public class CellRestController {

    private final CellService cellService;

    @PostMapping("/create")
    public ResponseEntity<Cell> createCell(@RequestBody CellDTO cellDTO) {
        return new ResponseEntity<>(cellService.createCell(cellDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cell> getCellById(@PathVariable Long id) {
        return new ResponseEntity<>(cellService.getCellById(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Cell>> getListCelles() {
        return new ResponseEntity<>(cellService.getListCelles(), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Cell> editCellById(@PathVariable Long id, @RequestBody CellDTO cellDTO) {
        return new ResponseEntity<>(cellService.editCellById(id, cellDTO), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public HttpStatus deleteCellById(@PathVariable Long id) {
        cellService.deleteCellById(id);

        return HttpStatus.OK;
    }
}
