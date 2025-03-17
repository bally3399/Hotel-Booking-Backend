package topg.bimber_user_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.UserCreatedDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;
    private UserCreatedDto userCreatedDto;

    @BeforeEach
    public void setUp() {
        adminService.deleteAll();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("john@doe.com");
        userRequestDto.setPassword("Password@123");
        userRequestDto.setUsername("johny");
        userCreatedDto = adminService.createAdmin(userRequestDto);
    }

    @Test
    public void createAdminTest(){
        assertThat(userCreatedDto.getMessage()).isEqualTo("Admin created successfully");
    }

}