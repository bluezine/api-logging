package kr.co.bluezine.api.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("::::: LoggingFilter:init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        ContentCachingRequestWrapper requestToCache = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseToCache = new ContentCachingResponseWrapper(response);

        chain.doFilter(requestToCache, responseToCache);

        String requestBody = new String(requestToCache.getContentAsByteArray(), requestToCache.getCharacterEncoding());
        String responseBody = new String(responseToCache.getContentAsByteArray(),
                responseToCache.getCharacterEncoding());

        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headersE = requestToCache.getHeaderNames();
        while (headersE.hasMoreElements()) {
            String header = headersE.nextElement();
            headers.put(header, requestToCache.getHeader(header));
        }

        Map<String, Object> logObj = new HashMap<>();
        logObj.put("requestHeader", headers);
        logObj.put("request", requestBody);
        logObj.put("response", responseBody);

        log.info("{}", logObj);

        responseToCache.copyBodyToResponse();
    }

    @Override
    public void destroy() {
        log.info("::::: LoggingFilter:destroy");
    }
}
