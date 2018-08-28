package com.sema.ast;

import java.io.File;
import java.util.List;

public interface FolderAstFinder {
    List<String> getAstFiles(File astFolder);

    String getAstFile(File astFile);
}
