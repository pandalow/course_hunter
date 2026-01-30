package com.hunt.service;

import com.hunt.dao.CommentDAO;
import com.hunt.dao.CourseDAO;
import com.hunt.dao.TeacherDAO;
import com.hunt.dao.UserDAO;
import com.hunt.dto.CommentDTO;
import com.hunt.entity.Comment;
import com.hunt.entity.Course;
import com.hunt.entity.User;
import com.hunt.service.impl.CommentServiceImpl;
import com.hunt.vo.CommentVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CommentServiceImplUnitTest {

    @Mock
    private CommentDAO commentDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private CourseDAO courseDAO;
    @Mock
    private TeacherDAO teacherDAO;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    private User mockUser;
    private String testGoogleId = "google-123";

    @BeforeEach
    void setUp() {
        //
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setGoogleId(testGoogleId);

        // 模拟 SecurityContext
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(testGoogleId);
    }

    @Test
    @DisplayName("Save Course Successfully")
    void saveCourseComment_Success() throws Exception {

        CommentDTO dto = new CommentDTO();
        dto.setContent("Great Course!");
        dto.setTargetId(101L);
        dto.setTargetType("2"); // 假设 2 是 COURSE

        when(userDAO.findByGoogleId(testGoogleId)).thenReturn(Optional.of(mockUser));
        when(courseDAO.findById(101L)).thenReturn(Optional.of(new Course()));

        CommentVO result = commentService.save(dto);

        assertNotNull(result);
        verify(commentDAO, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("Delete Comment when is not user")
    void deleteComment_Unauthorized_ThrowsException() {
        User otherUser = new User();
        otherUser.setGoogleId("other-google-id");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(otherUser);

        when(commentDAO.findById(1L)).thenReturn(Optional.of(comment));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            commentService.delete(1L);
        });

        assertEquals("You are not authorized to delete this comment", exception.getMessage());
        verify(commentDAO, never()).save(any());
    }

    @Test
    @DisplayName("Delete success")
    void deleteComment_Success() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(mockUser);
        comment.setDeleted(false);

        when(commentDAO.findById(1L)).thenReturn(Optional.of(comment));

        commentService.delete(1L);

        assertTrue(comment.isDeleted());
        verify(commentDAO, times(1)).save(comment);
    }
}