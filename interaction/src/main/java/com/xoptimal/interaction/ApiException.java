package com.xoptimal.interaction;

/**
 * Created by Freddie on 2018/2/24 0024 .
 * Description:
 */
public class ApiException extends RuntimeException {

    private ResponseStatus status;

    public ApiException(ResponseStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }


    public enum ResponseStatus {

        ERROR, SUCCESS, JURISDICTION
    }
}
