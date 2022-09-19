package com.sz.gateway.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 白名单配置
 * @author wangqianping
 * @date 2022-09-19
 */
@Component
@ConfigurationProperties(prefix = "customize.white-list")
public class WhiteList {

    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
