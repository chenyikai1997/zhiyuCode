package com.fh.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

/**
 * 同步聚合数据题库及答案
 * @author laogui
 *
 */
public class DriverexamAPI {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
//	public static final String APPKEY = "fa6fa95242dcf5cb5c487f9160ad3cdb";
	public static final String APPKEY = "84327a88f269e7c29e113e7686155e02";

	// 1.题库接口
	public static String getRequest1(Integer subject, String model, String testType) {
		String result = null;
		String url = "http://v.juhe.cn/jztk/query";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 您申请的appKey
		params.put("subject", subject);// 选择考试科目类型，1：科目1；4：科目4
		params.put("model", model);// 驾照类型，可选择参数为：c1,c2,a1,a2,b1,b2；当subject=4时可省略
		params.put("testType", testType);// 测试类型，rand：随机测试（随机100个题目），order：顺序测试（所选科目全部题目）
		String str = "";
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				str = object.get("result").toString();
				System.out.println(str);
			} else {
				str = "error_code:" + object.get("error_code").toString() + ";reason:" + object.get("reason").toString();
				System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// 2.answer字段对应答案
	public static String getRequest2() {
		String result = null;
		String url = "http://v.juhe.cn/jztk/answers";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 您申请的appk
		String str = "";
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				str = object.get("result").toString();
				System.out.println(str);
			} else {
				str = "error_code:" + object.get("error_code").toString() + ";reason:" + object.get("reason").toString();
				System.out.println(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void main(String[] args) {
		 getRequest2();
		// getRequest1();
	}

	/**
	 *
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map<String, Object> params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// 将map型转为请求参数型
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
