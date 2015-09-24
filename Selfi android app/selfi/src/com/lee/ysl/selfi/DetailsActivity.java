package com.lee.ysl.selfi;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;



@SuppressLint("NewApi") public class DetailsActivity extends Activity {
    private String mBackgroundFilename;
    private View mBackgroundContainer;
    private ImageView mBlurImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        mBackgroundContainer = findViewById(R.id.container);
        mBlurImage = (ImageView) findViewById(R.id.blur_image);
        View mFigure = findViewById(R.id.figure);

        setBlurBackground();

        mFigure.setBackgroundResource(getIntent().getIntExtra(Constants.DROID_ID, R.drawable.chocolate));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1) @SuppressLint("NewApi") private void setBlurBackground() {
        mBackgroundFilename = getIntent().getStringExtra(Constants.BLUR_FILENAME);
        if(!TextUtils.isEmpty(mBackgroundFilename)){
            mBackgroundContainer.setVisibility(View.VISIBLE);
            Bitmap background = Utils.loadBitmapFromFile(mBackgroundFilename);
            if (background != null) {
                mBlurImage.setImageBitmap(background);
                mBlurImage.animate().alpha(1).setDuration(1000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanupBlurBackground();
    }

    private void cleanupBlurBackground() {
        if(!TextUtils.isEmpty(mBackgroundFilename)){
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    Utils.deleteFile(mBackgroundFilename);
                    return null;
                }
            }.execute();
        }
    }
}
