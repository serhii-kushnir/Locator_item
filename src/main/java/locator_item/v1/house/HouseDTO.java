package locator_item.v1.house;

import locator_item.v1.user.User;

import lombok.Data;

@Data
public class HouseDTO {

    private Long id;

    private String name;

    private String address;

    private User user;
}