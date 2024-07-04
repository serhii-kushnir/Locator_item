package locator_item.v1.Box;

import locator_item.v1.room.RoomService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BoxService {

    private final BoxRepository boxRepository;
    private final RoomService roomService;

    public Box create(BoxDTO boxDTO) {
        Box box = Box.builder()
                .name(boxDTO.getName())
                .room(roomService.getById(boxDTO.getRoomId()))
                .build();

        return boxRepository.save(box);
    }

    public List<Box> getAll() {
        return boxRepository.findAll();
    }

    public Box getById(Long id) {
        return boxRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Box not found - " + id));
    }

    public Box update(Box box) {
        return boxRepository.save(box);
    }

    public void deleteById(Long id) {
        boxRepository.deleteById(id);
    }
}
