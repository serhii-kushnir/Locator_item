package locator_item.v1.house;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/house")
public class HouseRestController {

    @Autowired
    private HouseService houseService;


    @PostMapping("/create")
    public ResponseEntity<House> create(@RequestBody HouseDTO houseDTO) {
        return ResponseEntity.ok(houseService.create(houseDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<House> getById(@PathVariable Long id) {
        return houseService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<House> getAll() {
        return houseService.getAll();
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<House> updateById(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        return ResponseEntity.ok(houseService.updateById(id, houseDTO));
    }

    @PostMapping("/delete/{id}")
    public HttpStatus deleteById(@PathVariable Long id) {
        houseService.deleteById(id);
        return HttpStatus.OK;
    }
}
