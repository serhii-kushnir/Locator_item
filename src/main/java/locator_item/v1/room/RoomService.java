package locator_item.v1.room;

import jakarta.transaction.Transactional;

import locator_item.v1.house.House;
import locator_item.v1.house.HouseRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final HouseRepository houseRepository;

    public Room create(RoomDTO roomDTO) {
        House house = houseRepository.findById(roomDTO.getHouseId())
                .orElseThrow(() -> new RuntimeException("House not found - " + roomDTO.getHouseId()));

        Room room = Room.builder()
                .id(roomDTO.getId())
                .name(roomDTO.getName())
                .house(house)
                .build();

        return roomRepository.save(room);
    }

    public Room getById(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Room not found - " + id));
    }

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room updateById(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
}
