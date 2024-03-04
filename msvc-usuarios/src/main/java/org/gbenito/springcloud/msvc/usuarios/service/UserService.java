package org.gbenito.springcloud.msvc.usuarios.service;

import org.gbenito.springcloud.msvc.usuarios.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserEntity> findAll();
    public Optional<UserEntity> findById(Long id);

    public void save(UserEntity user);
    public void deleteById(Long id);
}
