package demo.spring.boot.demospringboot.framework;

import java.util.ArrayList;
import java.util.List;

//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Data
//@Setter
//@Getter
//@ToString
public class Response<T> {
    private String code;
    private String msg;
    private String error;
    private List<Exception> exceptions
            = new ArrayList<>();
    private T content;

    public Response() {
    }

    public Response(String code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void addException(Exception exception) {
        this.exceptions.add(exception);
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
