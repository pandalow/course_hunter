package com.hunt.service;

import com.hunt.dao.CourseDAO;
import com.hunt.dao.RatingDAO;
import com.hunt.dao.UserDAO;
import com.hunt.dto.RatingDTO;
import com.hunt.entity.Course;
import com.hunt.entity.Rating;
import com.hunt.entity.User;
import com.hunt.exception.EntityNotFoundException;
import com.hunt.service.impl.RatingServiceImpl;
import com.hunt.vo.RatingVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // 整个类都变宽容
public class RatingServiceImplUnitTest {

    @Mock
    private RatingDAO ratingDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private CourseDAO courseDAO;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private RatingServiceImpl ratingService;

    private void mockUserAuthentication(String googleId) {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(googleId);
        SecurityContextHolder.setContext(securityContext);
    }
    @Test
    void saveShouldReturnVOWhenSuccessful() throws Exception {

        String googleId = "google-123";
        User mockUser = new User(); mockUser.setId(1L); mockUser.setGoogleId(googleId);
        Course mockCourse = new Course(); mockCourse.setId(100L);
        RatingDTO dto = new RatingDTO(); dto.setTargetId(100L); dto.setScore(5); dto.setContent("Good!");

        mockUserAuthentication(googleId);
        when(userDAO.findByGoogleId(googleId)).thenReturn(Optional.of(mockUser));
        when(courseDAO.findById(100L)).thenReturn(Optional.of(mockCourse));
        when(ratingDAO.save(any(Rating.class))).thenAnswer(i -> i.getArguments()[0]);

        RatingVO result = ratingService.save(dto);

        assertNotNull(result);
        assertEquals("Good!", result.getContent());
        verify(ratingDAO, times(1)).save(any(Rating.class));
    }

    @Test
    void updateShouldThrowExceptionWhenUserNotOwner() {
        String googleId = "hacker-id";
        User hacker = new User(); hacker.setId(999L); // Hacker ID
        User owner = new User(); owner.setId(1L);     // Author ID

        Rating existingRating = new Rating();
        existingRating.setId(50L);
        existingRating.setUser(owner);

        RatingDTO updateDto = new RatingDTO();
        updateDto.setTargetId(50L);

        mockUserAuthentication(googleId);
        when(userDAO.findByGoogleId(googleId)).thenReturn(Optional.of(hacker));
        when(ratingDAO.findById(50L)).thenReturn(Optional.of(existingRating));

        assertThrows(RuntimeException.class, () -> ratingService.update(updateDto));
    }

    @Test
    void deleteShouldInvokeDeleteWhenUserIsOwner() {
        String googleId = "owner-123";
        User owner = new User(); owner.setId(1L);

        Rating existingRating = new Rating();
        existingRating.setId(500L);
        existingRating.setUser(owner);

        mockUserAuthentication(googleId);
        when(userDAO.findByGoogleId(googleId)).thenReturn(Optional.of(owner));
        when(ratingDAO.findById(500L)).thenReturn(Optional.of(existingRating));

        assertDoesNotThrow(() -> ratingService.delete(500L));

        verify(ratingDAO, times(1)).delete(existingRating);
    }

    @Test
    void deleteShouldThrowExceptionWhenRatingNotFound() {
        String googleId = "any-user";
        User user = new User();

        mockUserAuthentication(googleId);
        when(userDAO.findByGoogleId(googleId)).thenReturn(Optional.of(user));
        when(ratingDAO.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ratingService.delete(999L));
        verify(ratingDAO, never()).delete(any());
    }

    @Test
    void getRatings_ShouldReturnVOList_WhenCourseExists() {
        Long courseId = 100L;

        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setAvatar("http://avatar.com/1");

        Rating rating1 = new Rating();
        rating1.setId(1L);
        rating1.setContent("Review 1");
        rating1.setScore(5);
        rating1.setUser(user);

        Rating rating2 = new Rating();
        rating2.setId(2L);
        rating2.setContent("Review 2");
        rating2.setScore(4);
        rating2.setUser(user);

        when(ratingDAO.findByCourseId(courseId)).thenReturn(List.of(rating1, rating2));

        List<RatingVO> result = ratingService.getRatings(courseId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Review 1", result.get(0).getContent());
        assertEquals("Test User", result.get(0).getUserName()); // 验证 covertToVO 逻辑

        verify(ratingDAO, times(1)).findByCourseId(courseId);
    }

}
