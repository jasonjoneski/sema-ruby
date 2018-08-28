package com.sema.parser.web;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {
    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = (request.getContextPath() == null) ? "" : request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }
}
