package locator_item.v1.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import locator_item.v1.cell.CellDTO;
import locator_item.v1.room.RoomDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    @Schema(description = "ID of the Room", example = "1")
    @NotBlank(message = "ID cannot be empty")
    private Long id;

    @Schema(description = "Name of the Item", example = "Short-T")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Schema(description = "Description", example = "My red short-t")
    private String description;

    @Schema(description = "Quantity", example = "1")
    @NotBlank(message = "Quantity cannot be empty")
    private Short quantity;

    @Schema(description = "Cell", example = "Closet")
    private CellDTO cell;

    @Schema(description = "Room", example = "Living room")
    @NotBlank(message = "Room cannot be empty")
    private RoomDTO room;
}
