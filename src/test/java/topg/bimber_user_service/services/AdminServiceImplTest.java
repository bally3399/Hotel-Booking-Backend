package topg.bimber_user_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import topg.bimber_user_service.dto.requests.LoginRequest;
import topg.bimber_user_service.dto.requests.UpdateDetailsRequest;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.LoginResponse;
import topg.bimber_user_service.dto.responses.UpdateDetailsResponse;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.models.Admin;
import topg.bimber_user_service.repository.AdminRepository;
import topg.bimber_user_service.repository.TokenRepository;
import topg.bimber_user_service.service.AdminService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private TokenRepository tokenRepository;
    private UserCreatedDto userCreatedDto;
    private LoginResponse loginResponse;
    @Autowired
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        tokenRepository.deleteAll();
        adminService.deleteAll();
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("john@doe.com");
        userRequestDto.setPassword("Password@123");
        userRequestDto.setUsername("johny");
        userCreatedDto = adminService.createAdmin(userRequestDto);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@doe.com");
        loginRequest.setPassword("Password@123");
        loginResponse = adminService.login(loginRequest);


    }

    @Test
    public void createAdminTest(){
        assertThat(userCreatedDto.getMessage()).isEqualTo("Admin registered successfully");
    }

    @Test
    public void testThatAdminCanLogin(){
        assertEquals("Login Successful", loginResponse.getMessage());
    }

    @Test
    public void testThatAdminCanBeUpdated() {
        Admin existingAdmin = new Admin();
        existingAdmin.setEmail("johnny@doe.com");
        existingAdmin.setPassword("Password@123");
        existingAdmin.setUsername("johnny");
        adminRepository.save(existingAdmin);

        UpdateDetailsRequest updateUserRequest = new UpdateDetailsRequest();
        updateUserRequest.setEmail("john@doe.com");
        updateUserRequest.setPassword("@Password12");

        UpdateDetailsResponse response = adminService.updateAdmin(updateUserRequest);

        assertEquals("Updated successfully", response.getMessage());

        Admin updatedAdmin = adminService.findByEmail("john@doe.com");
        assertThat(updatedAdmin).isNotNull();
        assertEquals("@Password12", updatedAdmin.getPassword());
    }
}