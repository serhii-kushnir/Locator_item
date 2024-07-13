package locator_item.v1.room;

import locator_item.v1.house.House;
import locator_item.v1.house.HouseDTO;
import locator_item.v1.house.HouseException;
import locator_item.v1.house.HouseRepository;
import locator_item.v1.user.User;
import locator_item.v1.user.UserDTO;
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
        House house = getHouseByIdAndUser(roomDTO);

        Room room = Room.builder()
                .name(roomDTO.getName())
                .house(house)
                .build();

        return convertRoomToRoomDTO(roomRepository.save(room));
    }

    public RoomDTO getRoomById(final Long id) {
        Room room = getRoomByIdAndHouseUser(id);

        return convertRoomToRoomDTO(room);
    }

    public List<RoomDTO> getRoomsByHouse() {
        List<House> houses = houseRepository.findByUser(getCurrentUser());

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
        House house = getHouseByIdAndUser(roomDTO);

        Room room = getRoomByIdAndHouseUser(id);
        room.setName(roomDTO.getName());
        room.setHouse(house);

        return convertRoomToRoomDTO(roomRepository.save(room));
    }

    public void deleteRoomById(final Long id) {
        Room room = getRoomByIdAndHouseUser(id);

        roomRepository.delete(room);
    }

    public RoomDTO convertRoomToRoomDTO(final Room room) {
        UserDTO userDTO = userService.convertUserToUserDTO(room.getHouse().getUser());

        HouseDTO houseDTO = HouseDTO.builder()
                .id(room.getHouse().getId())
                .name(room.getHouse().getName())
                .address(room.getHouse().getAddress())
                .user(userDTO)
                .build();

        return RoomDTO.builder()
                .id(room.getId())
                .name(room.getName())
                .house(houseDTO)
                .build();
    }

    private House getHouseByIdAndUser(RoomDTO roomDTO) {
        return houseRepository.findByIdAndUser(roomDTO.getHouse().getId(), getCurrentUser())
                .orElseThrow(() -> new HouseException(HOUSE_NOT_FOUND + roomDTO.getHouse().getId()));
    }

    private Room getRoomByIdAndHouseUser(Long id) {
        return roomRepository.findByIdAndHouseUser(id, getCurrentUser())
                .orElseThrow(() -> new RoomException(ROOM_NOT_FOUND + id));
    }

    private User getCurrentUser() {
        return userService.getCurrentUser();
    }
}
