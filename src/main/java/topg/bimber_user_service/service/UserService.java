package topg.bimber_user_service.service;

import topg.bimber_user_service.dto.requests.UpdateDetailsRequest;
import topg.bimber_user_service.dto.responses.UpdateDetailsResponse;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.UserResponseDto;

import java.math.BigDecimal;

public interface UserService {
    UserCreatedDto createUser(UserRequestDto userRequestDto);
    UpdateDetailsResponse updateUserDetails(UpdateDetailsRequest updateUserDetails);
    void deleteAll();
//    UserResponseDto editUserById(UserAndAdminUpdateDto userAndAdminUpdateDto, String userId);
    String deleteUserById(String userId);
    String fundAccount(String userId, BigDecimal amount);
}
