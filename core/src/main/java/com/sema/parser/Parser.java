package com.sema.parser;

import java.io.File;

public interface Parser {
    String parseToAstString(File fileToParse);
}
