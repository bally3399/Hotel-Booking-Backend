package topg.bimber_user_service.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDetailsRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private String email;
    private String newEmail;

}
