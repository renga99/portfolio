package com.lee.ysl.selfi;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;

public class BlurTask extends AsyncTask<Void, Void, Void> {

    private Activity mContext;
    private int mBlurLayoutId;
    private Bitmap mDownScaled;
    private Intent mIntent;

    public BlurTask(Activity activity, Intent intent, int layoutId) {
        mContext = activity;
        mIntent = intent;
        mBlurLayoutId = layoutId;
    }

    private View getViewFrame() {
        return mContext.findViewById(this.mBlurLayoutId);
    }

    @Override
    protected Void doInBackground(Void... params) {
        mIntent.putExtra(Constants.BLUR_FILENAME, getBlurredBackgroundFilename());
        return null;
    }

    @Override
    protected void onPreExecute() {
        this.mDownScaled = Utils.drawViewToBitmap(getViewFrame(), Color.parseColor("#fff5f5f5"));
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mContext.startActivity(mIntent);
    }

    private String getBlurredBackgroundFilename()
    {
        Bitmap localBitmap = Blur.fastblur(this.mContext, this.mDownScaled, 8);
        String str = Utils.saveBitmapToFile(this.mContext, localBitmap);
        this.mDownScaled.recycle();
        localBitmap.recycle();
        return str;
    }
}
