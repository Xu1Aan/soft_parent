package com.ihrm.domain.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.domain.system
 * @Author: XuAn
 * @CreateTime: 2020-03-08 19:39
 * @Version: 1.0
 */
@Data
public class SelectTest implements Serializable {

    private static final long serialVersionUID = 855599446128936870L;

    private String testId;

    private String addressId;

    private String userId;

}
