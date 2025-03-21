package topg.bimber_user_service.dto.responses;


import topg.bimber_user_service.models.RoomType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record RoomResponseDto (
        Long id,
        RoomType roomType,
        BigDecimal price,
        Boolean available,
        List<String> pictureUrls
) implements Serializable {
    private static final long serialVersionUID = 1L;

}

