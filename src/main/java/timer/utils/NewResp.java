package timer.utils;

import java.io.Serializable;

/**
 * 响应类
 * @author SilenT
 *
 */
public class NewResp implements Serializable {
	
	private static final long serialVersionUID = -1821975830305378599L;
	
	private String msg = "";//返回给客户端的消息
	
	private Object result = "";//数据
	
	private int code = 0;//错误码
	
	private NewResp() {}
	
	private NewResp(Object result, int code, String msg) {
		this.result = result;
		this.code = code;
		this.msg = msg;
	}
	
	public static NewResp OK(Object result) {
		return new NewResp(result, 0, "OK");
	}
	
	public static NewResp OK() {
		return new NewResp("", 0, "OK");
	}
	
	public static NewResp fail(String msg) {
		return new NewResp("", 500, msg);
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
