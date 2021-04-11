package com.soft.system.dao;

import com.soft.domain.system.TestInfo;
import com.soft.domain.system.TestUser;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

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

    @Query(value = "SELECT bs_user.username, bs_address.address,bs_user.mobile,bs_user.id,bs_user_test.test_id " +
            "FROM bs_user_test " +
            "INNER JOIN bs_address ON bs_user_test.address_id = bs_address.id " +
            "INNER JOIN bs_user ON bs_user_test.user_id= bs_user.id " +
            "WHERE  bs_user_test.test_id= ?1 " +
            "ORDER BY bs_address.address ASC ",nativeQuery = true)
    List<Object> slectTestInfo(String id);

    List<TestUser> findByExchangeTeacherId(String userId);
}
