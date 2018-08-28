package com.sema.parser.ruby;

import com.sema.parser.AstParseException;
import org.jruby.Ruby;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class JRubyAstParserTest {

    @Test
    public void parseToAstStringGeneratesAstStringWithValidFile() throws URISyntaxException {
        Ruby testRuby = Ruby.newInstance();
        JRubyAstParser parser = new JRubyAstParser(testRuby);

        File validFile = TestResourceUtils.getResourceAsFile("valid.rb");

        String parsedTree = parser.parseToAstString(validFile);

        System.out.println(parsedTree);

        assertThat(parsedTree, is(notNullValue()));
    }


    @Test(expected = AstParseException.class)
    public void parseToAstStringHasExceptionOnInvalidRuby() throws URISyntaxException {
        Ruby testRuby = Ruby.newInstance();
        JRubyAstParser parser = new JRubyAstParser(testRuby);

        File invalidFile = TestResourceUtils.getResourceAsFile("invalid.rb");

        parser.parseToAstString(invalidFile);
    }
}