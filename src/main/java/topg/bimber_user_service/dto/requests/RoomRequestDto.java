package topg.bimber_user_service.dto.requests;

import topg.bimber_user_service.models.RoomType;

import java.math.BigDecimal;

public record RoomRequestDto(
        RoomType roomType,
        BigDecimal price,
        Boolean available,
        Long hotelId
) {
}
