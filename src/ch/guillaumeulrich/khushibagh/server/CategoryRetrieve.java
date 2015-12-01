package ch.guillaumeulrich.khushibagh.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ch.guillaumeulrich.khushibagh.element.Category;

import com.example.khushibagh.R;


/**
 * Async class that retrieves a random quiz question. If there is an error
 * (network, JSON, etc), it will return null (T0D0: add real error handling).
 */
@SuppressLint("NewApi") public class CategoryRetrieve extends AsyncTask<Void, Void, List<Category>> {
	private static final int SOCKET_TIMEOUT = 10 * 1000;
	private static final int CONNECTION_TIMEOUT = 5 * 1000;
	private static final int BUFFER_SIZE = 512;

	private static final String PATH = HttpClientFactory.HOST+"category/list/";
	
	private Throwable exception = null;
	private Activity ctx;

	public CategoryRetrieve(Activity ctx) {
		this.ctx = ctx;
	}

	/**
	 * Retrieve the quiz question in background.
	 * @param listener The listener for the callback
	 * @return The quiz question
	 */
	@Override
	protected List<Category> doInBackground(Void... params) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);

		AbstractHttpClient client = HttpClientFactory.getInstance();
		client.setParams(httpParams);
		Log.i("Lists", PATH);
		HttpGet request = new HttpGet(PATH);

		try {
			HttpResponse response = client.execute(request);

			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HttpStatus.SC_OK) {
				exception = new IOException("Invalid response from server: "
						+ status.toString());
			} else {
				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();

				ByteArrayOutputStream content = new ByteArrayOutputStream();

				// Read response into a buffered stream
				int readBytes = 0;
				byte[] sBuffer = new byte[BUFFER_SIZE];
				while ((readBytes = inputStream.read(sBuffer)) != -1) {
					content.write(sBuffer, 0, readBytes);
				}

				// Return result from buffered stream
				String dataAsString = new String(content.toByteArray(), Charset.forName("ISO-8859-1"));


				Log.d("Json", dataAsString);
				return Category.fromJson(dataAsString);
			}
		} catch (IOException e) {
			exception = e;
		} catch (JSONException e) {
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(final List<Category> result) {
		final Spinner spinner = (Spinner) ctx.findViewById(R.id.spinnerCategory);
				
		if(exception == null) {
			Category.categories = new ArrayList<Category>(result);
			ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(ctx, R.layout.spinner_category, result) {
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					if(position < result.size()-1) {
						TextView tv = null;
						if(convertView == null) {
							convertView = LayoutInflater.from(ctx).inflate(R.layout.spinner_category, parent);
							tv = (TextView) convertView.findViewById(R.id.textCategory);
							convertView.setTag(tv);
						} else {					
							tv = (TextView) convertView.getTag();
						}
						tv.setText(result.get(position).toString());
					}
					return convertView;
				}
			};	
			
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);

			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
					EditText et = (EditText)ctx.findViewById(R.id.editNewCategory);

					if(pos == result.size()-1) {
						et.setEnabled(true);
					} else {
						et.setEnabled(false);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});


		} else {
			Toast.makeText(ctx, exception.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
}
