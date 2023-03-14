package com.isxcode.star.backend.datasource.repository;

import com.isxcode.star.backend.datasource.pojo.DatasourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DatasourceRepository extends JpaRepository<DatasourceEntity,String> {

    DatasourceEntity findFirstByName(String name);

    void deleteAllByIdIn(List<String> ids);
}
