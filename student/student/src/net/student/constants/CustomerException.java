package net.student.constants;
/**
 * 自定义异常类
 * @author liuqingchao
 *
 */
public class CustomerException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private Object[] args;

    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(Throwable cause) {
        super(cause);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerException(String message, String arg) {
        super(message);
        this.args = new Object[1];
        this.args[0] = arg;
    }
    
    public CustomerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}
