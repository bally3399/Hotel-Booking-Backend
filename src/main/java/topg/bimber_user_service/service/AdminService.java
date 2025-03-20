package topg.bimber_user_service.service;

import topg.bimber_user_service.dto.requests.LoginRequest;
import topg.bimber_user_service.dto.requests.UpdateDetailsRequest;
import topg.bimber_user_service.dto.responses.LoginResponse;
import topg.bimber_user_service.dto.responses.UpdateDetailsResponse;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.UserResponseDto;
import topg.bimber_user_service.models.Admin;

public interface AdminService {
    UserCreatedDto createAdmin(UserRequestDto userRequestDto);

    LoginResponse login(LoginRequest loginRequest);

    UpdateDetailsResponse updateAdmin(UpdateDetailsRequest updateUserRequest);

    UserResponseDto getAdminById(String adminId);
//    UserResponseDto editAdminById(UserAndAdminUpdateDto adminUpdateRequestDto, String adminId);
    String deleteAdminById(String adminId);

    void deleteAll();

    Admin findByEmail(String mail);
}
