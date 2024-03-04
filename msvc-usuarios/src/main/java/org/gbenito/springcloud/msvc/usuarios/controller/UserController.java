package org.gbenito.springcloud.msvc.usuarios.controller;

import org.gbenito.springcloud.msvc.usuarios.entity.UserEntity;
import org.gbenito.springcloud.msvc.usuarios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(){
        Map<String, Object> response = new HashMap<>();
        List<UserEntity> userList = userService.findAll();
        response.put("Data", userList);
        response.put("message", "Get entries success");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Optional<UserEntity> userEntity = userService.findById(id);
        if(id == null){
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "User id was not null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(userEntity.isPresent()){
            response.put("Data", userEntity.get());
            response.put("message", "Get entry success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.put("message", "User with id "+id+" not found");
        response.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> save(@RequestBody UserEntity userEntity){
        Map<String, String> response = new HashMap<>();
        userService.save(userEntity);
        response.put("message", "User save success");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "User id was not null");
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<UserEntity> userEntity = userService.findById(id);

        if(userEntity.isPresent()){
            userService.deleteById(id);
            response.put("message", "User delete success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.put("message", "User with id "+id+" not found");
        response.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@RequestBody UserEntity user, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "User id was not null");
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<UserEntity> userOptional = userService.findById(id);
        if(userOptional.isPresent()){
            UserEntity userUpdate = userOptional.get();
            userUpdate.setName(user.getName());
            userUpdate.setEmail(user.getEmail());
            userUpdate.setPassword(user.getPassword());

            userService.save(userUpdate);
            response.put("message", "User update success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.put("message", "User with id "+id+" not found");
        response.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
