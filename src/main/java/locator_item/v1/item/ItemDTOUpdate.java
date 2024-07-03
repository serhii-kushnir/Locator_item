package locator_item.v1.item;

import lombok.Data;

@Data
public class ItemDTOUpdate {

    private String name;

    private String description;

    private Short quantity;

    private Long boxId;

    private Long roomId;

}