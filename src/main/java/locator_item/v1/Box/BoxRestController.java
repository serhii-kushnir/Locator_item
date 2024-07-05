package locator_item.v1.Box;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/box")
@AllArgsConstructor
public class BoxRestController {

    private final BoxService boxService;

    @PostMapping("/create")
    public ResponseEntity<Box> createBox(@RequestBody BoxDTO boxDTO) {
        return new ResponseEntity<>(boxService.createBox(boxDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public List<Box> getBoxById(@PathVariable Long id) {
        return Collections.singletonList(boxService.getBoxById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Box>> getListBoxes() {
        return new ResponseEntity<>(boxService.getListBoxes(), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Box> editBoxById(@PathVariable Long id, @RequestBody BoxDTO boxDTO) {
        return new ResponseEntity<>(boxService.editBoxById(id, boxDTO), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public HttpStatus deleteBoxById(@PathVariable Long id) {
        boxService.deleteBoxById(id);

        return HttpStatus.OK;
    }
}
