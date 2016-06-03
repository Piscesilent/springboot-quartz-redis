package timer.utils;

import com.google.gson.Gson;

/**
 * Gson工具类
 * 
 * @author SilenT
 *
 */
public class GsonUtils {
	
	private static Gson gson = new Gson();
	
	private static GsonUtils instance = new GsonUtils();
	
	private GsonUtils() {}

	public static GsonUtils getInstance() {
		return instance;
	}
	
	/**
	 * Object 转为 Json
	 * @author SilenT
	 * @param obj
	 * @return
	 */
	public String toJson(Object obj) {
		if (null == obj) {
			return "";
		}
		return gson.toJson(obj);
	}
}
