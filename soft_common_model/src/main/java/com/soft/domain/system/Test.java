package com.soft.domain.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.domain.system
 * @Author: XuAn
 * @CreateTime: 2020-03-06 12:01
 * @Version: 1.0
 */

@Entity
@Table(name = "bs_test")
@Data
@NoArgsConstructor
public class Test implements Serializable {

    private static final long serialVersionUID = -2421504546763128723L;

    @Id
    private String id;

    //监考日期
    private String date;

    //监考时间
    private String time;

    //通知日期
    private Date noticeTime;

    //创建时间
    private Date createTime;

    //考试类型
    private String type;

    //考试科目
    private String subject;

    //剩余监考人数
    private String remain;

    //监考人数
    private Integer number;

    //单位id
    private String companyId;

    //考试状态
    private Integer status;



}
