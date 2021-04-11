package com.soft.domain.company.response;

import com.soft.domain.company.Company;
import com.soft.domain.company.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeptListResult {

    private String companyId;
    private String companyName;
    private String companyManage;//联系人
    private List<Department> depts;

    public DeptListResult(Company company,List depts) {
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManage = company.getLegalRepresentative();//联系人
        this.depts = depts;
    }
}
