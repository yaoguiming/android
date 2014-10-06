package cn.why.json;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;

import cn.why.domain.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class JsonUtils {

	/**
	 * 使用gson解析json数组
	 * @param jsonData
	 */
	public void parseJsonArray(String jsonData) {
		JsonReader jsonReader = new JsonReader(new StringReader(jsonData));
		try {
			jsonReader.beginArray();//开始解析json数组
			while (jsonReader.hasNext()) {
				jsonReader.beginObject();//开始解析json对象
				while (jsonReader.hasNext()) {
					String tagName = jsonReader.nextName();
					if (tagName.equals("name")) {
						String name = jsonReader.nextString();
						System.out.println("name--->>>"+name);
					}
					if (tagName.equals("age")) {
						int age = jsonReader.nextInt();
						System.out.println("age--->>>"+age);
					}
				}
				jsonReader.endObject();//结束json对象解析
			}
			jsonReader.endArray();//结束json数组解析
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用gson解析json对象，与User实体对象对应
	 * @param jsonData
	 */
	public void parseJsonObjectToUser(String jsonData) {
		Gson gson = new Gson();
		User user = gson.fromJson(jsonData, User.class);//将json对象转化为user对象
		System.out.println("name--->>>"+user.getName());
		System.out.println("age--->>>"+user.getAge());
	}

	/**
	 * 使用gson解析json对象，与User实体对象对应的链式集合
	 * @param jsonData
	 */
	public void parseJsonArrayToUsers(String jsonData) {
		Type listType = new TypeToken<LinkedList<User>>() {}.getType();
		Gson gson = new Gson();
		LinkedList<User> users = gson.fromJson(jsonData, listType);//将json数组转化为user对象集合
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			System.out.println("name--->>>"+user.getName());
			System.out.println("age--->>>"+user.getAge());
		}
	}
}
