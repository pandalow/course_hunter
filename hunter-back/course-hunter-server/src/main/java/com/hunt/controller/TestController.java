package com.hunt.controller;

import com.hunt.result.PageResult;
import com.hunt.result.Result;
import com.hunt.service.CourseService;
import com.hunt.vo.CourseCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private CourseService courseService;

    //    @GetMapping("/hello")
//    public Map test(){
//        Map cool;
//        cool = courseService.getCourseVector();
//        return cool;
//    }
    @GetMapping("/findcourse")
    public Result<PageResult<CourseCardVO>> searchCourse(@RequestParam("query") String query) {
        PageResult<CourseCardVO> courses = courseService.getCourseByQuery(query);
        return Result.success(courses);
    }
}
