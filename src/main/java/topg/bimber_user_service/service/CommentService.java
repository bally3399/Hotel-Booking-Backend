package topg.bimber_user_service.service;

import topg.bimber_user_service.dto.responses.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto addComment(String userId, Long hotelId, String content);

    boolean containsProhibitedWords(String content);

    List<CommentResponseDto> getCommentsByHotel(Long hotelId);

    CommentResponseDto getCommentsByUser(String userId);

    String deleteComment(Long hotelId, Long commentId, String userId);
}
