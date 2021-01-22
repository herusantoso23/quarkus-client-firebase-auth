package com.herusantoso.handlers;

import com.herusantoso.dtos.ResultErrorDTO;
import com.herusantoso.utils.Constants;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException exception) {
        ResultErrorDTO dto = new ResultErrorDTO();
        dto.setErrorCode(Constants.ErrorCode.BAD_REQUEST);
        dto.setMessage(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(dto).build();
    }
}