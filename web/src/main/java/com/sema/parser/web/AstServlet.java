package com.sema.parser.web;

import com.sema.ast.AstFindException;
import com.sema.ast.FolderAstFinder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class AstServlet extends HttpServlet {
    private FolderAstFinder astFinder;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = req.getParameter("file");
        File astFile = Paths.get(Application.getCodeFolder(), file).toFile();

        if (!(astFile.exists() && astFile.isFile())) {
            resp.setStatus(404);
            return;
        }

        resp.setHeader("Content-Type", "text/plain");
        try {
            String ast = astFinder.getAstFile(astFile);
            resp.getOutputStream().print(ast);
        } catch (AstFindException astFindException) {
            resp.setStatus(404);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        astFinder = Application.getFolderAstFinder();
    }
}
