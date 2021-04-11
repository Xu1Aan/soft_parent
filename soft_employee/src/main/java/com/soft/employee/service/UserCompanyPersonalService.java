package com.soft.employee.service;

import com.soft.domain.employee.UserCompanyPersonal;
import com.soft.domain.employee.response.EmployeeReportResult;
import com.soft.employee.dao.UserCompanyPersonalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/10/19 9:52
 * Description:
 */
@Service
public class UserCompanyPersonalService {
    @Autowired
    private UserCompanyPersonalDao userCompanyPersonalDao;

    public void save(UserCompanyPersonal personalInfo) {
        userCompanyPersonalDao.save(personalInfo);
    }

    public UserCompanyPersonal findById(String userId) {
        return userCompanyPersonalDao.findByUserId(userId);
    }

    public List<EmployeeReportResult> findByReport(String companyId,String month) {
        return userCompanyPersonalDao.findByReport(companyId,month+"%");
    }

}
