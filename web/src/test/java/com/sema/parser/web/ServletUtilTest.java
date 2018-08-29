package com.sema.parser.web;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class ServletUtilTest {

    @Test
    public void getBaseUrl() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("localhost");
        when(request.getServerPort()).thenReturn(8081);
        when(request.getContextPath()).thenReturn("/somewhere");

        String baseUrl = ServletUtil.getBaseUrl(request);

        assertThat(baseUrl, is("http://localhost:8081/somewhere"));
    }
}