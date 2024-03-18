package org.gbenito.springcloud.msvc.cursos.service;

import org.gbenito.springcloud.msvc.cursos.client.UserClientRest;
import org.gbenito.springcloud.msvc.cursos.entity.Course;
import org.gbenito.springcloud.msvc.cursos.entity.CourseUser;
import org.gbenito.springcloud.msvc.cursos.entity.UserEntity;
import org.gbenito.springcloud.msvc.cursos.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService{
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserClientRest clientRest;


    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<UserEntity> assignUser(UserEntity user, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isPresent()){
            System.out.println("user id: "+user.getId());
            UserEntity userEntity = clientRest.findById(user.getId());
            Course course = courseOptional.get();

            CourseUser courseUser = new CourseUser();

            courseUser.setUserId(userEntity.getId());
            course.addCourseUse(courseUser);
            courseRepository.save(course);

            return Optional.of(userEntity);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserEntity> createUser(UserEntity user, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isPresent()){
            UserEntity newUser = clientRest.save(user);
            Course course = courseOptional.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(newUser.getId());
            course.addCourseUse(courseUser);
            courseRepository.save(course);

            return Optional.of(newUser);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UserEntity> unassignUser(UserEntity user, Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isPresent()){
            UserEntity userEntity = clientRest.findById(user.getId());
            Course course = courseOptional.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userEntity.getId());

            course.removeCourseUser(courseUser);
            courseRepository.save(course);

            return Optional.of(userEntity);
        }
        return Optional.empty();
    }
}
