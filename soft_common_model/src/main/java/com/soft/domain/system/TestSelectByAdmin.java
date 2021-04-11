package com.soft.domain.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.domain.system
 * @Author: XuAn
 * @CreateTime: 2021-04-07 16:36
 * @Version: 1.0
 */
@Data
public class TestSelectByAdmin {

    private String testId;

    private List<SimpleUser> tableData = new ArrayList<>();
}
