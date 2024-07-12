package locator_item.v1.security.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
@Schema(description = "Authentication request")
public class AuthLoginRequest {

    @Schema(description = "Username", example = "Serhii")
    @Size(min = 3, max = 50, message = "Username must contain from 3 to 50 characters")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 4, max = 255, message = "Password length must be at least 4 characters and no more than 255 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;
}