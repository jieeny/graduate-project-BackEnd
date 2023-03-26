package com.lgh.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 李广辉
 * @date 2023/1/9 13:20
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class LoginInfo {

    private String username;
    private String password;
    private String code;
    private String radio;

}
