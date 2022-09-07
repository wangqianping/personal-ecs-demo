package com.sz.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author wangqianping
 * @date 2022-09-07
 */
@Data
public class BaseEntity {

    private Date createTime;
    private Date updateTime;
    private Integer delFlag;

}
