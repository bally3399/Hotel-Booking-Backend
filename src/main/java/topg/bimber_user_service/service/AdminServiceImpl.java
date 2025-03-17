package topg.bimber_user_service.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import topg.bimber_user_service.dto.requests.UserAndAdminUpdateDto;
import topg.bimber_user_service.dto.requests.UserRequestDto;
import topg.bimber_user_service.dto.responses.UserCreatedDto;
import topg.bimber_user_service.dto.responses.UserResponseDto;
import topg.bimber_user_service.exceptions.InvalidDetailsException;
import topg.bimber_user_service.exceptions.MailNotSentException;
import topg.bimber_user_service.exceptions.UserNotFoundInDb;
import topg.bimber_user_service.mail.MailService;
import topg.bimber_user_service.models.Admin;
import topg.bimber_user_service.models.AdminVerificationToken;
import topg.bimber_user_service.models.NotificationEmail;
import topg.bimber_user_service.repository.AdminRepository;
import topg.bimber_user_service.repository.AdminVerificationRepository;

import java.util.Random;
import java.util.UUID;

import static topg.bimber_user_service.utils.ValidationUtils.isValidEmail;
import static topg.bimber_user_service.utils.ValidationUtils.isValidPassword;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;
    private final AdminVerificationRepository adminVerificationRepository;
    private final MailService mailService;

    @Override
    public UserCreatedDto createAdmin(UserRequestDto userRequestDto) {
        validateFields(userRequestDto.getEmail(), userRequestDto.getPassword());
        doesUserExists(userRequestDto.getEmail());
        Admin admin = modelMapper.map(userRequestDto, Admin.class);
        admin = adminRepository.save(admin);
        UserCreatedDto response = modelMapper.map(admin, UserCreatedDto.class);
        response.setMessage("Admin registered successfully");
        return response;
    }

    private void validateFields(String email, String password) {
        if (!isValidEmail(email)) throw new InvalidDetailsException("The email you entered is not correct");
        if (!isValidPassword(password))
            throw new InvalidDetailsException("Password must be between 8 and 16 characters long, including at least one uppercase letter, one lowercase letter, one number, and one special character (e.g., @, #, $, %, ^).");
    }

    private void doesUserExists(String email){
        adminRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundInDb(String.format("Admin with email: %s already exits", email)));
    }

    private boolean isEmailTaken(String email) {
        return adminRepository.findByEmail(email).isPresent();
    }


    private void sendActivationEmail(Admin admin, String token) {
        String activationUrl = "http://localhost:9090/api/v1/admin/accountVerification/" + token;
        String emailBody = String.format(
                "Thank you for signing up to our hotel, please click on the below URL to activate your account: %s",
                activationUrl
        );

        mailService.sendMail(new NotificationEmail(
                "Please activate your account",
                admin.getEmail(),
                emailBody
        ));
    }



    // Generates a unique user ID for the admin
    private String generateUserId() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomPart = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            int index = random.nextInt(alphanumeric.length());
            randomPart.append(alphanumeric.charAt(index));
        }
        return "Admin" + "_" + randomPart;
    }

    // Retrieves an admin by ID
    @Override
    public UserResponseDto getAdminById(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new UserNotFoundInDb("User with id " + adminId + " not found"));
        if (!admin.isEnabled()) {
            throw new IllegalStateException("Your account is not activated. Please activate your account.");
        }
        return new UserResponseDto(admin.getEmail(), admin.getUsername(), admin.getId());
    }

    // Updates admin details by ID
    @Override
    @Transactional
    public UserResponseDto editAdminById(UserAndAdminUpdateDto adminUpdateRequestDto, String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new UserNotFoundInDb("User with id " + adminId + " not found"));
        if (!admin.isEnabled()) {
            throw new IllegalStateException("Your account is not activated. Please activate your account.");
        }
        if (adminRepository.findByEmail(adminUpdateRequestDto.email()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken.");
        }
        if (adminUpdateRequestDto.email() != null) {
            admin.setEmail(adminUpdateRequestDto.email());
        }
        if (adminUpdateRequestDto.username() != null) {
            admin.setUsername(adminUpdateRequestDto.username());
        }
        adminRepository.save(admin);
        return new UserResponseDto(admin.getEmail(), admin.getUsername(), admin.getId());
    }

    // Deletes an admin by ID
    @Override
    public String deleteAdminById(String adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new UserNotFoundInDb("User with id " + adminId + " not found"));
        if (!admin.isEnabled()) {
            throw new IllegalStateException("Your account is not activated. Please activate your account.");
        }
        adminRepository.delete(admin);
        return "User with id " + adminId + " has been successfully deleted.";
    }

    // Generates a verification token for an admin
    private String generateVerificationToken(Admin admin) {
        String verificationToken = UUID.randomUUID().toString();
        AdminVerificationToken adminVerificationToken = new AdminVerificationToken();
        adminVerificationToken.setToken(verificationToken);
        adminVerificationToken.setAdmin(admin);
        adminVerificationRepository.save(adminVerificationToken);
        return verificationToken;
    }

    // Verifies the provided token and enables the admin account
    public void verifyToken(String token) {
        AdminVerificationToken verificationToken = adminVerificationRepository.findByToken(token)
                .orElseThrow(() -> new MailNotSentException("Invalid Token"));
        fetchAdminAndEnable(verificationToken);
    }

    // Enables the admin account linked to the verification token
    private void fetchAdminAndEnable(AdminVerificationToken verificationToken) {
        String email = verificationToken.getAdmin().getEmail();
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundInDb("Email not found"));
        admin.setEnabled(true);
        adminRepository.save(admin);
    }
}
