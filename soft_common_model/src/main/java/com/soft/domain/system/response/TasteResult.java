package com.soft.domain.system.response;

import lombok.Data;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.domain.system.response
 * @Author: XuAn
 * @CreateTime: 2021-04-08 16:33
 * @Version: 1.0
 */
@Data
public class TasteResult {
    private Integer finishNumber;
    private Integer unfinishNumber;

    public TasteResult(int finishNumber, int unfinishNumber) {
        this.finishNumber = finishNumber;
        this.unfinishNumber = unfinishNumber;
    }
}
