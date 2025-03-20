package topg.bimber_user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import topg.bimber_user_service.dto.requests.UpdateDetailsRequest;
import topg.bimber_user_service.dto.responses.UpdateDetailsResponse;
import topg.bimber_user_service.dto.responses.UserResponseDto;
import topg.bimber_user_service.exceptions.UserNotFoundException;
import topg.bimber_user_service.service.AdminServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;


    @GetMapping("/me/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDto> getAdminById(@PathVariable("id") String userId) {
        UserResponseDto message = adminServiceImpl.getAdminById(userId);
        return ResponseEntity.ok(message);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateDetails(@Valid @RequestBody UpdateDetailsRequest request) {
        try {
            UpdateDetailsResponse response = adminServiceImpl.updateAdmin(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.valueOf(e.getMessage())).body(e.getMessage());
        }

    }
    @DeleteMapping("/me/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAdminById(@PathVariable("id") String userId) {
        String message = adminServiceImpl.deleteAdminById(userId);
        return ResponseEntity.ok(message);
    }

}