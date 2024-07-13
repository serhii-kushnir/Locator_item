package locator_item.v1.room;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import locator_item.v1.house.HouseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class RoomDTO {

    @Schema(description = "ID of the Room", example = "1")
    @NotBlank(message = "ID cannot be empty")
    private Long id;

    @Schema(description = "Name of the Room", example = "Living Room")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Schema(description = "House")
    private HouseDTO house;
}
