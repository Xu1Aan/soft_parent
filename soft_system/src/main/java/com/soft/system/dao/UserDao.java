package com.soft.system.dao;

import com.soft.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

    public User findByMobile(String mobile);

    boolean existsByUsernameAndMobile(String username, String mobile);

    User findByUsernameAndMobile(String username, String mobile);

    List<User> findByCompanyId(String companyId);
}
