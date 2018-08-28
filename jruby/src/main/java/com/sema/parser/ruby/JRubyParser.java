package com.sema.parser.ruby;

import com.sema.parser.ParseException;
import com.sema.parser.Parser;
import org.jruby.Ruby;
import org.jruby.ast.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JRubyParser implements Parser {
    private final Ruby ruby;

    public JRubyParser(Ruby ruby) {
        this.ruby = ruby;
    }

    @Override
    public String parseToAstString(File fileToParse) {
        try {
            Node node = ruby.parseFromMain(new FileInputStream(fileToParse), fileToParse.getName());
            return node.toString(true, 2);
        } catch (FileNotFoundException fileNotFoundException) {
            String couldNotFileFile = String.format("Could not find file: %s", fileToParse.getPath());
            throw new ParseException(couldNotFileFile, fileNotFoundException);
        }
    }
}
