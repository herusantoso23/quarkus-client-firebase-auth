package com.herusantoso.handlers;

import com.herusantoso.dtos.ResultErrorDTO;
import com.herusantoso.dtos.ResultObjectValidationDTO;
import com.herusantoso.utils.Constants;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ResultObjectValidationDTO> errors = exception.getConstraintViolations().stream()
                .map(constraintViolation -> {
                    ResultObjectValidationDTO dto = new ResultObjectValidationDTO();
                    dto.setMessage(constraintViolation.getMessage());
                    dto.setObject(extractObjectNameFromPath(constraintViolation.getPropertyPath()));
                    dto.setValue(constraintViolation.getInvalidValue());
                    return dto;
                }).collect(Collectors.toList());

        ResultErrorDTO dto = new ResultErrorDTO();
        dto.setErrorCode(Constants.ErrorCode.VALIDATION_ERROR);
        dto.setMessage(errors.get(0).getMessage());
        dto.setResult(errors);
        return Response.status(Response.Status.BAD_REQUEST).entity(dto).build();
    }

    private String extractObjectNameFromPath(Path path){
        String object = path.toString();
        String[] objects = object.split("\\.");
        int length = objects.length;
        log.info("objects {} length : {}", objects, length);
        return objects[length-1];
    }
}