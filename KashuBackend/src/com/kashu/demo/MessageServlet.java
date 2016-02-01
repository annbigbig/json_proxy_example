package com.kashu.demo;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * 送入的JSON電文格式
 * 	{
 * 		"userid" : "kenny",
 * 		"passwd" : "kennypass",
 * 		"messageid" : "1"
 * 	}
 * 
 * 打開你Chrome的Postman，把上面的JSON往這個地址送去
 * http://localhost:8080/KashuBackend/MessageServlet
 * 
 * 返回的JSON格式
 * 	{
  			"content": "我可以和你賭一百今天不會再加班了(應該啦)",
  			"title": "要不要賭一百今天不會加班？",
  			"messageid": "3"
		}
 * 
 */
public class MessageServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doMain(request,response);
	}
	
	//主函式
	public void doMain(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//從request裡取出JSON
		JSONObject json = parseJSONfromHttpRequest(request);
		
		//打印看看
		System.out.println("MessageServlet.doMain()");
		System.out.println("json=" + json.toString());
		
		//把JSON映射成領域模型
		Params params = mapping(json);
		
		//一樣打印看看
		System.out.println(params);
		
		//依照用戶提交來的messageid，產生對應的JSON物件
		JSONObject json_res = process(params);
		
		//response打印此JSON物件
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.getWriter().print(json_res.toString());
	}
	
	/*
	 * 傳入request
	 * 情況一:返回JSONObject物件
	 * 情況二:返回空的JSONObject物件
	 */
	public JSONObject parseJSONfromHttpRequest(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try{
			request.setCharacterEncoding("UTF-8");
			BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		           sb.append(line).append('\n');
		    }
		    reader.close();
		    System.out.println(sb.toString());
		    json = new JSONObject(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	//將傳入的JSON映射成領域模型Params物件
	public Params mapping(JSONObject json) {
		Params params = new Params();
		try{
			ObjectMapper mapper = new ObjectMapper();
			params = mapper.readValue(json.toString(),Params.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return params;
	}
	
	//判斷Params物件內的屬性，返回相對應的JSON物件
	public JSONObject process(Params params){
		JSONObject json = new JSONObject();
		if((params.getUserid()!=null) && (params.getPasswd()!=null)){
			if((params.getUserid().equals("kenny")) && (params.getPasswd().equals("kennypass"))) {
				switch(params.getMessageid()){
					case "1":
						json = new JSONObject("{\"messageid\":\"1\",\"title\":\"今天要去那裡跨年呢？\",\"content\":\"我是阿宅今天在想要去那裡跨年\"}");
						break;
					case "2":
						json = new JSONObject("{\"messageid\":\"2\",\"title\":\"我想要喝一杯有誰要和我喝\",\"content\":\"我想喝個爛醉有誰要和我喝的趕快報數\"}");
						break;
					case "3":
						json = new JSONObject("{\"messageid\":\"3\",\"title\":\"要不要賭一百今天不會加班？\",\"content\":\"我可以和你賭一百今天不會再加班了(應該啦)\"}");
						break;
					default:
						break;
				}
			}
		}
		return json;
	}
}
