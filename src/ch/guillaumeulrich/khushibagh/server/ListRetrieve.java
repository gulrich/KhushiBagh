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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ch.guillaumeulrich.khushibagh.element.Element;
import ch.guillaumeulrich.khushibagh.element.ListAdapter;
import ch.guillaumeulrich.khushibagh.element.ListItem;

import com.example.khushibagh.R;


/**
 * Async class that retrieves a random quiz question. If there is an error
 * (network, JSON, etc), it will return null (T0D0: add real error handling).
 */
@SuppressLint("NewApi") public class ListRetrieve extends AsyncTask<Void, Void, List<Element>> {
	private static final int SOCKET_TIMEOUT = 10 * 1000;
	private static final int CONNECTION_TIMEOUT = 5 * 1000;
	private static final int BUFFER_SIZE = 512;

	private static final String PATH = HttpClientFactory.HOST+"item/list/";

	private Throwable exception = null;
	private Activity ctx;

	public ListRetrieve(Activity ctx) {
		this.ctx = ctx;
	}

	/**
	 * Retrieve the quiz question in background.
	 * @param listener The listener for the callback
	 * @return The quiz question
	 */
	@Override
	protected List<Element> doInBackground(Void... params) {
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
				return Element.fromJson(dataAsString);
			}
		} catch (IOException e) {
			exception = e;
		} catch (JSONException e) {
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(final List<Element> result) {
		ListView list = (ListView) ctx.findViewById(R.id.list);
		if(exception != null) {
			Log.d("Exception",exception.getMessage());
			Toast.makeText(ctx, exception.getMessage(), Toast.LENGTH_LONG).show();
		} else {
			Log.d("",result.toString());
			Element.elements = new ArrayList<Element>(result);
			ArrayAdapter<ListItem> adapter = new ListAdapter(result,ctx).createAdapter();
			list.setAdapter(adapter);
		}
	}
}
