package com.soft.employee.service;

import com.soft.domain.employee.EmployeePositive;
import com.soft.employee.dao.PositiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IDEA
 * Author:xzengsf
 * Date:2018/10/22 15:23
 * Description:
 */
@Service
public class PositiveService {
    @Autowired
    private PositiveDao positiveDao;

    public EmployeePositive findById(String uid, Integer status) {
        EmployeePositive positive = positiveDao.findByUserId(uid);
        if (status != null && positive != null) {
            if (positive.getEstatus() != status) {
                positive = null;
            }
        }
        return positive;
    }

    public EmployeePositive findById(String uid) {
        return positiveDao.findByUserId(uid);
    }

    public void save(EmployeePositive positive) {
        positive.setCreateTime(new Date());
        positive.setEstatus(1);//未执行
        positiveDao.save(positive);
    }
}
