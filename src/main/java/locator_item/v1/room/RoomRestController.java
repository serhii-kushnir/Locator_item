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
    public ResponseEntity<Room> createRoom(@RequestBody RoomDTO roomDTO) {
        return new ResponseEntity<>(roomService.createRoom(roomDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.getRoomById(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Room>> getListRooms() {
        return new ResponseEntity<>(roomService.getListRooms(), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Room> editRoomById(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        return new ResponseEntity<>(roomService.editRoomById(id, roomDTO), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public HttpStatus deleteRoomById(@PathVariable Long id) {
        roomService.deleteRoomById(id);
        return HttpStatus.OK;
    }
}
