package pl.itraff.TestApi.ItraffApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import pl.itraff.TestApi.ItraffApi.model.APIResponse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * AsyncTask used to get response from api. In PostExecute it retruns:
 * 
 * <pre>
 * {
 * 	&#064;code
 * 	Message msg = new Message();
 * 	Bundle data = new Bundle();
 * 	data.putInt(ItraffApi.STATUS, status);
 * 
 * 	data.putString(ItraffApi.RESPONSE, response);
 * 
 * 	msg.setData(data);
 * 	itraffApiHandler.sendMessage(msg);
 * }
 * </pre>
 * <p>
 * 
 * <pre>
 * {@code
 * Where STATUS is Integer:
 * < 0 application error
 * 0 status ok
 * > 0 api error
 * 
 * and RESPONSE is json containing whole response from api
 * }
 * </pre>
 */
public class ItraffApiPostPhoto extends AsyncTask<Void, Void, Integer> {

	private String TAG = "SimplePhotoRecognition";
	Boolean debug = false;

	String response;
	HttpPost httpPost;
	Handler itraffApiHandler;
	private APIResponse apiResp;

	public ItraffApiPostPhoto(Handler itraffApiHandler, HttpPost httpPost,
			String debugTag, Boolean debug) {
		this.httpPost = httpPost;
		this.itraffApiHandler = itraffApiHandler;
		this.TAG = debugTag;
		this.debug = debug;
	}

	@Override
	protected Integer doInBackground(Void... params) {
		Integer status = -1;
		if (this.httpPost == null) {
				this.response = "Empty input data";
				return -1;
		}

		// post photo and get json response
		response = post();
		if (response != null) {
			apiResp = new APIResponse(response);
			status = apiResp.getStatus();
		}

		return status;
	}

	@Override
	protected void onPostExecute(Integer status) {
		super.onPostExecute(status);
		// callback to method in MainActivity with api response
		if (itraffApiHandler != null) {
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putSerializable(ItraffApi.RESPONSE, apiResp);

			msg.setData(data);
			itraffApiHandler.sendMessage(msg);
		}
	}

	private String post() {
		HttpClient httpClient = ItraffApiHttpClient.getHttpClient();
		BufferedReader in = null;
		try {
			log(httpPost.getURI().toString());
			HttpResponse response = httpClient.execute(httpPost);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent(), "UTF-8"));

			String jsonResponse = unescape(getResponseJson(in));

			return jsonResponse;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getResponseJson(BufferedReader in) throws IOException {
		StringBuffer sb = new StringBuffer("");
		String line = "";
		String NL = System.getProperty("line.separator");
		while ((line = in.readLine()) != null) {
			sb.append(line + NL);
		}
		in.close();
		return sb.toString();
	}

	public void log(String message) {
		if (debug) {
			Log.v(TAG, message);
		}
	}

	protected String unescape(String res) {
		Pattern p = Pattern.compile("\\\\u([0-9A-F]{4})", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(res);
		while (m.find()) {
			res = res
					.replaceAll(
							"\\" + m.group(0),
							Character.toString((char) Integer.parseInt(
									m.group(1), 16)));
		}
		return res;
	}
}
