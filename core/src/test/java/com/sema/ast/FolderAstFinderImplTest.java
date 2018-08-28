package com.sema.ast;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FolderAstFinderImplTest {
    private FolderAstFinderImpl testFinder;

    @Before
    public void setup() {
        testFinder = new FolderAstFinderImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAstFilesFailsWithNonExistentFolder() {
        testFinder.getAstFiles(new File("doesntExist"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAstFilesFailsOnNullProcessFolder() {
        testFinder.getAstFiles(null);
    }


}