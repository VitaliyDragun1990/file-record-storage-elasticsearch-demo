package com.vdrahun.filestorage.api.v1.resource;

import com.vdrahun.filestorage.api.v1.resource.dto.ApiResponse;
import com.vdrahun.filestorage.core.datetime.DateTimeService;
import com.vdrahun.filestorage.core.service.search.BadRequestException;
import com.vdrahun.filestorage.core.service.search.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static com.vdrahun.filestorage.util.WebUtil.getCurrentRequestPath;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandlerResource extends ResponseEntityExceptionHandler {

    private final DateTimeService dateTimeService;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("Handling ConstraintViolationException:{}", e.getMessage());

        ApiResponse apiResponse = ApiResponse.failure(
                buildMessageFromConstraintViolation(e.getConstraintViolations()),
                getCurrentRequestPath(),
                dateTimeService.currentDateTime());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<Object> handleConversionIsNotSupportedException(ConversionFailedException e) {
        log.warn("Handling ConversionFailedException:{}", e.getMessage());

        ApiResponse apiResponse = ApiResponse.failure(
                e.getMessage(),
                getCurrentRequestPath(),
                dateTimeService.currentDateTime());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleConversionIsNotSupportedException(MethodArgumentTypeMismatchException e) {
        log.warn("Handling MethodArgumentTypeMismatchException:{}", e.getMessage());

        ApiResponse apiResponse = ApiResponse.failure(
                format("Invalid value for param:[%s]", e.getName()),
                getCurrentRequestPath(),
                dateTimeService.currentDateTime());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("Handling ResourceNotFoundException:{}", e.getMessage());

        ApiResponse apiResponse = ApiResponse.failure(
                e.getMessage(),
                getCurrentRequestPath(),
                dateTimeService.currentDateTime());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        log.warn("Handling BadRequestException:{}", e.getMessage());

        ApiResponse apiResponse = ApiResponse.failure(
                e.getMessage(),
                getCurrentRequestPath(),
                dateTimeService.currentDateTime());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("Handling MethodArgumentNotValidException:{}", e.getMessage());

        ApiResponse apiResponse = ApiResponse.failure(
                buildMessageFromBindingResult(e.getBindingResult()),
                getCurrentRequestPath(),
                dateTimeService.currentDateTime());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("Handling MissingServletRequestParameterException:{}", e.getMessage());

        ApiResponse apiResponse = ApiResponse.failure(
                format("Required request parameter [%s] is missing", e.getParameterName()),
                getCurrentRequestPath(),
                dateTimeService.currentDateTime());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.warn("Handling Exception:{}", e.getMessage(), e);

        ApiResponse apiResponse = ApiResponse.failure(
                "Internal server error",
                getCurrentRequestPath(),
                dateTimeService.currentDateTime());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    private String buildMessageFromBindingResult(BindingResult bindingResult) {
        String fieldErrors = bindingResult.getFieldErrors().stream()
                .map(e -> format("Invalid value for property [%s] - %s", e.getField(), e.getDefaultMessage())).collect(joining(", "));
        String globalErrors = bindingResult.getGlobalErrors().stream()
                .map(e -> format("Invalid request - %s", e.getDefaultMessage())).collect(joining(", "));

        return List.of(fieldErrors, globalErrors).stream().filter(StringUtils::hasText).collect(joining(", "));
    }

    private String buildMessageFromConstraintViolation(Set<ConstraintViolation<?>> violations) {
        return violations
                .stream()
                .map(c -> format("Invalid value [%s] - %s", ((PathImpl) c.getPropertyPath()).getLeafNode().asString(), c.getMessage()))
                .collect(joining(", "));
    }
}
