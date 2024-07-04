package locator_item.v1.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    public House create(HouseDTO houseDTO) {
        House house = House.builder()
                .id(houseDTO.getId())
                .name(houseDTO.getName())
                .address(houseDTO.getAddress())
                .build();

        return houseRepository.save(house);
    }

    public Optional<HouseDTO> getById(Long id) {
        Optional<House> houseOptional = houseRepository.findById(id);
        return houseOptional.map(this::convertToDTO);
    }

    public List<House> getAll() {
        return houseRepository.findAll();
    }

    public House updateById(Long id, HouseDTO houseDTO) {
        Optional<House> houseOptional = houseRepository.findById(id);

        if (houseOptional.isPresent()) {
            House house = houseOptional.get();
            house.setName(houseDTO.getName());
            house.setAddress(houseDTO.getAddress());

            return houseRepository.save(house);
        }

        return null;
    }

    public void deleteById(Long id) {
        House house = houseRepository.findById(id).orElseThrow(() -> new RuntimeException("House not found - " + id));

        houseRepository.delete(house);
    }

    private HouseDTO convertToDTO(House house) {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(house.getId());
        houseDTO.setName(house.getName());
        houseDTO.setAddress(house.getAddress());

        return houseDTO;
    }
}
