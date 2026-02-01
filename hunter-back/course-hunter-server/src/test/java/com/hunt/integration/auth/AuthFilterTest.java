package com.hunt.filter;

import com.hunt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class AuthFilterTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtUtils jwtUtils;

    @Test
    public void shouldAllowAccessWhenTokenIsValid() throws Exception {
        Claims claims = Jwts.claims()
                .subject("123456789")
                .add("role", "User")
                .build();

        // 2. 录制 Mock 行为
        when(jwtUtils.extractAllClaims(anyString())).thenReturn(claims);

        // 3. 执行请求并验证
        mockMvc.perform(get("/course")
                        .header("Authorization", "Bearer mock-token"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnForbiddenWhenTokenIsMissed() throws Exception {
        mockMvc.perform(get("/course")).andExpect(status().isForbidden());
    }
}



