package com.sema.ast;

import com.sema.parser.AstParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderAstFinderImpl implements FolderAstFinder {
    private static final Logger log = LoggerFactory.getLogger(FolderAstFinderImpl.class);

    @Override
    public List<String> getAstFiles(File astFolder) {
        log.debug("Getting Ast Files for {}", astFolder);
        if (astFolder == null) {
            throw new IllegalArgumentException("processFolder cannot be null.");
        }

        if (!astFolder.exists()) {
            throw new IllegalArgumentException(String.format("Folder: %s does not exist.", astFolder));
        }

        if (!astFolder.isDirectory()) {
            throw new IllegalArgumentException(String.format("Folder: %s is not a directory.", astFolder));
        }
        try {
            Stream<Path> astFiles = Files.find(Paths.get(astFolder.getAbsolutePath()), 255, (path, basicFileAttributes) -> path.toString().endsWith(".ast"));
            return astFiles.map(f -> f.toString()).collect(Collectors.toList());
        } catch (IOException ioException) {
            log.warn("IO Exception finding Ast Files in folder {}", astFolder, ioException);
            throw new AstParseException("Unable to find Ast Files.", ioException);
        }

    }

    @Override
    public String getAstFile(File astFile) {
        log.debug("Getting AST File {}", astFile);
        if (astFile == null) {
            throw new IllegalArgumentException("file cannot be null.");
        }

        if (!astFile.exists()) {
            throw new IllegalArgumentException(String.format("Folder: %s does not exist.", astFile));
        }

        try {
            return new String(Files.readAllBytes(Paths.get(astFile.getAbsolutePath())), StandardCharsets.UTF_8);
        } catch (IOException ioException) {
            log.warn("IO Exception finding AST file {}", astFile, ioException);
            throw new AstFindException(String.format("Unable to find Ast file: %s", astFile.getName()), ioException);
        }
    }

}
