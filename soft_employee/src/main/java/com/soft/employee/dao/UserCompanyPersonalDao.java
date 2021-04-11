package com.soft.employee.dao;

import com.soft.domain.employee.UserCompanyPersonal;
import com.soft.domain.employee.response.EmployeeReportResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 */
public interface UserCompanyPersonalDao extends JpaRepository<UserCompanyPersonal, String>, JpaSpecificationExecutor<UserCompanyPersonal> {

    UserCompanyPersonal findByUserId(String userId);

    //ON a.user_id = b.user_id
    //WHERE a.time_of_entry LIKE '2018-02%' OR (b.resignation_time LIKE "2018-02%")
    @Query(value="select new com.soft.domain.employee.response.EmployeeReportResult(a,b) from UserCompanyPersonal a " +
            "LEFT JOIN EmployeeResignation b on a.userId=b.userId where a.companyId=?1 and a.timeOfEntry like?2 or (" +
            "b.resignationTime like ?2)")
    List<EmployeeReportResult> findByReport(String companyId,String month);
}