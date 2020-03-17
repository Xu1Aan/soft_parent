package com.ihrm.domain.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.domain.system
 * @Author: XuAn
 * @CreateTime: 2020-03-06 19:55
 * @Version: 1.0
 */
@Data
public class TestAddressTaste {

    private String id;

    private Date date;

    private String time;

    private String type;

    private String subject;

    private String remain;

    private Integer number;

    private Integer originalNumber;

    private String companyId;

    private List<String> teacher = new ArrayList<>();

    private List<String> address = new ArrayList<>();

    private List<TestAddress> testAddresses = new ArrayList<>();
}
