package com.sema.parser.ruby;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class TestResourceUtils {

    private static ClassLoader getClassLoader() {
        return TestResourceUtils.class.getClassLoader();
    }

    public static File getResourceAsFile(String resourcePath) throws URISyntaxException {
        URL fileUrl = getClassLoader().getResource(resourcePath);

        return new File(fileUrl.toURI());
    }
}
