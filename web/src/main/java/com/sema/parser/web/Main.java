package com.sema.parser.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Optional;

public class Main {
    public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));

    public static void main(String[] args) throws Exception {
        int serverPort = Integer.parseInt(port.orElse("8080"));
        Server server = new Server(serverPort);

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(AstStatusServlet.class, "/parser/*");

        server.setHandler(servletHandler);
        server.start();
        server.join();
    }
}
