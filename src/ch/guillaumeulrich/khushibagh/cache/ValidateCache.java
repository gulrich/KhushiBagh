package ch.guillaumeulrich.khushibagh.cache;

import java.util.List;

import ch.guillaumeulrich.khushibagh.element.Element;
import ch.guillaumeulrich.khushibagh.server.Validate;

public class ValidateCache extends Cache {
	
	private static final long serialVersionUID = 1L;
	
	
	private List<Element> elements;
	
	public ValidateCache(List<Element> elements) {
		super(Cache.VALIDATE);
		this.elements = elements;
	}
	
	public void execute() {
		new Validate(Cache.context,elements).execute();
	}
	
}