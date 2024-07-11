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
    public ResponseEntity<Item> createItem(@RequestBody ItemDTO itemDTO) {
        return new ResponseEntity<>(itemService.createItem(itemDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        return ResponseEntity.ok(itemDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ItemDTO>> getListItems() {
        List<ItemDTO> items = itemService.getListItems();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Item> editItemById(@PathVariable long id, @RequestBody ItemDTO itemDTO) {
        Item itemUpdate = itemService.editItemById(id, itemDTO);
        return new ResponseEntity<>(itemUpdate, HttpStatus.OK);
    }

    @PostMapping("delete/{id}")
    public HttpStatus deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
        return HttpStatus.OK;
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Item>> getItemsByRoomId(@PathVariable Long id) {
        return  new ResponseEntity<>(itemService.getItemsByRoomId(id), HttpStatus.OK);
    }

    @GetMapping("/cell/{id}")
    public ResponseEntity<List<Item>> getItemsByCellId(@PathVariable Long id) {
        return  new ResponseEntity<>(itemService.getItemsByCellId(id), HttpStatus.OK);
    }

}
