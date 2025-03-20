package topg.bimber_user_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import topg.bimber_user_service.dto.requests.UpdateDetailsRequest;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.UpdateDetailsResponse;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.service.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void updateUserServiceTest() {

        UserRequestDto createUser = new UserRequestDto();
        createUser.setUsername("username1");
        createUser.setPassword("Password1%");
        createUser.setEmail("username@gmail.com");
        UserCreatedDto response = userService.createUser(createUser);

        UpdateDetailsRequest updateUserDetails = new UpdateDetailsRequest();
        updateUserDetails.setEmail("username@gmail.com");
        updateUserDetails.setPassword("Password1%");
        updateUserDetails.setNewPassword("Password2%");
        UpdateDetailsResponse updateResponse = userService.updateUserDetails(updateUserDetails);
        assertEquals("Password reset successful", updateResponse.getMessage());
    }

}
