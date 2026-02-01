package com.hunt.unit.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.hunt.dao.UserDAO;
import com.hunt.entity.User;
import com.hunt.service.impl.UserServiceImpl;
import com.hunt.utils.JwtUtils;
import com.hunt.vo.UserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnitTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private GoogleIdTokenVerifier verifier;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private UserServiceImpl userService;

    private String mockCode;
    private String mockEmail;
    private User existingUser;
    private GoogleIdToken mockIdToken;
    private GoogleIdToken.Payload payload;


    @BeforeEach
    void setUp(){
        this.mockCode = "google_mock_code";
        this.mockEmail = "test@gmail.com";
        this.existingUser = new User();
        this.existingUser.setId(1L);
        this.existingUser.setEmail(mockEmail);
        this.existingUser.setName("HUNT");

        this.mockIdToken = mock(GoogleIdToken.class);
        this.payload = new GoogleIdToken.Payload();
        payload.setEmail(mockEmail);
        payload.setSubject("google_123");
    }


    @Test
    @DisplayName("Google Login test")
    public void testGoogleLogin() throws Exception {
        // Preparing the verify processing
        when(mockIdToken.getPayload()).thenReturn(payload);
        when(verifier.verify(mockCode)).thenReturn(mockIdToken);

        when(userDAO.findByEmail(mockEmail)).thenReturn(Optional.of(existingUser));
        when(jwtUtils.generateToken(any(User.class))).thenReturn("mock-jwt-code");

        UserVO userVO = userService.handleGoogleOAuth(mockCode);

        //Assertion
        assertThat(userVO.getToken()).isEqualTo("mock-jwt-code");
        assertThat(userVO.getName()).isEqualTo("HUNT");
        assertThat(userVO.getId()).isEqualTo(1L);

        //Assertion of Database has not been invoked
        verify(userDAO, never()).save(any());
    }

    @Test
    @DisplayName("test account not existing and registering")
    public void testGoogleLoginWithoutRegister() throws Exception {

        when(mockIdToken.getPayload()).thenReturn(payload);
        when(verifier.verify(mockCode)).thenReturn(mockIdToken);

        when(userDAO.findByEmail(mockEmail)).thenReturn(Optional.empty());
        when(jwtUtils.generateToken(any(User.class))).thenReturn(("mock-jwt-code"));

        //Mock the database to return a user object with a simulated generated ID
        when(userDAO.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(2L);
            return savedUser;
        });

        UserVO userVo = userService.handleGoogleOAuth(mockCode);

        assertThat(userVo.getToken()).isEqualTo("mock-jwt-code");
        assertThat(userVo.getId()).isEqualTo(2L);

        //Assertion of Database is being called;
        verify(userDAO, times(1)).save(any(User.class));
    }
}
