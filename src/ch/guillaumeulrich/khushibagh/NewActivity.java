package ch.guillaumeulrich.khushibagh;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import ch.guillaumeulrich.khushibagh.element.Category;
import ch.guillaumeulrich.khushibagh.server.Add;
import ch.guillaumeulrich.khushibagh.server.CategoryRetrieve;

import com.example.khushibagh.R;

public class NewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		
		CategoryRetrieve cr = new CategoryRetrieve(this);
		cr.execute();
		
		final EditText name = (EditText)this.findViewById(R.id.editName);
		final EditText quantity = (EditText)this.findViewById(R.id.editQuantity);
		final Spinner spinner = (Spinner)this.findViewById(R.id.spinnerCategory);
		Button add = (Button)this.findViewById(R.id.buttonAdd);
		final Activity ctx = this;
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Add(ctx,name.getText().toString(),
						quantity.getText().toString(),
						Category.categories.get(spinner.getSelectedItemPosition())).execute();
				ctx.finish();
			}
		});
	}
}
