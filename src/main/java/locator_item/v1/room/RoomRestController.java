package locator_item.v1.room;

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
@RequestMapping("/api/v1/room")
@AllArgsConstructor
public class RoomRestController {

    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<Room> create(@RequestBody RoomDTO roomDTO) {
        return mappingResponseRoom(roomService.create(roomDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        return mappingResponseRoom(roomService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAll() {
        return mappingResponseListIRooms(roomService.getAll());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Room> updateById(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        return new ResponseEntity<>(roomService.updateById(id, roomDTO), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public HttpStatus deleteById(@PathVariable Long id) {
        roomService.deleteById(id);
        return HttpStatus.OK;
    }

    private ResponseEntity<Room> mappingResponseRoom(Room room) {
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    private ResponseEntity<List<Room>> mappingResponseListIRooms(List<Room> rooms) {
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
