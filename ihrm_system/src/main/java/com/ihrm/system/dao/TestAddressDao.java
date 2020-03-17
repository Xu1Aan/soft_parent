package com.ihrm.system.dao;

import com.ihrm.domain.system.TestAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.system.dao
 * @Author: XuAn
 * @CreateTime: 2020-03-06 20:36
 * @Version: 1.0
 */
public interface TestAddressDao extends JpaRepository<TestAddress,String>, JpaSpecificationExecutor<TestAddress> {
    List<TestAddress> findByTestId(String id);

    void deleteByTestId(String id);
}
