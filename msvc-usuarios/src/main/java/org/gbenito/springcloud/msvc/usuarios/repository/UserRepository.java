package org.gbenito.springcloud.msvc.usuarios.repository;

import org.gbenito.springcloud.msvc.usuarios.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    public Optional<UserEntity> findByEmail(String email);
}
