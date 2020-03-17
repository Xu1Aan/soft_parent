package com.ihrm.system.dao;

import com.ihrm.domain.system.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.system.dao
 * @Author: XuAn
 * @CreateTime: 2020-03-06 12:26
 * @Version: 1.0
 */
public interface TestDao extends JpaRepository<Test,String>, JpaSpecificationExecutor<Test> {

}
