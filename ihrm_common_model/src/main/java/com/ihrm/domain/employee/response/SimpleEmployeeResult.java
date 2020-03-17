package com.ihrm.domain.employee.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.domain.employee.response
 * @Author: XuAn
 * @CreateTime: 2020-03-06 14:46
 * @Version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SimpleEmployeeResult {
    private String userId;
    private String username;

    public SimpleEmployeeResult(SimpleEmployeeResult simpleEmployeeResult){
        this.userId = simpleEmployeeResult.getUserId();
        this.username = simpleEmployeeResult.getUsername();
    }
}
