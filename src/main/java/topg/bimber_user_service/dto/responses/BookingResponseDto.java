package topg.bimber_user_service.dto.responses;

import java.time.LocalDate;

public record BookingResponseDto(
        Long bookingId,
        String userId,
        Long roomId,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        boolean isPaid
)  {


}