package com.hunt.utils;

import com.hunt.exception.BaseException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** This class is utils for copy array which the different class has same properties
 *  Example: copy an entity from Course to CourseVO ( they have the same properties, such as name, id..)
 *           You can use code like: CourseVO courseVO = CopyUtil(course, CourseVO.class)
 *           the utils can automatically copy the attribute from course to courseVO
 */
public class CopyUtil {
    /**
     * Copy Entity
     *
     * @param source - the source object
     * @param targetClass - the target object
     * @param <T> - the target object type
     * @return - the target object
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) throws BaseException{
        if (source == null) {
            return null;
        }

        try {
            T instance = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (Exception e) {
            throw new BaseException();
        }
    }

    /**
     * Copy Arrays
     * Example: List<CourseVO> courseVO = CopyUtil.copyList(courses, CourseVO.class)
      */

    public static <E, T> List<T> copyList(List<E> sources, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<T>();
        }
        return sources.stream()
                .map(source -> copyProperties(source, targetClass))
                .collect(Collectors.toList());
    }
}