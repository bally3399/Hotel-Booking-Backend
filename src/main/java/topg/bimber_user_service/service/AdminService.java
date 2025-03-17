package topg.bimber_user_service.service;

import topg.bimber_user_service.dto.requests.UserAndAdminUpdateDto;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.UserResponseDto;

public interface AdminService {
    UserCreatedDto createAdmin(UserRequestDto userRequestDto);
    UserResponseDto getAdminById(String adminId);
    UserResponseDto editAdminById(UserAndAdminUpdateDto adminUpdateRequestDto, String adminId);
    String deleteAdminById(String adminId);

    void deleteAll();
}
