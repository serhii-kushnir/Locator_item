package locator_item.v1.cell;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import locator_item.v1.room.RoomDTO;
import lombok.Data;

@Data
public class CellDTO {

    @Schema(description = "ID of the cell", example = "1")
    @NotBlank(message = "ID cannot be empty")
    private Long id;

    @Schema(description = "Name of the Cell", example = "Living Room")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Schema(description = "Room")
    private RoomDTO room;
}
