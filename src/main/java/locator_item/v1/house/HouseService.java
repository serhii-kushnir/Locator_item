package locator_item.v1.house;

import locator_item.v1.user.User;
import locator_item.v1.user.UserDTO;

import locator_item.v1.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public final class HouseService {

    private final HouseRepository houseRepository;
    private final UserService userService;

    public HouseDTO createHouse(final HouseDTO houseDTO) {
        User user = userService.getCurrentUser();
        House house = House.builder()
                .name(houseDTO.getName())
                .address(houseDTO.getAddress())
                .user(user)
                .build();

        House savedHouse = houseRepository.save(house);

        return convertHouseToHouseDTO(savedHouse);
    }

    public Optional<HouseDTO> getHouseByIdAndUser(final Long id) {
        User user = userService.getCurrentUser();
        Optional<House> houseOptional = houseRepository.findByIdAndUser(id, user);

        return houseOptional.map(this::convertHouseToHouseDTO);
    }

    public HouseDTO editHouseById(final Long id, final HouseDTO houseDTO) {
        User user = userService.getCurrentUser();
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new HouseException("House not found - " + id));

        if (!house.getUser().getId().equals(user.getId())) {
            throw new HouseException("You do not have permission to edit this house");
        }

        house.setName(houseDTO.getName());
        house.setAddress(houseDTO.getAddress());

        House updatedHouse = houseRepository.save(house);

        return convertHouseToHouseDTO(updatedHouse);
    }

    public void deleteHouseById(final Long id) {
        User user = userService.getCurrentUser();
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new HouseException("House not found - " + id));

        if (!house.getUser().getUsername().equals(user.getUsername())) {
            throw new HouseException("You are not authorized to delete this house");
        }

        houseRepository.delete(house);
    }

    public List<House> getHousesByUser() {
        User user = userService.getCurrentUser();

        return houseRepository.findByUser(user);
    }

    public HouseDTO convertHouseToHouseDTO(final House house) {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(house.getId());
        houseDTO.setName(house.getName());
        houseDTO.setAddress(house.getAddress());

        UserDTO userDTO = userService.convertUserToUserDTO(house.getUser());
        houseDTO.setUser(userDTO);

        return houseDTO;
    }
}
