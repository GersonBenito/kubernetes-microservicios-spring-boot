package org.gbenito.springcloud.msvc.cursos.service;

import org.gbenito.springcloud.msvc.cursos.entity.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    public List<Course> findAll();
    public Optional<Course> findById(Long id);
    public void save(Course course);
    public void deleteById(Long id);
}
