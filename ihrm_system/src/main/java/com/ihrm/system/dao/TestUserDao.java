package com.ihrm.system.dao;

import com.ihrm.domain.system.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.system.dao
 * @Author: XuAn
 * @CreateTime: 2020-03-06 20:42
 * @Version: 1.0
 */
public interface TestUserDao extends JpaRepository<TestUser,String>, JpaSpecificationExecutor<TestUser> {

    void deleteByTestId(String id);

    List<TestUser> findByTestId(String id);

    List<TestUser> findByTestIdAndUserId(String testId, String userId);

    List<TestUser> findByUserId(String id);

    Integer deleteByTestIdAndUserId(String testId, String userId);

    boolean existsByTestIdAndUserId(String testId, String userId);
}
