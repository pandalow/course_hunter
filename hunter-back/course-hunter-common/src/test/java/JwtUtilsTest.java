import com.hunt.entity.User;
import com.hunt.enumerate.Role;
import com.hunt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilsTest {
    private JwtUtils jwtUtils;
    private User testUser;

    @BeforeEach
    void setUp(){
        this.jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(
                jwtUtils,
                "SECRET_KEY",
                "MTY4YmI5N2VjYmI0ZDUwM2M0YWU1Y2I4YTM0YzVjZDY3Mjk2YmYyM2Q4ZGQyNTVhN2U3YjFhM2Y0ODk1MGExYg=="
        );
       ReflectionTestUtils.setField(
               jwtUtils,
               "EXPIRATION_TIME",
               86400000
       );
        this.testUser = User.builder()
                .googleId("google_12345")
                .email("test@gmail.com")
                .role(Role.User)
                .build();
    }

    @Test
    @DisplayName("Should generate authorized Token and extract correct claims")
    void shouldGenerateAndExtraction(){
        String tokens = jwtUtils.generateToken(testUser);
        System.out.println(tokens);

        assertThat(tokens.split("\\.")).hasSize(3).doesNotContain("");

        Claims claims = jwtUtils.extractAllClaims(tokens);
        assertThat(claims.getSubject()).isEqualTo("google_12345");
        assertThat(claims.get("email")).isEqualTo("test@gmail.com");
        assertThat(claims.get("role")).isEqualTo("User");
    }

}
