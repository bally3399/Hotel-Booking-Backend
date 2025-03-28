//package topg.bimber_user_service.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/comments")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//public class CommentController {
//
//    private final CommentServiceImpl commentServiceImpl;
//
//    @PostMapping("/add/{hotelId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<?> addComment(
//            @RequestParam String userId,
//            @PathVariable Long hotelId,
//            @RequestParam String content) {
//        try {
//            CommentResponseDto comment = commentServiceImpl.addComment(userId, hotelId, content);
//            return ResponseEntity.ok(comment);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/hotel/{hotelId}")
//    public ResponseEntity<List<CommentResponseDto>> getCommentsByHotel(@PathVariable Long hotelId) {
//        return ResponseEntity.ok(commentServiceImpl.getCommentsByHotel(hotelId));
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<?> getCommentsByUser(@PathVariable String userId) {
//        return ResponseEntity.ok(commentServiceImpl.getCommentsByUser(userId));
//    }
//
//    @DeleteMapping("/{hotelId}/{commentId}")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<?> deleteComment(@PathVariable Long hotelId, @PathVariable Long commentId,
//                                           @RequestParam String userId) {
//        try {
//            commentServiceImpl.deleteComment(hotelId, commentId, userId);
//            return ResponseEntity.ok("Comment deleted successfully.");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}
