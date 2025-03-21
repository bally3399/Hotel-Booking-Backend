package topg.bimber_user_service.exceptions;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String roomNotFound) {
        super(roomNotFound);
    }
}
