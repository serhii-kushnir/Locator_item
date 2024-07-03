package locator_item.v1.item;

import lombok.AllArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ItemRestController.class);
    private ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAll() {
        return mappingResponseListItems(itemService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Item> create(@RequestBody ItemDTOCreate itemDTOCreate) {
        return mappingResponseItem(itemService.create(itemDTOCreate));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Item> updateById(@PathVariable long id, @RequestBody ItemDTOUpdate itemDTOUpdate) {
        logger.info("Updating item with id: " +  id);
        logger.info("Update data: " +  itemDTOUpdate);

        Item itemUdade = itemService.updateById(id, itemDTOUpdate);
        return new ResponseEntity<>(itemUdade, HttpStatus.OK);
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

    @GetMapping("/room/box/{id}")
    public ResponseEntity<List<Item>> getByBoxId(@PathVariable Long id) {
        return mappingResponseListItems(itemService.getByRoomId(id));
    }

    private ResponseEntity<Item> mappingResponseItem(Item item) {
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    private ResponseEntity<List<Item>> mappingResponseListItems(List<Item> items) {
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
