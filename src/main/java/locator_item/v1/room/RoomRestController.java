package locator_item.v1.room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import locator_item.v1.user.UserService;

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
@Tag(name = "Room")
public final class RoomRestController {

    private final RoomService roomService;
    private final UserService userService;

    @Operation(summary = "Create Room")
    @PostMapping("/create")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody final RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.createRoom(roomDTO));
    }

    @Operation(summary = "Get Room by id")
    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable final Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @Operation(summary = "Get Rooms by House")
    @GetMapping("/list")
    public List<RoomDTO> getRoomsByHouse() {
        return roomService.getRoomsByHouse();
    }

    @Operation(summary = "Edit Room by id")
    @PostMapping("/edit/{id}")
    public ResponseEntity<RoomDTO> editRoomById(@PathVariable final Long id, @RequestBody final RoomDTO roomDTO) {
        try {
            RoomDTO updatedRoomDTO = roomService.editRoomById(id, roomDTO);
            return ResponseEntity.ok(updatedRoomDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @Operation(summary = "Delete Room by id")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id) {
        try {
            roomService.deleteRoomById(id);
            return ResponseEntity.ok().build();
        } catch (RoomException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
