package vn.rananu.shared;


import java.util.Map;

public class Result<T> {
    private T data;
    private Integer status;
    private String message;
    private Map<Object, Object> errors;

    public Result(T data, Integer status, String message, Map<Object, Object> errors) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public Result(T data, Integer status) {
        this(data, null, null, null);
    }


    public Result(Map<Object, Object> errors, Integer status) {
        this(null, status, null, errors);
    }

    public Result(T data, String message, Integer status) {
        this(data, status, message, null);
    }

    public Result(String message, Map<Object, Object> errors, Integer status) {
        this(null, status, message, errors);
    }

    public Result(String message, Integer status) {
        this(null, status, message, null);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Object, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<Object, Object> errors) {
        this.errors = errors;
    }
}
