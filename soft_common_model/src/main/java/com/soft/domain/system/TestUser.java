package com.soft.domain.system;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.domain.system
 * @Author: XuAn
 * @CreateTime: 2020-03-06 14:08
 * @Version: 1.0
 */
@Entity
@Table(name = "bs_user_test")
@Getter
@Setter
@NoArgsConstructor
public class TestUser {

    @Id
    private String id;

    private String userId;

    private String testId;

    private int isTeacher;

    private String addressId;

    //交换教师Id
    private String exchangeTeacherId;
}
