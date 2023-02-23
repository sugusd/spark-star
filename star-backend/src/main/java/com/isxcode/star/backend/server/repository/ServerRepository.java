package com.isxcode.star.backend.server.repository;

import com.isxcode.star.backend.server.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<ServerEntity,String> {

}
