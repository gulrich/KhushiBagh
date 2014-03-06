package ch.guillaumeulrich.khushibagh.cache;

import ch.guillaumeulrich.khushibagh.element.Category;
import ch.guillaumeulrich.khushibagh.server.Add;

public class AddCache extends Cache {
	
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	private String quantity;
	private Category category;
	
	public AddCache(String name, String quantity, Category category) {
		super(Cache.ADD);
		this.name = name;
		this.quantity = quantity;
		this.category = category;
	}
	
	public void execute() {
		new Add(Cache.context,name,quantity,category).execute();
	}
	
}
