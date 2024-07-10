package locator_item.v1.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import locator_item.v1.user.User;
import locator_item.v1.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/house")
public class HouseRestController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    public ResponseEntity<HouseDTO> createHouse(@RequestBody HouseDTO houseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            House house = new House();
            house.setName(houseDTO.getName());
            house.setAddress(houseDTO.getAddress());
            house.setUser(existingUser);

            HouseDTO createdHouseDTO = houseService.createHouse(houseDTO, existingUser);
            return ResponseEntity.ok(createdHouseDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return houseService.getHouseByIdAndUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public List<HouseDTO> getHousesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<House> houses = houseService.getHousesByUser(user);

        return houses.stream()
                .map(house -> {
                    HouseDTO houseDTO = new HouseDTO();
                    houseDTO.setId(house.getId());
                    houseDTO.setName(house.getName());
                    houseDTO.setAddress(house.getAddress());
                    houseDTO.setUser(house.getUser());

                    return houseDTO;
                })
                .collect(Collectors.toList());
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity<House> editHouseById(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        return ResponseEntity.ok(houseService.editHouseById(id, houseDTO));
    }

    @PostMapping("/delete/{id}")
    public HttpStatus deleteHouseById(@PathVariable Long id) {
        houseService.deleteHouseById(id);
        return HttpStatus.OK;
    }
}
