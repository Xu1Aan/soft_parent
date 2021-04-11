package com.soft.domain.system;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.domain.system
 * @Author: XuAn
 * @CreateTime: 2021-04-08 17:19
 * @Version: 1.0
 */
@Data
public class Users {
    private List<User> userInfo = new ArrayList<User>();

    private Integer taste;
}
