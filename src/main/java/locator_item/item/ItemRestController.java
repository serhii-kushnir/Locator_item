package locator_item.item;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemRestController {

    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody ItemDTO dto) {
        return mappingResponseItem(itemService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        return mappingResponseListItems(itemService.getAll());
    }

    @PutMapping
    public ResponseEntity<Item> update(@RequestBody Item item) {
        return mappingResponseItem(itemService.update(item));
    }

    @DeleteMapping("delete/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        itemService.delete(id);
        return HttpStatus.OK;
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Item>> getByRoomId(@PathVariable Long id) {
        return mappingResponseListItems(itemService.getByRoomId(id));
    }

    private ResponseEntity<Item> mappingResponseItem(Item item) {
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    private ResponseEntity<List<Item>> mappingResponseListItems(List<Item> items) {
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
