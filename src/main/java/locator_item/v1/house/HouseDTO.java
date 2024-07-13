package locator_item.v1.house;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import locator_item.v1.user.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class HouseDTO {

    @Schema(description = "ID House", example = "1")
    @NotBlank(message = "ID cannot be empty")
    private Long id;

    @Schema(description = "Name of the House", example = "Home 1")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Schema(description = "Address of the House", example = "Bazova, 17")
    private String address;

    @Schema(description = "User", example = "Serhii")
    @NotBlank(message = "User cannot be empty")
    private UserDTO user;
}