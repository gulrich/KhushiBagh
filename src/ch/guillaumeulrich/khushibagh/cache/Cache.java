package ch.guillaumeulrich.khushibagh.cache;

import java.io.Serializable;

import ch.guillaumeulrich.khushibagh.ListActivity;

public abstract class Cache implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	public static final int ADD = 0;
	public static final int VALIDATE = 1;
	public static final int LIST = 2;
	
	public static ListActivity context;
	
	public Cache(int action) {
		
		// write in a file
	}
	
	public abstract void execute();
	
	public void start() {
		// check internet connection
		// if connection:
		//		read from file
		// 		execute();
	}
	
}