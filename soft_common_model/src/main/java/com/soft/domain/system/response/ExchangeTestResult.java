package com.soft.domain.system.response;

import com.soft.domain.system.ExchangeTest;
import com.soft.domain.system.Test;
import com.soft.domain.system.TestUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.domain.system.response
 * @Author: XuAn
 * @CreateTime: 2021-04-09 18:36
 * @Version: 1.0
 */
@Data
public class ExchangeTestResult {
    List<ExchangeTest> exchangeTests= new ArrayList<>();

}
