package ch.guillaumeulrich.khushibagh.element;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Element extends ListItem {

	public static List<Element> elements;
	
	private int id;
	private String name;
	private String category;
	private String quantity;
	private boolean selected = false;

	public Element(String json) throws JSONException {
		JSONObject obj = new JSONObject(json);
		
        id = obj.optInt("id");
        name = obj.optString("name");
        category = obj.optString("category");
        quantity = obj.optString("quantity");
	}
	
	public static List<Element> fromJson(String json) throws JSONException {
		JSONObject obj = new JSONObject(json);
        JSONArray lists = obj.optJSONArray("list");

        List<Element> res = new ArrayList<Element>();
        for (int i = 0; i < lists.length(); i++) {
            res.add(new Element(lists.optString(i)));
        }
        return res;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getQuantity() {
		return quantity;
	}
	
	public String toString() {
		return getName()+(getQuantity().isEmpty() ? "" : " ("+getQuantity()+")");
	}
	
	public void onClick() {
		selected = !selected;
	}
	
	public boolean isSelected() {
		return selected;
	}

}
