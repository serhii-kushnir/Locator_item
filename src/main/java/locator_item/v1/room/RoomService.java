package locator_item.v1.room;

import locator_item.v1.house.House;
import locator_item.v1.house.HouseDTO;
import locator_item.v1.house.HouseException;
import locator_item.v1.house.HouseRepository;
import locator_item.v1.user.User;
import locator_item.v1.user.UserService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static locator_item.v1.house.HouseService.HOUSE_NOT_FOUND;

@Service
@AllArgsConstructor
public class RoomService {

    public static final String ROOM_NOT_FOUND = "Room not found - ";

    private final RoomRepository roomRepository;
    private final HouseRepository houseRepository;
    private final UserService userService;

    public RoomDTO createRoom(final RoomDTO roomDTO) {
        User user = userService.getCurrentUser();
        House house = houseRepository.findByIdAndUser(roomDTO.getHouse().getId(), user)
                .orElseThrow(() -> new HouseException(HOUSE_NOT_FOUND + roomDTO.getHouse().getId()));

        Room room = Room.builder()
                .name(roomDTO.getName())
                .house(house)
                .build();

        Room savedRoom = roomRepository.save(room);

        return convertRoomToRoomDTO(savedRoom);
    }

    public RoomDTO getRoomById(final Long id) {
        User user = userService.getCurrentUser();
        Room room = roomRepository.findByIdAndHouseUser(id, user)
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + id));

        return convertRoomToRoomDTO(room);
    }

    public List<RoomDTO> getRoomsByHouse() {
        User user = userService.getCurrentUser();
        List<House> houses = houseRepository.findByUser(user);

        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (House house : houses) {
            List<Room> rooms = roomRepository.findByHouse(house);
            roomDTOs.addAll(rooms.stream()
                    .map(this::convertRoomToRoomDTO)
                    .toList());
        }

        return roomDTOs;
    }

    public RoomDTO editRoomById(final Long id, final RoomDTO roomDTO) {
        User user = userService.getCurrentUser();
        House house = houseRepository.findById(roomDTO.getHouse().getId())
                .orElseThrow(() -> new HouseException(HOUSE_NOT_FOUND + roomDTO.getHouse().getId()));

        Room room = roomRepository.findByIdAndHouseUser(id, user)
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + id));
        room.setName(roomDTO.getName());
        room.setHouse(house);

        Room updatedRoom = roomRepository.save(room);

        return convertRoomToRoomDTO(updatedRoom);
    }

    public void deleteRoomById(final Long id) {
        User user = userService.getCurrentUser();
        Room room = roomRepository.findByIdAndHouseUser(id, user)
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + id));

        roomRepository.delete(room);
    }

    public Room getRoomEntityById(Long id, User user) {
        return roomRepository.findByIdAndHouseUser(id, user)
                .orElseThrow(() -> new RoomException("Room not found or not authorized - " + id));
    }


    public RoomDTO convertRoomToRoomDTO(final Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());

        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(room.getHouse().getId());
        houseDTO.setName(room.getHouse().getName());
        houseDTO.setAddress(room.getHouse().getAddress());

        houseDTO.setUser(userService.convertUserToUserDTO(room.getHouse().getUser()));

        roomDTO.setHouse(houseDTO);

        return roomDTO;
    }
}
