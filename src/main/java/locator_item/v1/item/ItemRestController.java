package locator_item.v1.item;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@AllArgsConstructor
public class ItemRestController {

    private ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<Item> create(@RequestBody ItemDTO itemDTO) {
        return new ResponseEntity<>(itemService.create(itemDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getById(@PathVariable Long id) {
        ItemDTO itemDTO = itemService.getById(id);
        return ResponseEntity.ok(itemDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> items = itemService.getAll();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Item> updateById(@PathVariable long id, @RequestBody ItemDTO itemDTO) {
        Item itemUpdate = itemService.updateById(id, itemDTO);
        return new ResponseEntity<>(itemUpdate, HttpStatus.OK);
    }

    @PostMapping("delete/{id}")
    public HttpStatus deleteById(@PathVariable Long id) {
        itemService.deleteById(id);
        return HttpStatus.OK;
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Item>> getByRoomId(@PathVariable Long id) {
        return mappingResponseListItems(itemService.getByRoomId(id));
    }

    @GetMapping("/box/{id}")
    public ResponseEntity<List<Item>> getByBoxId(@PathVariable Long id) {
        return mappingResponseListItems(itemService.getByBoxId(id));
    }

    private ResponseEntity<Item> mappingResponseItem(Item item) {
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    private ResponseEntity<List<Item>> mappingResponseListItems(List<Item> items) {
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
