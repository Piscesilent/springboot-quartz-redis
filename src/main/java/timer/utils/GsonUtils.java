package timer.utils;

import com.google.gson.Gson;

/**
 * Gson Utils
 * @author SilenT
 *
 */
public class GsonUtils {
	
	private static Gson gson = new Gson();
	
	/**
	 * Object 转为 Json
	 * @author SilenT
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if (null == obj) {
			return "";
		}
		return gson.toJson(obj);
	}
}
