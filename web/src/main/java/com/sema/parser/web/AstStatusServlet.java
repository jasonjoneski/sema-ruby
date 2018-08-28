package com.sema.parser.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AstStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println("{\"name\": \"Hello World!\"}");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
}
