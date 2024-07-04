package locator_item.v1.item;

import lombok.Data;

@Data
public class ItemDTO {

    private Long id;

    private String name;

    private String description;

    private Short quantity;

    private Long boxId;

    private Long roomId;
}
