package locator_item.v1.Box;

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
@RequestMapping("/api/v1/box")
@AllArgsConstructor
public class BoxRestController {

    private final BoxService boxService;

    @GetMapping("/all")
    public ResponseEntity<List<Box>> getAll() {
        return new ResponseEntity<>(boxService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Box> create(@RequestBody BoxDTOCreate boxDTOCreate) {
        return new ResponseEntity<>(boxService.create(boxDTOCreate), HttpStatus.CREATED);
    }
}
