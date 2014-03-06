package ch.guillaumeulrich.khushibagh.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import ch.guillaumeulrich.khushibagh.element.Category;

@SuppressLint("NewApi") public class Add extends AsyncTask<Void, Void, String> {
	private static final int SOCKET_TIMEOUT = 10 * 1000;
	private static final int CONNECTION_TIMEOUT = 5 * 1000;
	private static final int BUFFER_SIZE = 512;

	private static final String HOST = "http://guillaumeulrich.ch/courses/";

	private Throwable exception = null;

	private Activity ctx;
	private String name;
	private String quantity;
	private Category category;
	
	public Add(Activity ctx, String name, String quantity, Category category) {
		this.ctx = ctx;
		this.name = name;
		this.quantity = quantity;
		this.category = category;
	}
	
	/**
	 * Retrieve the quiz question in background.
	 * @param listener The listener for the callback
	 * @return The quiz question
	 */
	@Override
	protected String doInBackground(Void... params) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);

		AbstractHttpClient client = SwengHttpClientFactory.getInstance();
		client.setParams(httpParams);
		String callUrl = HOST + "add/?element="+name+"&cid="+category.getId()+"&qtty="+quantity;
		
		Log.i("Lists", callUrl);
		HttpGet request = new HttpGet(callUrl);

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
				return dataAsString;
			}
		} catch (IOException e) {
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		String message = result;
		if(exception != null) {
			message = exception.getMessage();
		}
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
	}
}
