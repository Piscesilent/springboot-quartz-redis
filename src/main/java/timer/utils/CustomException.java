package timer.utils;

/**
 * 自定义异常
 * @author SilenT
 *
 */
public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 2580503536594824649L;
	
	public CustomException() {
        super();
    }
	
	public CustomException(String message) {
        super(message);
    }
	
	public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
