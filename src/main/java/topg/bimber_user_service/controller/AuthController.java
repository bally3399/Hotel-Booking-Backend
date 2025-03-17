package topg.bimber_user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import topg.bimber_user_service.dto.responses.JwtResponseDto;
import topg.bimber_user_service.dto.requests.LoginRequestDto;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.service.AdminService;
import topg.bimber_user_service.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AdminService adminService;
    private final UserService userService;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JwtResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto) {

        JwtResponseDto message = userService.loginUser(loginRequestDto);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/user/register")
    public ResponseEntity<UserCreatedDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserCreatedDto message = userService.createUser(userRequestDto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<UserCreatedDto> createAdmin(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserCreatedDto message = adminService.createAdmin(userRequestDto);
        return ResponseEntity.ok(message);
    }


}
