package com.herusantoso.handlers;

import com.herusantoso.dtos.ResultErrorDTO;
import com.herusantoso.handlers.exception.TokenInvalidException;
import com.herusantoso.utils.Constants;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TokenInvalidExceptionHandler implements ExceptionMapper<TokenInvalidException> {
    @Override
    public Response toResponse(TokenInvalidException exception) {
        ResultErrorDTO dto = new ResultErrorDTO();
        dto.setErrorCode(Constants.ErrorCode.TOKEN_INVALID);
        dto.setMessage(exception.getMessage());
        return Response.status(Response.Status.UNAUTHORIZED).entity(dto).build();
    }
}