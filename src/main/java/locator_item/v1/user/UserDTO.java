package locator_item.v1.user;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Schema(description = "ID of the user", example = "1")
    @NotBlank(message = "ID cannot be empty")
    private Long id;

    @Schema(description = "Username of the user", example = "Serhii")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Email of the user", example = "Serhii@gmail.com")
    @NotBlank(message = "Email cannot be empty")
    private String email;
}
