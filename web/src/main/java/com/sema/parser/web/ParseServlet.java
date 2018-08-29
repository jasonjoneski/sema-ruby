package com.sema.parser.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sema.ast.FolderAstProcessor;
import com.sema.ast.StandardSourcePredicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ParseServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ParseServlet.class);

    private FolderAstProcessor folderAstProcessor;
    ObjectMapper objectMapper;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        String folder = req.getParameter("parseFolder");

        log.info("Parsing Ruby files and creating AST for folder {}", folder);

        Path folderPath = Paths.get(Application.getCodeFolder(), folder);
        if (!(folderPath.toFile().exists() && folderPath.toFile().isDirectory())) {
            resp.setStatus(404);
            ParseResponse errorParseResponse = new ParseResponse(ParseResponse.ERROR);
            String errorMessage = String.format("Folder: %s does not exist or is not a directory", folder);
            log.info(errorMessage);
            errorParseResponse.setMessage(errorMessage);
            resp.getOutputStream().print(objectMapper.writeValueAsString(errorParseResponse));
            return;
        }

        folderAstProcessor.processFolder(folderPath.toFile(), StandardSourcePredicates.RUBY);

        ParseResponse parseResponse = new ParseResponse(ParseResponse.SUCCESS);
        parseResponse.setLink(ServletUtil.getBaseUrl(req) + "/astList?astFolder=" + resp.encodeURL(folder));
        resp.getOutputStream().print(objectMapper.writeValueAsString(parseResponse));
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        folderAstProcessor = Application.getFolderAstProcessor();
        objectMapper = Application.getObjectMapper();
    }
}
