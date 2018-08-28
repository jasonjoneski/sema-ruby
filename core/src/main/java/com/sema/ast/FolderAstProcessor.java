package com.sema.ast;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public interface FolderAstProcessor {
    void processFolder(File processFolder, Predicate<String> fileMatchCriteria);


}
