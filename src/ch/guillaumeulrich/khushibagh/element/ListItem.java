package ch.guillaumeulrich.khushibagh.element;

public abstract class ListItem {

	private int position;
	
	abstract public String toString();
	abstract public void onClick();
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	
	
}
