package ch.guillaumeulrich.khushibagh.element;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Category extends ListItem {

	public static List<Category> categories;
	
	private String name;
	private int id;

	public Category(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public Category(String json) throws JSONException {
		JSONObject obj = new JSONObject(json);
		
        id = obj.optInt("id");
        name = obj.optString("category");
	}
	
	public static List<Category> fromJson(String json) throws JSONException {
		JSONObject obj = new JSONObject(json);
        JSONArray lists = obj.optJSONArray("list");

        List<Category> res = new ArrayList<Category>();
        for (int i = 0; i < lists.length(); i++) {
            res.add(new Category(lists.optString(i)));
        }
        return res;
	}

	public String toString() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void onClick() {}
	
}
