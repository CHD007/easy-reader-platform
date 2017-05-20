package com.easy.reader.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

/**
 * CORS filter created for debugging only. Don't use in production
 * <p>
 * Adds CORS headers in order to fulfill request in CORS
 *
 * @author etitkov
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {
    private static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS, HEAD";
    private final static int MAX_AGE = 42 * 60 * 60;
    private final static String DEFAULT_ALLOWED_HEADERS = "origin,accept,content-type";

    @Context
    private UriInfo info;

    @Override
    public void filter(final ContainerRequestContext requestContext,
                       final ContainerResponseContext responseContext) throws IOException {
        final MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", getRequestedHeaders(requestContext));
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
        headers.add("Access-Control-Max-Age", MAX_AGE);
        headers.add("x-responded-by", "cors-response-filter");
    }

    /**
     * Get requested headers from context and adds specified
     *
     * @param responseContext - context
     *
     * @return - newly created allowed header list string
     */
    private String getRequestedHeaders(final ContainerRequestContext responseContext) {
        final List<String> headers = responseContext.getHeaders().get("Access-Control-Request-Headers");
        return createHeaderList(headers);
    }

    /**
     * Creates a string of allowed headers
     *
     * @param headers - list of headers
     *
     * @return - headers in one string
     */
    private String createHeaderList(final List<String> headers) {
        if (headers == null || headers.isEmpty()) {
            return DEFAULT_ALLOWED_HEADERS;
        }
        final StringBuilder retVal = new StringBuilder();
        for (final String header1 : headers) {
            retVal.append(header1);
            retVal.append(',');
        }
        retVal.append(DEFAULT_ALLOWED_HEADERS);
        return retVal.toString();
    }

}
