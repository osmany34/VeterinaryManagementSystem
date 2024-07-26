package dev.patika.VeterinaryManagementSystem.core.result;

import lombok.Getter;

@Getter
public class ResultData<T> extends Result {
    private final T data;

    public ResultData(boolean status, String message, String httpCode, T data) {
        super(status, message, httpCode);
        this.data = data;
    }

}
