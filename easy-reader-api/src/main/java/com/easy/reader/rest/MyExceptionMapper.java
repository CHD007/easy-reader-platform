package com.easy.reader.rest;

import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Mapper for exception catching in rest api.
 * @author dchernyshov
 */
@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOGGER = Logger.getLogger(MyExceptionMapper.class);
    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("Error in rest api", exception);
        return Response.status(500).build();
    }
}
