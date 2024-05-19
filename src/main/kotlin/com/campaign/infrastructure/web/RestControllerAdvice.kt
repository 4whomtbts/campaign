package com.campaign.infrastructure.web

import com.campaign.controller.ErrorResponse
import com.campaign.controller.HttpResponseStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class RestControllerAdvice {
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun httpRequestMethodNotSupportedException(cause: HttpRequestMethodNotSupportedException) =
        ResponseEntity.status(HttpStatus.OK)
            .body(errorResponseFor(HttpResponseStatus.METHOD_NOT_ALLOWED))

    @ExceptionHandler(
        MissingServletRequestPartException::class, // multipart/form-data 에서 필수 파라미터가 없을 경우
        MethodArgumentTypeMismatchException::class, // 파라미터 타입이 맞지 않을 경우
        NumberFormatException::class, // 파라미터 타입이 맞지 않을 경우
        HttpMessageNotReadableException::class, // json 파싱 에러
        MissingServletRequestParameterException::class, // 필수 파라미터가 없을 경우
    )
    fun badRequestException(exception: Exception) =
        ResponseEntity.status(HttpStatus.OK)
            .body(errorResponseFor(HttpResponseStatus.INVALID_REQUEST))

    @ExceptionHandler(
        NoHandlerFoundException::class,
        NoResourceFoundException::class,
    )
    fun noHandlerException(exception: Exception) =
        ResponseEntity.status(HttpStatus.OK)
            .body(errorResponseFor(HttpResponseStatus.NOT_FOUND))

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception) =
        ResponseEntity.status(HttpStatus.OK)
            .body(errorResponseFor(HttpResponseStatus.INTERNAL_SERVER_ERROR))

    private fun errorResponseFor(status: HttpResponseStatus) =
        status.let {
            ErrorResponse(
                status = it.statusCode,
                message = it.message,
            )
        }
}
