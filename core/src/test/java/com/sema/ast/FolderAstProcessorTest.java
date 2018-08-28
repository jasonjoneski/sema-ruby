package com.sema.ast;

import com.sema.parser.Parser;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.function.Predicate;

import static org.mockito.Mockito.mock;

public class FolderAstProcessorTest {

    private Parser mockParser;
    private FolderAstProcessor testProcessor;
    private Predicate<String> testPredicate = (file) -> true;

    @Before
    public void setup() {
        mockParser = mock(Parser.class);
        testProcessor = new FolderAstProcessor(mockParser);
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
        testProcessor.processFolder(testFolder, (file) -> file.endsWith(".rb"));
    }


}