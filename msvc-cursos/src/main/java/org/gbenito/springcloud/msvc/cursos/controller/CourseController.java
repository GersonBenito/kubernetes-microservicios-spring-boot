package org.gbenito.springcloud.msvc.cursos.controller;

import feign.FeignException;
import jakarta.validation.Valid;
import org.gbenito.springcloud.msvc.cursos.entity.Course;
import org.gbenito.springcloud.msvc.cursos.entity.UserEntity;
import org.gbenito.springcloud.msvc.cursos.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(){
        Map<String, Object> response = new HashMap<>();
        List<Course> courseList = courseService.findAll();
        response.put("Data", courseList);
        response.put("message", "Get entries success");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Optional<Course> courseOptional = courseService.findById(id);
        if(id == null){
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Course id was not be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if(courseOptional.isPresent()){
            response.put("Data", courseOptional.get());
            response.put("message", "Get entry success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.put("message", "User with id "+id+" not found");
        response.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody @Valid Course course){
        Map<String, Object> response = new HashMap<>();
        courseService.save(course);
        response.put("message", "Course save success");
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        if(id == null){
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Course id was not be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<Course> courseOptional = courseService.findById(id);
        if(courseOptional.isPresent()){
            courseService.deleteById(id);
            response.put("message", "Course with id "+id+" was delete");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", "Course with "+id+" not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateById(@RequestBody @Valid Course course, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "Course id was not be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Optional<Course> courseOptional = courseService.findById(id);
        if(courseOptional.isPresent()){
            Course courseUpdate = courseOptional.get();
            courseUpdate.setName(course.getName());
            courseService.save(courseUpdate);
            response.put("message", "Course with id "+id+" was update");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", "Course with "+id+" not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody UserEntity user, @PathVariable Long courseId){
        Optional<UserEntity> optionalUser;
        try{
            optionalUser = courseService.assignUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                        Collections
                             .singletonMap(
                                "message",
                                "No existe el usuario por id o error en la comunicacion: "+e.getMessage()
                             )
                    );
        }

        if(optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user, @PathVariable Long courseId){
        Optional<UserEntity> optionalUser;
        try{
            optionalUser = courseService.createUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Collections
                                    .singletonMap(
                                            "message",
                                            "Error al crear usuario o error en la comunicacion: "+e.getMessage()
                                    )
                    );
        }

        if(optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/unassign-user/{courseId}")
    public ResponseEntity<?> unassignUser(@RequestBody UserEntity user, @PathVariable Long courseId){
        Optional<UserEntity> optionalUser;
        try{
            optionalUser = courseService.unassignUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Collections
                                    .singletonMap(
                                            "message",
                                            "No existe el usuario por id o error en la comunicacion: "+e.getMessage()
                                    )
                    );
        }

        if(optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
        }

        return ResponseEntity.notFound().build();
    }
}
