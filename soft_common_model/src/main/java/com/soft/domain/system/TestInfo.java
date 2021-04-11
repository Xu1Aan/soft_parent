package com.soft.domain.system;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.domain.system
 * @Author: XuAn
 * @CreateTime: 2021-04-04 14:52
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestInfo implements Serializable {

    private static final long serialVersionUID = 3021350860196484523L;

    private String address;

    private String username;

    private String mobile;

    private String userId;

    private String testId;

}
