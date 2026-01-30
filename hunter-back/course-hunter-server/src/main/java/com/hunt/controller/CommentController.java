package com.hunt.controller;

import com.hunt.dto.CommentDTO;
import com.hunt.result.Result;
import com.hunt.service.CommentService;
import com.hunt.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Create Comment
     * @param commentDTO cotains content, targetId, targetType
     */
    @PostMapping
    public Result<CommentVO> createComment(@RequestBody CommentDTO commentDTO) throws Exception {
        log.info("Request info: " + commentDTO);
        CommentVO commentVO = commentService.save(commentDTO);
        return Result.success(commentVO);
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

        return Result.success("Comment deleted successfully");
    }
}
