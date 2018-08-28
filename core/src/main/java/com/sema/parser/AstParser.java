package com.sema.parser;

import java.io.File;

public interface AstParser {
    String parseToAstString(File fileToParse);
}
