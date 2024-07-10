package locator_item.v1.house;

import locator_item.v1.user.User;
import locator_item.v1.user.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HouseService {

    private HouseRepository houseRepository;
    private UserRepository userRepository;

    public HouseDTO createHouse(HouseDTO houseDTO, User user) {
        House house = House.builder()
                .name(houseDTO.getName())
                .address(houseDTO.getAddress())
                .user(user)
                .build();

        House savedHouse = houseRepository.save(house);

        return convertHouseToHouseDTO(savedHouse);
    }


    public Optional<HouseDTO> getHouseByIdAndUser(Long id, User user) {
        Optional<House> houseOptional = houseRepository.findByIdAndUser(id, user);
        return houseOptional.map(this::convertHouseToHouseDTO);
    }

    public List<House> getListHouses() {
        return houseRepository.findAll();
    }

    public HouseDTO editHouseById(Long id, HouseDTO houseDTO, User user) {
        Optional<House> houseOptional = houseRepository.findById(id);

        if (houseOptional.isPresent()) {
            House house = houseOptional.get();

            if (!house.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("You do not have permission to edit this house");
            }

            house.setName(houseDTO.getName());
            house.setAddress(houseDTO.getAddress());

            House updatedHouse = houseRepository.save(house);
            return convertHouseToHouseDTO(updatedHouse);
        }

        return null;
    }

    public void deleteHouseById(Long id, String username) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found - " + id));

        if (!house.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete this house");
        }

        houseRepository.delete(house);
    }

    public List<House> getHousesByUser(User user) {
        return houseRepository.findByUser(user);
    }

    private HouseDTO convertHouseToHouseDTO(House house) {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(house.getId());
        houseDTO.setName(house.getName());
        houseDTO.setAddress(house.getAddress());
        houseDTO.setUser(house.getUser());

        return houseDTO;
    }
}
