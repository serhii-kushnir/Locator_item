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

    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAll() {
        return mappingResponseListIRooms(roomService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Room> create(@RequestBody RoomDTOCreate roomDTOCreate) {
        return mappingResponseRoom(roomService.create(roomDTOCreate));
    }

    @PostMapping("/update")
    public ResponseEntity<Room> update(@RequestBody Room room) {
        return mappingResponseRoom(roomService.update(room));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<Room> mappingResponseRoom(Room room) {
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    private ResponseEntity<List<Room>> mappingResponseListIRooms(List<Room> rooms) {
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
