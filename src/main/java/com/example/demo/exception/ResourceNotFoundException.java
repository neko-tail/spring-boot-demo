package com.example.demo.exception;

import com.example.demo.common.ResultCode;

/**
 * 资源未找到异常
 */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(ResultCode.DATA_NOT_FOUND, message);
    }

    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(ResultCode.DATA_NOT_FOUND, String.format("%s with id '%s' not found", resourceName, resourceId));
    }
}
