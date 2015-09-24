package com.lee.ysl.selfi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.itraff.TestApi.ItraffApi.ItraffApi;
import pl.itraff.TestApi.ItraffApi.model.*;
import lee.yslHackathon.selfie.TransactionApp;

public class CatActivity extends Activity {

	private static final String TAG = "CatActivity";
	private CardArrayAdapter cardArrayAdapter,cardArrayAdapter1;
	private ListView listView,listView1;

	static Boolean debug = false;

	// only for demo to enable user to edit this values on screen
	private String CLIENT_API_KEY = "21bf64761e";
	private Integer CLIENT_API_ID = new Integer(44603);

	public static final String ID = "id";
	public static final String KEY = "key";

	public static final String PREFS_NAME = "iTraffTestApiPreferences";

	private static final int RESULT_BMP_DAMAGED = 128;

	private static final int REQUEST_SEND = 100;
	private static final int REQUEST_PHOTO = 101;

	private TextView responseView;

	private EditText clientIdEdit;
	private EditText clientApiKeyEdit;

	// minimum size of picture to scale
	final int REQUIRED_SIZE = 400;

	ProgressDialog waitDialog;

	protected APIResponse response;

	private Button imageShowBtn;

	private File outFile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cat);
		listView = (ListView) findViewById(R.id.card_listView);
		listView1 = (ListView) findViewById(R.id.card_listView1);

		listView.addHeaderView(new View(this));
		listView.addFooterView(new View(this));

		listView1.addHeaderView(new View(this));
		listView1.addFooterView(new View(this));

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		outFile = new File(Environment.getExternalStorageDirectory(), "scan.jpg");
		System.out.println(intent);
		Log.d(TAG, action);
		Log.d(TAG, type);
		if (action.equals(Intent.ACTION_SEND) && type.contains("image/")) {
			Uri receivedUri = (Uri) intent
					.getParcelableExtra(Intent.EXTRA_STREAM);
			InputStream fis = null;
			OutputStream fos = null;
			try {
				fis = this.getContentResolver().openInputStream(receivedUri);
				fos = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = fis.read(buf)) > 0) {
					fos.write(buf, 0, bytesRead);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			onActivityResult(REQUEST_SEND, Activity.RESULT_OK, intent);
		}


		cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
		cardArrayAdapter1 = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);


		Card card = new Card("Account Image","Account Summary");
		Card card1 = new Card("Transaction Image","Transactions");
		Card card2 = new Card("Port image","Portfolio Manager");
		cardArrayAdapter.add(card);
		cardArrayAdapter.add(card1);
		cardArrayAdapter.add(card2);

		listView.setAdapter(cardArrayAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				  View parentView = (View) view.getParent();
				 String   item = ((TextView) parentView
				            .findViewById(R.id.line2)).getText().toString();
                System.out.println("this is :"+item);
				if(item == "Account Summary"){

					makePhotoClick();
				}

			}

		});

		Card card3 = new Card("Bill image","Bill reminder");
		Card card4 = new Card("Net Image","Net Worth");
		Card card5 = new Card("Reminders Image","Reminders");
		cardArrayAdapter1.add(card3);
		cardArrayAdapter1.add(card4);
		cardArrayAdapter1.add(card5);

		listView1.setAdapter(cardArrayAdapter1);
	}

	@Override
	protected void onStop() {
//		getValuesFromEdit();
//		savePrefs();
		super.onStop();
	}

	public void infoClickHandler(View v) {
		//		Intent intent = new Intent(this, InfoActivity.class);
		//		startActivity(intent);
	}

	private void getValuesFromEdit() {
		// get values from edti text fields
		String clientApiIdString = clientIdEdit.getText().toString();
		Integer clientApiId = 0;
		if (clientApiIdString.length() > 0) {
			try {
				clientApiId = Integer.parseInt(clientApiIdString);
				CLIENT_API_ID = clientApiId;
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"ID should be a correct number!", Toast.LENGTH_LONG)
						.show();
			}
		}
		CLIENT_API_KEY = clientApiKeyEdit.getText().toString();
	}

	public void showResult(View v) {
		if (response == null || response.getStatus() != 0) {
			return;
		}
		Intent intent = new Intent(this, ImageShowActivity.class);
		intent.putExtra("data", response);
		intent.putExtra("bitmap", pictureData);
		startActivity(intent);
	}

	public void makePhotoClick() {
		//getValuesFromEdit();

		if (CLIENT_API_KEY != null && CLIENT_API_KEY.length() > 0
				&& CLIENT_API_ID != null && CLIENT_API_ID > 0) {
			savePrefs();

			// Intent to take a photo
			outFile.delete();
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(outFile));
			takePictureIntent.putExtra(MediaStore.EXTRA_FULL_SCREEN, true);
			takePictureIntent.putExtra(MediaStore.EXTRA_SHOW_ACTION_ICONS, false);
			startActivityForResult(takePictureIntent, REQUEST_PHOTO);
		} else {
			Toast.makeText(getApplicationContext(),
					"Invalid credentials", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private void savePrefs() {
		// Save prefs
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(ID, CLIENT_API_ID);
		editor.putString(KEY, CLIENT_API_KEY);
		// Commit the edits!
		editor.commit();
	}
	// handler that receives response from api
	@SuppressLint("HandlerLeak")
	private Handler itraffApiHandler = new Handler() {
		// callback from api
		@Override
		public void handleMessage(Message msg) {
			dismissWaitDialog();
			Bundle data = msg.getData();
			String name ="  ";
			if (data != null) {
				response = (APIResponse) data
						.getSerializable(ItraffApi.RESPONSE);
				// status ok
				if (response.getStatus() == 0) {
					String text = "Status: 0\tObjects: [";
					for (APIObject obj : response.getObjects()) {
						text = obj.getName() ;
					}
					 name = text;
					text = text.substring(0, text.length() - 2) + "]";
					
					Toast.makeText(getApplicationContext(),
							text,
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(CatActivity.this, CardListActivity.class);
					intent.putExtra("message", name);
					startActivity(intent);
					// application error (for example timeout)
				} else if (response.getStatus() == -1) {
		
					Intent intent = new Intent(CatActivity.this, CardListActivity.class);
					intent.putExtra("message", name);
					startActivity(intent);
					// error from api
				} else {

					Intent intent = new Intent(CatActivity.this, CardListActivity.class);
					intent.putExtra("message", name);
					startActivity(intent);
				}
			}
		}
	};

	private byte[] pictureData;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Bitmap image = null; // (Bitmap) bundle.get("data");
			InputStream fis = null;
			try {
				fis = new FileInputStream(outFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Config.RGB_565;
			options.inDither = true;
			options.inSampleSize = 4;
			image = BitmapFactory.decodeStream(fis, null, options);

			if (image == null) {
				return;
			}
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			pictureData = stream.toByteArray();
			image.recycle();
			image = null;

			if (pictureData != null) {
				// chceck internet connection
				if (ItraffApi.isOnline(getApplicationContext())) {
					showWaitDialog();
					SharedPreferences prefs = PreferenceManager
							.getDefaultSharedPreferences(getBaseContext());
					// send photo
					ItraffApi api = new ItraffApi(CLIENT_API_ID,
							CLIENT_API_KEY, TAG, true);
					Log.v("KEY", CLIENT_API_ID.toString());
					if (prefs.getString("mode", "single").equals("multi")) {
						api.setMode(ItraffApi.MODE_MULTI);
					} else {
						api.setMode(ItraffApi.MODE_SINGLE);
					}

					api.sendPhoto(pictureData, itraffApiHandler,
							prefs.getBoolean("allResults", true));
					


				} else {
					// show message: no internet connection
					// available.

					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.not_connected),
							Toast.LENGTH_LONG).show();
				}
			}
		} else if (resultCode == RESULT_BMP_DAMAGED) {
			log("RESULT_BMP_DAMAGEDl");
		}
	}

	private void showWaitDialog() {
		if (waitDialog != null) {
			if (!waitDialog.isShowing()) {
				waitDialog.show();
			}
		} else {
			waitDialog = new ProgressDialog(this);
			waitDialog.setMessage(getResources().getString(
					R.string.wait_message));
			waitDialog.show();
		}
	}

	private void dismissWaitDialog() {
		try {
			if (waitDialog != null && waitDialog.isShowing()) {
				waitDialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void log(String msg) {
		if (debug) {
			Log.v(TAG, msg);
		}
	}


}
