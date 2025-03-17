package topg.bimber_user_service.dto.responses;

public record UserCreatedDto(
        boolean success,
        String message,
        Object data
) {

}
