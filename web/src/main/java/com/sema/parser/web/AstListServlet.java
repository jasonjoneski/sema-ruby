package com.sema.parser.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sema.ast.FolderAstFinder;
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
import java.util.List;
import java.util.stream.Collectors;

public class AstListServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AstListServlet.class);
    private FolderAstFinder folderAstFinder;
    private ObjectMapper objectMapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String folder = req.getParameter("astFolder");
        log.info("Getting AST List for folder {}", folder);
        Path folderPath = Paths.get(Application.getCodeFolder(), folder);
        resp.setHeader("Content-Type", "application/json");
        if (!(folderPath.toFile().exists() && folderPath.toFile().isDirectory())) {
            resp.setStatus(404);
            String message = String.format("Ast Folder %s not found.", folder);
            log.info(message);
            resp.getOutputStream().print(objectMapper.writeValueAsString(new AstErrorResponse(message)));
            return;
        }

        List<String> astFiles = folderAstFinder.getAstFiles(folderPath.toFile());
        List<String> astLinks = createAstLinks(ServletUtil.getBaseUrl(req), astFiles);

        resp.getOutputStream().print(objectMapper.writeValueAsString(astLinks));
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        folderAstFinder = Application.getFolderAstFinder();
        objectMapper = Application.getObjectMapper();
    }

    private List<String> createAstLinks(String baseUrl, List<String> astFiles) {
        return astFiles.stream().map(ast -> ast.replace(Application.getCodeFolder(), ""))
                .map(ast -> baseUrl + "/ast?file=" + ast).collect(Collectors.toList());
    }
}
