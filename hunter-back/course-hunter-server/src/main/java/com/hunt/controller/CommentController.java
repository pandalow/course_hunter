package com.hunt.controller;

import com.hunt.constant.MessageConstant;
import com.hunt.dto.CommentDTO;
import com.hunt.result.Result;
import com.hunt.service.CommentService;
import com.hunt.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Comment Controller */
@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Create Comment
     *
     * @param commentDTO cotains content, targetId, targetType
     */
    @PostMapping
    public Result<CommentVO> createComment(@RequestBody CommentDTO commentDTO) throws Exception {
        log.info("Request info: " + commentDTO);
        CommentVO commentVO = commentService.save(commentDTO);
        return Result.success(commentVO);
    }

    /**
     * Get comments by targetId and targetType
     * @param targetId Target entity ID
     * @param targetType Target entity type 1 = 'Teacher', 2 = 'Course'
     * @return List of CommentVO
     */
    @GetMapping
    public Result<List<CommentVO>> getComments(@RequestParam Long targetId, @RequestParam String targetType) {
        log.info("Get comments for targetId: {}, targetType: {}", targetId, targetType);
        List<CommentVO> comments = commentService.getComments(targetId, targetType);
        return Result.success(comments);
    }

    /**
     * Delete comments
     * @param id Comment Id
     * @return String message
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteComment(@PathVariable Long id){
        log.info("Request id: " + id);
        commentService.delete(id);

        return Result.success(MessageConstant.DELETE_SUCCESS);
    }
}
