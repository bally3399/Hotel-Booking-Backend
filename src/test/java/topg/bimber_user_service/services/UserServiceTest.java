package topg.bimber_user_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.service.UserService;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        userService.deleteAll();
    }

    @Test
    public void createUserTest() {
        UserRequestDto createUser = new UserRequestDto();
        createUser.setUsername("username");
        createUser.setPassword("Password1%");
        createUser.setEmail("user@gmail.com");
        UserCreatedDto response = userService.createUser(createUser);
        assertThat(response.getMessage()).isEqualTo("Registration successful");
    }
}
