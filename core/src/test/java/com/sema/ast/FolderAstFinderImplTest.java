package com.sema.ast;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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

    @Test
    public void getAstFileSucceedsWithValidInput() throws URISyntaxException {
        File testFolder = TestResourceUtils.getResourceAsFile("valid");
        List<String> astFiles = testFinder.getAstFiles(testFolder);

        assertThat(astFiles.size(), is(1));

        String ast = testFinder.getAstFile(new File(astFiles.get(0)));

        assertThat(ast, notNullValue());
    }
}