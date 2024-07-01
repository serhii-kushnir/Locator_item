package locator_item.room;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomRestController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        return new ResponseEntity<>(roomService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<>(roomService.create(room), HttpStatus.CREATED);
    }

}
