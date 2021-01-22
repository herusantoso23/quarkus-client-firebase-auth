package com.herusantoso.handlers;

import com.herusantoso.dtos.ResultErrorDTO;
import com.herusantoso.utils.Constants;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        exception.printStackTrace();

        ResultErrorDTO dto = new ResultErrorDTO();
        dto.setErrorCode(Constants.ErrorCode.INTERNAL_SERVER_ERROR);
        dto.setMessage("Something went wrong. Please Contact The Administrator");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(dto).build();
    }
}