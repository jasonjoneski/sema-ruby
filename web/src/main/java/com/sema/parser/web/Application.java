package com.sema.parser.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sema.ast.FolderAstFinder;
import com.sema.ast.FolderAstFinderImpl;
import com.sema.ast.FolderAstProcessor;
import com.sema.ast.FolderAstProcessorImpl;
import com.sema.parser.AstParser;
import com.sema.parser.ruby.JRubyAstParser;
import org.jruby.Ruby;

import java.io.File;
import java.util.Optional;

/**
 * Code for wiring up dependencies, and useful for semi dependency injection for Servlets.  I'd prefer to use a DI
 * framework in order to make this a cleaner setup, but am going this route for the sake of simplicity / time.
 */
public class Application {

    public static String getCodeFolder() {
        Optional<String> codeFolder = Optional.ofNullable(System.getProperty("sema.code.folder"));
        return codeFolder.orElse(System.getProperty("user.dir") + File.pathSeparator + "code");
    }

    public static AstParser getAstParser() {
        return new JRubyAstParser(Ruby.newInstance());
    }

    public static FolderAstFinder getFolderAstFinder() {
        return new FolderAstFinderImpl();
    }

    public static FolderAstProcessor getFolderAstProcessor() {
        return new FolderAstProcessorImpl(getAstParser());
    }

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper =  new ObjectMapper();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }
}
