package com.soft.system.dao;

import com.soft.domain.system.Test;
import com.soft.domain.system.TestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.system.dao
 * @Author: XuAn
 * @CreateTime: 2020-03-06 12:26
 * @Version: 1.0
 */
public interface TestDao extends JpaRepository<Test,String>, JpaSpecificationExecutor<Test> {

    List<Test> findByCompanyId(String companyId);
}
