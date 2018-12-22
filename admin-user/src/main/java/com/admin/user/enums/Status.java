package com.admin.user.enums;

import lombok.*;

/**
 * Created by fei on 2017/9/18.
 */
@AllArgsConstructor
public enum Status {

    OFF(0, "禁用"),
    ON(1, "启用");


    @Getter
    private int value;
    @Getter
    private String massage;
}
