package topg.bimber_user_service.service;

import io.lettuce.core.AbstractRedisAsyncCommands;
import io.lettuce.core.GeoValue;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topg.bimber_user_service.dto.requests.UserAndAdminUpdateDto;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.dto.responses.UserResponseDto;
import topg.bimber_user_service.exceptions.EmailAlreadyExistException;
import topg.bimber_user_service.exceptions.InvalidDetailsException;
import topg.bimber_user_service.exceptions.UserNotFoundInDb;
import topg.bimber_user_service.models.User;
import topg.bimber_user_service.repository.UserRepository;

import java.math.BigDecimal;

import static topg.bimber_user_service.utils.ValidationUtils.isValidEmail;
import static topg.bimber_user_service.utils.ValidationUtils.isValidPassword;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserCreatedDto createUser(UserRequestDto userRequestDto) {

        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent())
            throw new EmailAlreadyExistException("Email already exist");
        User user = modelMapper.map(userRequestDto, User.class);
        user = userRepository.save(user);

        UserCreatedDto response = modelMapper.map(user, UserCreatedDto.class);
        response.setMessage("Registration successful");
        return response;
    }


    @Override
    public UserResponseDto editUserById(UserAndAdminUpdateDto userAndAdminUpdateDto, String userId) {
        return null;
    }

    @Override
    public String deleteUserById(String userId) {
        return "";
    }

    @Override
    public String fundAccount(String userId, BigDecimal amount) {
        return "";
    }

    @Override
    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }
}