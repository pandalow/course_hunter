package com.hunt.service.impl;

import com.hunt.constant.ExceptionMessageConstant;
import com.hunt.dao.CourseDAO;
import com.hunt.dao.TeacherDAO;
import com.hunt.dto.CoursePageQueryDTO;
import com.hunt.entity.Course;
import com.hunt.entity.Teacher;
import com.hunt.vo.ResultItem;
import com.hunt.result.PageResult;
import com.hunt.service.CourseService;
import com.hunt.utils.CopyUtil;
import com.hunt.vo.CourseVO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO;
    private final TeacherDAO teacherDAO;
    private final FastApiClientServiceImpl fastApiClientServiceImpl;

    /**
     * Get Course Entity By ID
     * @param id course Id
     * @return Course
     */
    @Override
    public Course getCourseById(Long id) {
        Optional<Course> course = courseDAO.findById(id);
        return course.orElseThrow(() ->
                new EntityNotFoundException(ExceptionMessageConstant.COURSE_NOT_FOUND));
    }

    /**
     * This method is used to return the information displayed by the front-end card.
     *
     * @param dto - the query info, including the page number, page size, sort direction, sort id, and program id.
     * @return PageResult contend the pageSize and course content;
     */
    @Override
    @Transactional(readOnly = true)
    public PageResult<CourseVO> getCourses(CoursePageQueryDTO dto) {
        // Generate
        Sort sort = Sort.by(Sort.Direction.fromString(dto.getSortDirection()),dto.getSortBy());
        Pageable pageable = PageRequest.of(dto.getPage()-1, dto.getPageSize(), sort);

        Page<Course> coursePage = courseDAO.findAll(pageable);

        List<CourseVO> vos = coursePage.getContent().stream().map(
                course -> {
                    CourseVO vo = CopyUtil.copyProperties(course, CourseVO.class);

                    List<String> teacherNames = course.getTeachers().stream()
                            .map(Teacher::getName)
                            .collect(Collectors.toList());

                    vo.setTeachers(teacherNames);
                    if(course.getInstitution() != null){
                        vo.setInstitutionName(course.getInstitution().getName());
                    }
                    if(course.getCountry() != null){
                        vo.setCountryName(course.getCountry().getName());
                    }

                    vo.setRating(course.getAvgScore() != null ? course.getAvgScore(): 0.0);
                    vo.setComments(course.getLatestComment() != null? course.getLatestComment() : "Waiting for more reviews");

                    return vo;
                }).collect(Collectors.toList());

        return new PageResult<>(coursePage.getTotalElements(), vos);
    }

    /**
     *
     *
     * @param query
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PageResult<CourseVO> getCourseByQuery(String query){
        List<ResultItem> results =
               fastApiClientServiceImpl
                       .getFastApiResult(query)
                       .map(fastApiResponse -> fastApiResponse.getResults())
                       .block();
        if (results == null || results.isEmpty()){
           return new PageResult<CourseVO>(0,new ArrayList<CourseVO>());
        }

        // Extract Id
        List<Long> ids = results.stream().map(ResultItem::getId).
                collect(Collectors.toList());

        // Query all Course
        List<Course> courses = courseDAO.findAllByIdIn(ids);

        Map<Long, Course> courseMap = courses.stream()
                .collect(Collectors.toMap(Course::getId, c->c));

        List<CourseVO> courseVOs = ids.stream()
                .filter(courseMap::containsKey)
                .map(id->{
                    Course course = courseMap.get(id);
                    CourseVO vo = CopyUtil.copyProperties(course, CourseVO.class);

                    vo.setTeachers(course.getTeachers().stream().map(Teacher::getName).toList());

                    if(course.getInstitution() != null){
                        vo.setInstitutionName(course.getInstitution().getName());
                    }
                    if(course.getCountry() != null){
                        vo.setCountryName(course.getCountry().getName());
                    }

                    vo.setRating(course.getAvgScore() != null ? course.getAvgScore() : 0.0);
                    vo.setComments(course.getLatestComment() != null ? course.getLatestComment() : "No reviews");
                    return vo;
                }).collect(Collectors.toList());

       return new PageResult<>(courseVOs.size(), courseVOs);
    }
}
