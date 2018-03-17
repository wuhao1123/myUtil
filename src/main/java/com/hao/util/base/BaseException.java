package com.hao.util.base;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @ClassName BaseException.java
 * @Description 功能描述：自定义异常
 * @author 吴昊 2018年3月6日19:30:30
 */
public class BaseException extends RuntimeException {

    /**
     * 
     */
    private static final long  serialVersionUID   = 1L;
    /**
     * 未知错误code
     */
    public static final String UNKNOWN_ERROR_CODE = "unknow error";
    /**
     * 未知错误
     */
    public static final String UNKNOWN_ERROR_MSG  = "未知错误";

    private Throwable          cause;                              // 异常
    private String             errorCode;                          // 错误code

    public BaseException(String errorMsg){
        this("", errorMsg);
    }

    public BaseException(Throwable cause){
        this(cause, "");
    }

    public BaseException(String errorCode, String errorMsg){
        this(null, errorCode, errorMsg);
    }

    public BaseException(Throwable cause, String errorMsg){
        this(cause, BaseException.UNKNOWN_ERROR_CODE, errorMsg);
    }

    public BaseException(Throwable cause, String errorCode, String errorMsg){
        super(errorMsg);
        this.cause = cause;
        this.errorCode = errorCode;
    }

    @Override
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }

    @Override
    public void printStackTrace(PrintStream ps) {
        if (null == getCause()) {
            super.printStackTrace(ps);
        } else {
            ps.println(this);
            getCause().printStackTrace(ps);
        }
    }

    @Override
    public void printStackTrace(PrintWriter pw) {
        if (null == getCause()) {
            super.printStackTrace(pw);
        } else {
            pw.println(this);
            getCause().printStackTrace(pw);
        }
    }

    @Override
    public Throwable getCause() {
        return this.cause == this ? null : this.cause;
    }

    @Override
    public String getMessage() {
        if (getCause() == null) {
            return super.getMessage();
        }
        return super.getMessage() + getCause().getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

}
