package locator_item.item;

import lombok.Data;

@Data
public class ItemDTO {

    private String name;

    private String description;

    private Short quantity;

    private Long roomId;
}
