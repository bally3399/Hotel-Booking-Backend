package topg.bimber_user_service.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString

public class Token {

    @Id
    private String id;

    private String token;

    private String ownerEmail;

    private LocalDateTime timeCreated =  LocalDateTime.now();
}
