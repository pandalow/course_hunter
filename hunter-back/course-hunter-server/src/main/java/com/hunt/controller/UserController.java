package com.hunt.controller;

import com.hunt.dto.CourseRatingPageQueryDTO;
import com.hunt.dto.UserInfoDTO;
import com.hunt.dto.UserLoginDTO;
import com.hunt.dto.UserTokenDTO;
import com.hunt.result.PageResult;
import com.hunt.result.Result;
import com.hunt.service.CourseRatingService;
import com.hunt.service.UserService;
import com.hunt.vo.UserInfoVO;
import com.hunt.vo.CourseRatingVO;
import com.hunt.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseRatingService courseRatingService;

    /**
     * login API
     *
     * @param userLoginDTO user login info
     * @return Result<UserLoginVO>
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("user login：{}", userLoginDTO);
        UserLoginVO userLoginVO = userService.userLogin(userLoginDTO);
        // Set user details to security context
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginVO, null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("set principal on login: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return Result.success(userLoginVO);

    }

    /**
     * register API
     *
     * @param userLoginDTO user register info
     * @return Result success
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("user register：{}", userLoginDTO);
        userService.userRegister(userLoginDTO);
        return Result.success();
    }

    /**
     * register API
     *
     * @param token token for verification
     * @return Result success
     */
    @GetMapping("/verify/{token}")
    public Result<String> verify(@PathVariable String token) {
        log.info("user verify：{}", token);
        userService.userVerify(token);
        return Result.success();
    }

    /**
     * send verification email API
     */
    @PostMapping("/sendVerifyEmail")
    public Result<String> sendVerifyEmail() {
        Map<String, Object> userDetails = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailAddress = (String) userDetails.get("email");
        userService.sendVerifyEmail(emailAddress);
        return Result.success();
    }

    /**
     * change password API
     */
    @PutMapping("/password")
    public Result<String> changePassword(@RequestBody UserLoginDTO userLoginDTO) {
        // TODO: currently it requires login to change password, this better be changed to use email verification
        userService.changePassword(userLoginDTO);
        return Result.success();
    }

    /**
     * get user info API
     */
    @GetMapping("/userInfo")
    public Result<UserInfoVO> getCurrentUser() {
        // TODO: it might be merged with BaseContextHandler which Jambo provided
        Map<String, Object> userDetails = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userIdStr = (String) userDetails.get("id");
        Long userId = Long.parseLong(userIdStr);
        UserInfoVO userInfoVO = userService.getUserInfo(userId);
        return Result.success(userInfoVO);
    }

    /**
     * update user info API
     */
    @PutMapping("/userInfo")
    public Result<String> updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        Map<String, Object> userDetails = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userIdStr = (String) userDetails.get("id");
        Long userId = Long.parseLong(userIdStr);
        userService.updateUserInfo(userId, userInfoDTO);
        return Result.success();
    }

    @GetMapping("/course-ratings")
    public Result<PageResult<CourseRatingVO>> getCourseRatings(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                               @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                               @RequestParam(required = false, defaultValue = "desc") String sortDirection,
                                                               @RequestParam(required = false, defaultValue = "createTime") String sortBy) {
        UserTokenDTO userDetails = (UserTokenDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        log.info("get course ratings by user id: {}", userId);

        CourseRatingPageQueryDTO queryDTO = CourseRatingPageQueryDTO.builder()
                .page(page)
                .pageSize(pageSize)
                .sortDirection(sortDirection)
                .sortBy(sortBy)
                .build();

        PageResult<CourseRatingVO> courseRatingVOPageResult = courseRatingService.getByUserId(userId, queryDTO);
        return Result.success(courseRatingVOPageResult);
    }
}
