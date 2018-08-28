package com.sema.ast;

import com.sema.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FolderAstProcessor {
    private final Parser parser;

    public FolderAstProcessor(Parser parser) {
        this.parser = parser;
    }

    public void processFolder(File processFolder, Predicate<String> fileMatchCriteria) {
        if (processFolder == null) {
            throw new IllegalArgumentException("processFolder cannot be null.");
        }
        if (fileMatchCriteria == null) {
            throw new IllegalArgumentException("fileMatchCriteria cannot be null.");
        }
        if (!processFolder.exists()) {
            throw new IllegalArgumentException(String.format("Folder: %s does not exist.", processFolder));
        }

        if (!processFolder.isDirectory()) {
            throw new IllegalArgumentException(String.format("Folder: %s is not a directory.", processFolder));
        }

        if (!(processFolder.canRead() && processFolder.canWrite())) {
            throw new IllegalArgumentException((String.format("Folder: %s must be readable and writable", processFolder)));
        }

        Path processFolderPath = Paths.get(processFolder.getAbsolutePath());

        try {
            Stream<Path> sourceFiles = Files.find(processFolderPath, 255, (path, basicFileAttributes) -> fileMatchCriteria.test(path.toString()));
            processSourceFiles(sourceFiles);
        } catch (IOException ioException) {
            throw new AstProcessorException("Unable to find source files.", ioException);
        }
    }

    private void processSourceFiles(Stream<Path> sourceFiles) {
        sourceFiles.forEach((sourceFile) -> {
            String parsedAst = parser.parseToAstString(sourceFile.toFile());
            Path astPath = getAstPath(sourceFile);
            try {
                OpenOption astWriteOption = astPath.toFile().exists() ? StandardOpenOption.TRUNCATE_EXISTING : StandardOpenOption.CREATE_NEW;
                Files.write(astPath, Collections.singleton(parsedAst), astWriteOption);
            } catch (IOException ioException) {
                throw new AstProcessorException(String.format("Unable to save Ast file %s", astPath), ioException);
            }
        });
    }

    private Path getAstPath(Path sourcePath) {
        String filename = sourcePath.getFileName().toString();
        return Paths.get(sourcePath.getParent().toString(), filename + ".ast");
    }


}
