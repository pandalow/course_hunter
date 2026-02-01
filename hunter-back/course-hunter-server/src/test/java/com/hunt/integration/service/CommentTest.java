package com.hunt.integration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunt.dao.CommentDAO;
import com.hunt.dao.CourseDAO;
import com.hunt.dao.UserDAO;
import com.hunt.dto.CommentDTO;
import com.hunt.entity.Comment;
import com.hunt.entity.Course;
import com.hunt.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CommentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private Course testCourse;

    @BeforeEach
    public void setup() {
        System.out.println("DEBUG: Running setup method...");
        commentDAO.deleteAll();
        userDAO.deleteAll();
        courseDAO.deleteAll();

        testUser = new User();
        testUser.setGoogleId("google-123");
        testUser.setName("Integration User");
        testUser.setEmail("google123@gmail.com");
        userDAO.save(testUser);

        testCourse = new Course();
        testCourse.setTitle("Java");
        courseDAO.save(testCourse);
    }

    @Test
    @WithMockUser(username = "google-123")
    public void testSaveAndFetchComment() throws Exception {
        CommentDTO dto = new CommentDTO();
        dto.setContent("Integration Test Content");
        dto.setTargetId(testCourse.getId());
        dto.setTargetType("2"); // 2 = COURSE

        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("Integration Test Content"));

        List<Comment> comments = commentDAO.findAll();
        assertEquals(1, comments.size());
        assertEquals(testUser.getId(), comments.get(0).getUser().getId());
    }

    @Test
    @WithMockUser(username = "google-123")
    public void testDeleteComment_Success() throws Exception {
        // 1. 先往数据库存一个属于该用户的评论
        Comment comment = new Comment();
        comment.setContent("To be deleted");
        comment.setUser(testUser);
        comment.setCourse(testCourse);
        comment = commentDAO.save(comment);

        mockMvc.perform(delete("/comment/" + comment.getId()))
                .andExpect(status().isOk());

        Comment deletedComment = commentDAO.findById(comment.getId()).orElse(null);
        assertNotNull(deletedComment);
        assertTrue(deletedComment.isDeleted());
    }

    @Test
    @WithMockUser(username = "google-123")
    void testGetComments_Success() throws Exception {
        Comment c1 = new Comment();
        c1.setContent("Review 1");
        c1.setUser(testUser);
        c1.setCourse(testCourse);
        commentDAO.save(c1);

        mockMvc.perform(get("/comment") // 路径只写到 /comment
                        .param("targetId", testCourse.getId().toString()) // 对应 @RequestParam Long targetId
                        .param("targetType", "2")) // 对应 @RequestParam String targetType (假设 1 是 COURSE)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].content").value("Review 1"));
    }
}