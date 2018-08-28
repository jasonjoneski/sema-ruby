package com.sema.parser.ruby;

import com.sema.parser.AstParseException;
import com.sema.parser.AstParser;
import org.jruby.Ruby;
import org.jruby.ast.Node;
import org.jruby.exceptions.SyntaxError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JRubyAstParser implements AstParser {
    private final Ruby ruby;

    public JRubyAstParser(Ruby ruby) {
        this.ruby = ruby;
    }

    @Override
    public String parseToAstString(File fileToParse) {
        try {
            Node node = ruby.parseFromMain(new FileInputStream(fileToParse), fileToParse.getName());
            return node.toString(true, 2);
        } catch (FileNotFoundException fileNotFoundException) {
            String couldNotFileFile = String.format("Could not find file: %s", fileToParse.getPath());
            throw new AstParseException(couldNotFileFile, fileNotFoundException);
        } catch (SyntaxError syntaxError) {
            throw new AstParseException(String.format("File: %s had a syntax error, %s", fileToParse.getPath(), syntaxError.getMessage()), syntaxError);
        }
    }
}
