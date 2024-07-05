package locator_item.v1.Box;

import locator_item.v1.room.Room;
import locator_item.v1.room.RoomRepository;
import locator_item.v1.room.RoomService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoxService {

    private final BoxRepository boxRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;

    public Box createBox(BoxDTO boxDTO) {
        Box box = Box.builder()
                .id(boxDTO.getId())
                .name(boxDTO.getName())
                .room(roomService.getRoomById(boxDTO.getRoomId()))
                .build();

        return boxRepository.save(box);
    }

    public Box getBoxById(Long id) {
        return boxRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Box not found - " + id));
    }

    public List<Box> getListBoxes() {
        return boxRepository.findAll();
    }

    public Box editBoxById(Long id, BoxDTO boxDTO) {
        Optional<Box> boxOptional = boxRepository.findById(id);

        if (boxOptional.isPresent()) {
            Box box = boxOptional.get();
            box.setName(boxDTO.getName());

            if (boxDTO.getRoomId() != null) {
                Optional<Room> roomOptional = roomRepository.findById(boxDTO.getRoomId());
                roomOptional.ifPresent(box::setRoom);
            }

            return boxRepository.save(box);
        }

        throw new RuntimeException("Box not found - " + id);
    }

    public void deleteBoxById(Long id) {
        boxRepository.deleteById(id);
    }
}
