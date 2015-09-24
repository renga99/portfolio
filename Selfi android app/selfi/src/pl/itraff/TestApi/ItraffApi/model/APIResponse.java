package pl.itraff.TestApi.ItraffApi.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIResponse implements Serializable {
	private static final long serialVersionUID = 1708657752355572093L;
	protected int status;
	protected String message;
	protected ArrayList<APIObject> objects;

	public APIResponse(String jsonToParse) {
		objects = new ArrayList<APIObject>();
		try {
			JSONObject resp = new JSONObject(jsonToParse);
			this.status= resp.getInt("status");
			if (status != 0) {
				this.message = resp.getString("message");
				return;
			}
			JSONArray obj = resp.getJSONArray("objects");
			for (int i = 0; i < obj.length(); ++i) {
				objects.add(new APIObject(obj.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public ArrayList<APIObject> getObjects() {
		return objects;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setObjects(ArrayList<APIObject> objects) {
		this.objects = objects;
	}

}
