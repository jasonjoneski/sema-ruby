package com.sema.parser.ruby;

import org.jruby.Ruby;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class JRubyParserTest {

    @Test
    public void parseToAstStringGeneratesAstStringWithValidFile() throws URISyntaxException {
        Ruby testRuby = Ruby.newInstance();
        JRubyParser parser = new JRubyParser(testRuby);

        File validFile = TestResourceUtils.getResourceAsFile("valid.rb");

        String parsedTree = parser.parseToAstString(validFile);

        System.out.println(parsedTree);

        assertThat(parsedTree, is(notNullValue()));
    }
}