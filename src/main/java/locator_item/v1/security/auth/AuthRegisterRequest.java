package locator_item.v1.security.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Registration Request")
public class AuthRegisterRequest {

    @Schema(description = "Username", example = "Serhii")
    @Size(min = 3, max = 50, message = "Username must contain from 3 to 50 characters")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "E-mail address", example = "serhii@gmail.com")
    @Size(min = 3, max = 255, message = "The email address must contain between 3 and 255 characters")
    @NotBlank(message = "Email address cannot be empty")
    @Email(message = "Email address must be in the format user@example.com")
    private String email;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 4, max = 255, message = "Password length must be at least 4 characters and no more than 255 characters")
    private String password;
}