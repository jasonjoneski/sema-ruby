package com.sema.parser.ruby;

import com.sema.parser.AstParseException;
import com.sema.parser.AstParser;
import org.jruby.Ruby;
import org.jruby.ast.Node;
import org.jruby.exceptions.SyntaxError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JRubyAstParser implements AstParser {
    private static final Logger log = LoggerFactory.getLogger(JRubyAstParser.class);

    private final Ruby ruby;

    public JRubyAstParser(Ruby ruby) {
        this.ruby = ruby;
    }

    @Override
    public String parseToAstString(File fileToParse) {
        FileInputStream parseInputStream = null;
        try {
            parseInputStream = new FileInputStream(fileToParse);
            Node node = ruby.parseFromMain(parseInputStream, fileToParse.getName());
            return node.toString(true, 2);
        } catch (FileNotFoundException fileNotFoundException) {
            String couldNotFileFile = String.format("Could not find file: %s", fileToParse.getPath());
            log.warn(couldNotFileFile, fileNotFoundException);
            throw new AstParseException(couldNotFileFile, fileNotFoundException);
        } catch (SyntaxError syntaxError) {
            log.warn("Syntax Error found in file {}", fileToParse, syntaxError);
            throw new AstParseException(String.format("File: %s had a syntax error, %s", fileToParse.getPath(), syntaxError.getMessage()), syntaxError);
        } finally {
            if (parseInputStream != null) {
                try {
                    parseInputStream.close();
                } catch (IOException ioException) {
                    log.warn("Unable to close parseInputStream", ioException);
                }
            }
        }
    }
}
