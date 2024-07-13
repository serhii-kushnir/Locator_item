package locator_item.v1.user;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final String USER_NOT_FOUND = "User not found";

    public void create(final User user) {
        if (userRepository.existsByUsername(user.getUsername())) {

            throw new UserException("A user with the same name already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("A user with this email already exists");
        }

        userRepository.save(user);
    }

    public User getByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public UserDTO convertUserToUserDTO(final User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }
}
