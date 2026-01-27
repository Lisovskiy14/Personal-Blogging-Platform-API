package com.example.blogging.repository;

import com.example.blogging.repository.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    boolean existsByName(String name);

    Optional<RoleEntity> findByName(String name);

    RoleEntity findByParent(RoleEntity parent);

    @Query("SELECT r FROM RoleEntity r LEFT JOIN FETCH r.permissions")
    Set<RoleEntity> findAllWithPermissions();
}
