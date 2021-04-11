package com.soft.domain.system.response;

import com.soft.domain.system.TestInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.domain.system.response
 * @Author: XuAn
 * @CreateTime: 2021-04-04 14:48
 * @Version: 1.0
 */
@Getter
@Setter
public class TestInfoResult {

    private List<TestInfo> testInfo = new ArrayList<>();
}
