package com.sema.ast;

import com.sema.parser.AstParser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class FolderAstProcessorTest {

    private AstParser mockAstParser;
    private FolderAstProcessor testProcessor;
    private Predicate<String> testPredicate = (file) -> true;

    @Before
    public void setup() {
        mockAstParser = mock(AstParser.class);
        testProcessor = new FolderAstProcessorImpl(mockAstParser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void processFolderFailsWithNonExistentFolder() {
        testProcessor.processFolder(new File("doesntExist"), testPredicate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void processFolderFailsOnNullProcessFolder() {
        testProcessor.processFolder(null, testPredicate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void processFolderFailsOnNullTestFunction() {
        testProcessor.processFolder(new File("anything"), null);
    }

    @Test
    public void processFolderSucceedsOnValidFolder() throws URISyntaxException {
        File testFolder = TestResourceUtils.getResourceAsFile("valid");
        testProcessor.processFolder(testFolder, StandardSourcePredicates.RUBY);
        File testAstFile = Paths.get(testFolder.getAbsolutePath(), "valid.rb.ast").toFile();
        assertThat(testAstFile.exists(), is(true));
    }
}