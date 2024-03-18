package org.gbenito.springcloud.msvc.cursos.service;

import org.gbenito.springcloud.msvc.cursos.entity.Course;
import org.gbenito.springcloud.msvc.cursos.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    public List<Course> findAll();
    public Optional<Course> findById(Long id);
    public void save(Course course);
    public void deleteById(Long id);
    public Optional<UserEntity> assignUser(UserEntity user, Long courseId);
    public Optional<UserEntity> createUser(UserEntity user, Long courseId);
    public Optional<UserEntity> unassignUser(UserEntity user, Long courseId);
}
