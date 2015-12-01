package ch.guillaumeulrich.khushibagh;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import ch.guillaumeulrich.khushibagh.element.Element;
import ch.guillaumeulrich.khushibagh.element.ListAdapter;
import ch.guillaumeulrich.khushibagh.server.ListRetrieve;
import ch.guillaumeulrich.khushibagh.server.Validate;

import com.example.khushibagh.R;

public class ListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
				
		ListRetrieve lr = new ListRetrieve(this);
        lr.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_done:
	        	List<Element> tmp = new ArrayList<Element>(Element.elements);
	        	List<Element> selected = new ArrayList<Element>();
	        	
	        	for (int i = 0; i < tmp.size(); i++) {
	        		Element element = tmp.get(i);
					if(element.isSelected()) {
						Element.elements.remove(i);
						
						selected.add(element);
					}
				}
	        	new Validate(this, selected).execute();
	        	ListView list = (ListView) this.findViewById(R.id.list);
	        	list.setAdapter(new ListAdapter(Element.elements,this).createAdapter());
	        	
	            return true;
	        case R.id.action_new:
				Intent i = new Intent(this, NewActivity.class);
				this.startActivity(i);
	            return true;
	        case R.id.action_refresh:
	        	refresh();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void refresh() {
		ListRetrieve lr = new ListRetrieve(this);
        lr.execute();
	}
}
