package ch.guillaumeulrich.khushibagh.element;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.khushibagh.R;

public class ListAdapter {

	private List<Element> elements;
	private Context ctx;

	public ListAdapter(List<Element> elements, Context ctx) {
		super();
		this.elements = elements;
		this.ctx = ctx;
	}
	
	public ArrayAdapter<ListItem> createAdapter() {
		
		final List<ListItem> items = new ArrayList<ListItem>();
		for (int i = 0; i < elements.size(); i++) {
			if(i == 0 || !elements.get(i).getCategory().equals(elements.get(i-1).getCategory())) {
				Category c = new Category(elements.get(i).getCategory(),0);
				c.setPosition(items.size()-1);
				items.add(c);
			}
			Element e = elements.get(i);
			e.setPosition(items.size()-1);
			items.add(e);
		}
		
		ArrayAdapter<ListItem> adapter = new ArrayAdapter<ListItem>(ctx, R.layout.list_item, items) {
			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				ViewHolder holder;
				if(convertView == null) {
					holder = new ViewHolder();
					if(items.get(position) instanceof Category) {
						convertView = LayoutInflater.from(ctx).inflate(R.layout.list_category, null);
						holder.checkBox = null;
					} else {
						convertView = LayoutInflater.from(ctx).inflate(R.layout.list_item, null);
						holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
						
						holder.checkBox.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								items.get(position).onClick();
							}
						});
						
					}
					holder.tvName = (TextView)convertView.findViewById(R.id.text);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.tvName.setText(items.get(position).toString());
				return convertView;
			}
		};
		
		return adapter;
	}
	
	private class ViewHolder {
		TextView tvName;
		CheckBox checkBox;
	}
	
}
