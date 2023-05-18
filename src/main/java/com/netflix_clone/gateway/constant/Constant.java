package com.netflix_clone.gateway.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created on 2023-05-18
 * Project gateway
 */
@Component(value = "constant")
public class Constant {
    public static String TOKEN_NAME;

    @Value("${constant.token_name}")
    public static void setTokenName(String _TOKEN_NAME) { TOKEN_NAME = _TOKEN_NAME; }

    public static String PROJECT_NAME;

    @Value("${constant.project_name}")
    public static void setProjectName(String _PROJECT_NAME) { PROJECT_NAME = _PROJECT_NAME; }

    public static String SALT;
    @Value("${constant.salt}")
    public static void setSalt(String _SALT) { SALT = _SALT; }


}
