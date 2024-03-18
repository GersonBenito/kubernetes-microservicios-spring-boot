package org.gbenito.springcloud.msvc.cursos.client;

import org.gbenito.springcloud.msvc.cursos.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-usuarios", url = "http://localhost:8001/api/v1/user") // Nombre del microservicio y url
public interface UserClientRest {

    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable Long id);

    @PostMapping
    public UserEntity save(@RequestBody UserEntity user);
}
