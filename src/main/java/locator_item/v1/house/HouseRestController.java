package locator_item.v1.house;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import locator_item.v1.user.User;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/house")
@AllArgsConstructor
@Tag(name = "House")
public final class HouseRestController {

    private final HouseService houseService;
    private final UserService userService;

    @Operation(summary = "Create House")
    @PostMapping("/create")
    public ResponseEntity<HouseDTO> createHouse(@RequestBody final HouseDTO houseDTO) {
        User user = userService.getCurrentUser();

        return Optional.ofNullable(user)
                .map(users -> {
                    HouseDTO createdHouseDTO = houseService.createHouse(houseDTO, users);
                    return ResponseEntity.ok(createdHouseDTO);
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get House by id")
    @GetMapping("/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable final Long id) {
        User user = userService.getCurrentUser();

        return houseService.getHouseByIdAndUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get Houses by User")
    @GetMapping("/list")
    public List<HouseDTO> getHousesByUser() {
        User user = userService.getCurrentUser();

        return houseService.getHousesByUser(user).stream()
                .map(houseService::convertHouseToHouseDTO)
                .toList();
    }

    @Operation(summary = "Edit House by id")
    @PostMapping("/edit/{id}")
    public ResponseEntity<HouseDTO> editHouseById(@PathVariable final Long id, @RequestBody final HouseDTO houseDTO) {
        User user = userService.getCurrentUser();

        try {
            HouseDTO updatedHouseDTO = houseService.editHouseById(id, houseDTO, user);

            return ResponseEntity.ok(updatedHouseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @Operation(summary = "Delete House by id")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHouseById(@PathVariable Long id) {
        User user = userService.getCurrentUser();

        try {
            houseService.deleteHouseById(id, user.getUsername());
            return ResponseEntity.ok().build();
        } catch (HouseException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
