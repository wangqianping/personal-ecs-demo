package common;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * TS 表默认字段
 * @author wangqianping
 * @date 2022-05-13
 */
@Data
public class TsBaseEntity {

    /**
     * 时间节点
     */
    private LocalDateTime time;

    /**
     * 字段名
     */
    private String measureName;

    /**
     * 字段值
     */
    private String measureValue;
}
