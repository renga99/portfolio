package pl.itraff.TestApi.ItraffApi;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import pl.itraff.TestApi.ItraffApi.model.APIResponse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Example usage:
 * 
 * <p>
 * Call api:
 * 
 * <pre>
 * {
 * 	&#064;code
 * 	ItraffApi api = new ItraffApi(CLIENT_API_ID, CLIENT_API_KEY, TAG, true);
 * 
 * 	api.sendPhoto(photo, itraffApiHandler);
 * }
 * </pre>
 * <p>
 * Get response:
 * 
 * <pre>
 * {
 * 	&#064;code
 * 	private Handler itraffApiHandler = new Handler() {
 * 		// callback from api
 * 		&#064;Override
 * 		public void handleMessage(Message msg) {
 * 			Bundle data = msg.getData();
 * 			if (data != null) {
 * 				Integer status = data.getInt(ItraffApi.STATUS, -1);
 * 				String response = data.getString(ItraffApi.RESPONSE);
 * 
 * 				if (status == 0) { // status ok
 * 					// TODO response contains json with your data
 * 				} else if (status == -1) { // application error (for example
 * 											// timeout)
 * 					// TODO show application error
 * 				} else { // error from api
 * 					// TODO get &quot;message&quot; from response json - it contains api
 * 					// error
 * 				}
 * 			}
 * 		}
 * 	};
 * }
 * </pre>
 */
public class ItraffApi {

	private String TAG = "TestApi";
	public static final String API_URL = "http://recognize.im/v2/recognize/";
	private Integer clientId;
	private String clientKey;
	private String customUrl;
	Boolean debug = true;
	private String mode = MODE_SINGLE;
	private String mockedResult = "";

	public static final String RESPONSE = "response";
	public static final String HASH_HEADER = "x-itraff-hash";

	public final static String ACCEPT = "Accept";
	public final static String APPLICATION_JSON = "application/json";

	public static final String MODE_SINGLE = "single";
	public static final String MODE_MULTI = "multi";
	public static final String MODE_SHELF = "shelf";

	/**
	 * ItraffApi public constructor
	 * 
	 * @param clientId
	 *            client api id
	 * @param clientKey
	 *            client api key
	 * @param debugTag
	 *            custom debug TAG
	 * @param debug
	 *            enable Log if true
	 */
	public ItraffApi(Integer clientId, String clientKey, String debugTag,
			Boolean debug) {
		this.clientId = clientId;
		this.debug = debug;
		this.TAG = debugTag;
		this.clientKey = clientKey;
	}

	/**
	 * ItraffApi public constructor
	 * 
	 * @param clientId
	 *            client api id
	 * @param clientKey
	 *            client api key
	 * @param customUrl
	 *            custom url; default is: "http://recognize.im/recognize/"
	 * @param debugTag
	 *            custom debug TAG
	 * @param debug
	 *            enable Log if true
	 */
	public ItraffApi(Integer clientId, String clientKey, String customUrl,
			String debugTag, Boolean debug) {
		this.clientId = clientId;
		this.debug = debug;
		this.customUrl = customUrl;
		this.TAG = debugTag;
		this.clientKey = clientKey;
	}

	/**
	 * Sends photo to recognition server
	 * 
	 * @param photo
	 *            Bitmap image that is send through api
	 * @param itraffApiHandler
	 *            Handler used to return message from api
	 * @param allResults
	 *            if true return full list of results; only best match otherwise
	 */
	public void sendPhoto(byte[] photo, Handler itraffApiHandler,
			boolean allResults) {
		if (mockedResult.length() > 0) {
			if (itraffApiHandler != null) {
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putSerializable(ItraffApi.RESPONSE, new APIResponse(mockedResult));

				msg.setData(data);
				itraffApiHandler.sendMessage(msg);
				return;
			}
		}
		try {
			HttpPost postPhoto = getPostPhotoRequest(photo, allResults);
			ItraffApiPostPhoto postPhotoAsyncTask = new ItraffApiPostPhoto(
					itraffApiHandler, postPhoto, TAG, debug);
			postPhotoAsyncTask.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends photo to recognition server
	 * 
	 * @param photo
	 *            Bitmap image that is send through api
	 * @param itraffApiHandler
	 *            Handler used to return message from api
	 */
	public void sendPhoto(byte[] photo, Handler itraffApiHandler) {
		sendPhoto(photo, itraffApiHandler, false);
	}

	private HttpPost getPostPhotoRequest(byte[] photo, boolean allResults)
			throws UnsupportedEncodingException {
		if (photo == null)
			return null;
		String requestUrl = customUrl;
		String paramsURL = "";
		if (mode.equals(MODE_MULTI)) {
			paramsURL = "multi/";
			photo = scaleBitmap(photo, 100000, 250000);
		} else if(mode.equals(MODE_SINGLE)) {
			paramsURL = "single/";
			photo = scaleBitmap(photo, 50000, 120000);
		} else if(mode.equals(MODE_SHELF)) {
			paramsURL = "shelf/all/";
		}
		if (allResults)
			paramsURL += "all/";
		if (requestUrl != null) {
			if (clientId != null) {
				requestUrl += paramsURL + clientId;
			}

		} else {
			if (clientId != null) {
				requestUrl = API_URL + paramsURL + clientId;
			} else {
				requestUrl = API_URL + paramsURL;
			}
		}

		HttpPost postRequest = new HttpPost(requestUrl);
		log("Request url: " + requestUrl);

		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 30000);
		postRequest.setParams(params);

		// create entity
		HttpEntity entity = new ByteArrayEntity(photo);
		postRequest.setEntity(entity);

		// get hash MD5(clientKey+image)
		String hash = ItraffApi.getMD5FromKeyAndImage(clientKey, photo);

		log("hash MD5(clientKey+image):");
		log(hash);

		Header header = new BasicHeader(HTTP.CONTENT_TYPE, "image/jpeg");
		postRequest.addHeader(header);
		header = new BasicHeader(HASH_HEADER, hash);
		postRequest.addHeader(header);
		header = new BasicHeader(ACCEPT, APPLICATION_JSON);
		postRequest.addHeader(header);

		return postRequest;
	}

	/**
	 * Generates MD5 from client api key and image bytes
	 * 
	 * @param clientKey
	 *            client api key
	 * @param image
	 *            byte[] image
	 * @return md5 string
	 */
	public static String getMD5FromKeyAndImage(String clientKey, byte[] image) {
		String hash = null;
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.reset();
			md.update(clientKey.getBytes("UTF-8"));
			md.update(image);
			byte[] array = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			hash = sb.toString();
		} catch (Exception e) {
			hash = null;
		}
		return hash;
	}

	/**
	 * Check if user is connected to internet
	 * 
	 * @param appContext
	 *            application context
	 * @return true if is connected or connecting
	 */
	public static boolean isOnline(Context appContext) {
		ConnectivityManager cm = (ConnectivityManager) appContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public void mock(String mock) {
		this.mockedResult  = mock;
	}
	
	/**
	 * Logs message if debug == true using Log.v(TAG, message)
	 * 
	 * @param message
	 *            message to log
	 */
	public void log(String message) {
		if (debug) {
			Log.v(TAG, message);
		}
	}

	/**
	 * scale bitmap to fit to maxWidth x maxheight bounding box
	 * 
	 * @param source
	 *            byte of bitmap we want to scale
	 * @return bytes of scaled bitmap
	 */
	protected static byte[] scaleBitmap(byte[] photo, float minArea,
			float maxArea) {
		Bitmap source = BitmapFactory.decodeByteArray(photo, 0, photo.length);
		float area = source.getWidth() * source.getHeight();
		if (area > minArea && area < maxArea)
			return photo;
		float scale;
		if (area < minArea) {
			scale = (float) Math.sqrt((minArea + 1000) / area); // adding some small value to balance rounding errors 
		} else
			scale = (float) Math.sqrt(maxArea / area);

		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
				source.getHeight(), matrix, false);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		result.compress(Bitmap.CompressFormat.JPEG, 80, stream);
		return stream.toByteArray();
	}
}
