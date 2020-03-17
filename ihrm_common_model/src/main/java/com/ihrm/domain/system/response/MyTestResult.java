package com.ihrm.domain.system.response;

import com.ihrm.domain.system.Test;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.domain.system.response
 * @Author: XuAn
 * @CreateTime: 2020-03-10 22:18
 * @Version: 1.0
 */
@Data
public class MyTestResult {

    private List<Test> tests = new ArrayList<>();

    public MyTestResult(List<Test> tests) {
        BeanUtils.copyProperties(tests,this);
    }
}
