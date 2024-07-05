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
    public ResponseEntity<House> createHouse(@RequestBody HouseDTO houseDTO) {
        return ResponseEntity.ok(houseService.createHouse(houseDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable Long id) {
        return houseService.getHouseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public List<House> getListHouses() {
        return houseService.getListHouses();
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity<House> editHouseById(@PathVariable Long id, @RequestBody HouseDTO houseDTO) {
        return ResponseEntity.ok(houseService.editHouseById(id, houseDTO));
    }

    @PostMapping("/delete/{id}")
    public HttpStatus deleteHouseById(@PathVariable Long id) {
        houseService.deleteHouseById(id);
        return HttpStatus.OK;
    }
}
