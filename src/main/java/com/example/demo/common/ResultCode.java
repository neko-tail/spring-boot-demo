package com.example.demo.common;

import lombok.Getter;

/**
 * 统一响应状态码枚举
 */
@Getter
public enum ResultCode {

    // 成功
    SUCCESS(200, "Success"),

    // 客户端错误 4xx
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CONFLICT(409, "Conflict"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),

    // 服务端错误 5xx
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    // 业务错误码 1xxx
    PARAM_ERROR(1001, "Parameter Error"),
    VALIDATION_ERROR(1002, "Validation Error"),
    DATA_NOT_FOUND(1003, "Data Not Found"),
    DATA_ALREADY_EXISTS(1004, "Data Already Exists"),
    OPERATION_FAILED(1005, "Operation Failed"),

    // 认证授权错误 2xxx
    AUTH_ERROR(2001, "Authentication Error"),
    TOKEN_EXPIRED(2002, "Token Expired"),
    TOKEN_INVALID(2003, "Token Invalid"),
    ACCESS_DENIED(2004, "Access Denied"),

    // 数据库错误 3xxx
    DB_ERROR(3001, "Database Error"),
    DB_CONNECTION_ERROR(3002, "Database Connection Error"),
    DUPLICATE_KEY(3003, "Duplicate Key Error");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
