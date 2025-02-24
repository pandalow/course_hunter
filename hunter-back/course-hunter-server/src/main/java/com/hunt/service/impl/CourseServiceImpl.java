package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.CourseDAO;
import com.hunt.dao.CourseUGCDAO;
import com.hunt.dao.TeacherDAO;
import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.entity.Course;
import com.hunt.entity.CourseUGC;
import com.hunt.entity.ResultItem;
import com.hunt.result.PageResult;
import com.hunt.service.CourseService;
import com.hunt.utils.CopyUtil;
import com.hunt.vo.CourseCardVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private CourseUGCDAO courseUGCDAO;
    @Autowired
    private FastApiClientServiceImpl fastApiClientServiceImpl;


    @Override
    public Course getCourseById(Long id) {
        Optional<Course> course = courseDAO.findById(id);
        return course.orElseThrow(() -> new EntityNotFoundException(ExceptionMessageConstant.COURSE_NOT_FOUND));
    }

    /**
     * This method is used to return the information displayed by the front-end card.
     *
     * @param pageQueryDTO - the query info, including the page number, page size, sort direction, sort id, and program id.
     * @return PageResult contend the pageSize and course content;
     */
    @Override
    public PageResult<CourseCardVO> getCourses(CoursePageQueryDTO pageQueryDTO) {

        // Setting sort direction and sort id;
        Sort sort = Sort.by(
                Sort.Direction.fromString(pageQueryDTO.getSortDirection()), pageQueryDTO.getSortBy());
        // Setting pageQuery params
        Pageable pageable = PageRequest.of(
                pageQueryDTO.getPage() - 1, pageQueryDTO.getPageSize(), sort);

        Page<CourseUGC> courses;
        log.info("query course: {}", pageQueryDTO);


        courses = courseUGCDAO.findAll(pageable);


        // Get the corresponding teacher of the course, and convert it to a pageResult.
        List<CourseCardVO> courseCardVOs = courses
                .getContent() // Get courseUGC list
                .stream()
                .map(courseUGC -> { // Map course to courseCard and adding teachers name listc
                    CourseCardVO cardVO = CopyUtil
                            .copyProperties(courseDAO.
                                            findById(courseUGC.getId())
                                            .orElseThrow(() ->
                                                    new EntityNotFoundException(ExceptionMessageConstant.COURSE_NOT_FOUND)),
                                    CourseCardVO.class);

                    cardVO.setComments(courseUGC.getLatestComment());
                    cardVO.setRating(courseUGC.getRating());
                    cardVO.setTeachers(teacherDAO.getTeacherNamesByCourses(courseUGC.getId()));
                    return cardVO;
                }).collect(Collectors.toList());

        return new PageResult<CourseCardVO>(courses.getNumberOfElements(), courseCardVOs);
    }

    @Override
    public PageResult<CourseCardVO> getCourseByQuery(String query){
       List<ResultItem> results =
               fastApiClientServiceImpl
                       .getFastApiResult(query)
                       .map(fastApiResponse -> fastApiResponse.getResults())
                       .block();
       if (results == null || results.isEmpty()){
           return new PageResult<CourseCardVO>(0,new ArrayList<CourseCardVO>());
        }


       ArrayList<Course> courses = new ArrayList<>();
       for(ResultItem p : results){
           Course course = getCourseById(p.getId());
           courses.add(course);
       }

         List<CourseCardVO> courseCardVOs = courses
                .stream()
                .map(course -> {
                     CourseCardVO cardVO = CopyUtil.copyProperties(course, CourseCardVO.class);
                     cardVO.setTeachers(teacherDAO.getTeacherNamesByCourses(course.getId()));
                     return cardVO;
                }).collect(Collectors.toList());

       return new PageResult<CourseCardVO>(courseCardVOs.size(), courseCardVOs);
    }




}
