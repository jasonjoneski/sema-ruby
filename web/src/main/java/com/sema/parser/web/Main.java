package com.sema.parser.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));

    public static void main(String[] args) throws Exception {
        int serverPort = Integer.parseInt(port.orElse("8080"));
        log.info("Starting web server for sema web parser on port {}", serverPort);
        Server server = new Server(serverPort);

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(ParseServlet.class, "/parse/*");
        servletHandler.addServletWithMapping(AstListServlet.class, "/astList/*");
        servletHandler.addServletWithMapping(AstServlet.class, "/ast/*");

        server.setHandler(servletHandler);
        server.start();
        server.join();
    }
}
