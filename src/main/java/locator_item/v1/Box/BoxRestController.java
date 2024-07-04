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
    public ResponseEntity<Box> create(@RequestBody BoxDTO boxDTO) {
        return new ResponseEntity<>(boxService.create(boxDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public List<Box> getById(@PathVariable Long id) {
        return Collections.singletonList(boxService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Box>> getAll() {
        return new ResponseEntity<>(boxService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Box> updateById(@PathVariable Long id, @RequestBody BoxDTO boxDTO) {
        return new ResponseEntity<>(boxService.updateById(id, boxDTO), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public HttpStatus deleteById(@PathVariable Long id) {
        boxService.deleteById(id);

        return HttpStatus.OK;
    }
}
