package com.herusantoso.handlers;

import com.herusantoso.dtos.ResultErrorDTO;
import com.herusantoso.utils.Constants;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionHandler implements ExceptionMapper<ForbiddenException> {
    @Override
    public Response toResponse(ForbiddenException exception) {
        ResultErrorDTO dto = new ResultErrorDTO();
        dto.setErrorCode(Constants.ErrorCode.FORBIDDEN);
        dto.setMessage(exception.getMessage());
        return Response.status(Response.Status.FORBIDDEN).entity(dto).build();
    }
}