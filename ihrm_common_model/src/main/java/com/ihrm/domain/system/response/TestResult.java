package com.ihrm.domain.system.response;

import com.ihrm.domain.system.Test;
import com.ihrm.domain.system.TestAddress;
import com.ihrm.domain.system.TestUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.domain.system.response
 * @Author: XuAn
 * @CreateTime: 2020-03-06 12:53
 * @Version: 1.0
 */
@Getter
@Setter
public class TestResult implements Serializable {

    private static final long serialVersionUID = 1652009339224289921L;
    @Id
    private String id;

    private String date;

    private String time;

    private String type;

    private String subject;

    private String remain;

    private String companyId;

    private Integer number;

    private String address;

    private List<UserSimpleResult> simpleResults = new ArrayList<>();

    private List<TestUser> testUsers = new ArrayList<>();

    private List<TestAddress> testAddresses = new ArrayList<>();

    public TestResult(Test test) {
        BeanUtils.copyProperties(test,this);
    }
}
