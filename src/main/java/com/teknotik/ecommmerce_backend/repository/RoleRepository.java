package com.teknotik.ecommmerce_backend.repository;

import com.teknotik.ecommmerce_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    @Query("SELECT r FROM Role r WHERE r.authority =:authority")
    Optional<Role> findByAuthority(String authority);

}