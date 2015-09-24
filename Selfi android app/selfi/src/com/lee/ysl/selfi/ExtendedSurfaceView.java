package com.lee.ysl.selfi;

import pl.itraff.TestApi.ItraffApi.model.APIObject;
import pl.itraff.TestApi.ItraffApi.model.APIResponse;
import pl.itraff.TestApi.ItraffApi.model.Point;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ExtendedSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	private APIResponse response;
	private Bitmap bitmap;
	private Paint paint, p, pText;
	
	private int size = 50;
	private int colors[] = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA};

	public ExtendedSurfaceView(Context context, Bitmap bitmap, APIResponse response) {
		super(context);
		this.response = response;
		this.bitmap = bitmap;
		this.getHolder().addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);

		
		
		
		// set drawing colour
		p = new Paint();
		p.setColor(Color.WHITE);
		p.setStrokeWidth(5);
		pText = new Paint();
		pText.setColor(Color.RED);
		pText.setTextSize(size);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Bitmap img = Bitmap.createScaledBitmap( bitmap, canvas.getWidth(), canvas.getHeight(), true );
		float xRatio = (float) img.getWidth() / (float) bitmap.getWidth();
		float yRatio = (float) img.getHeight() / (float) bitmap.getHeight();
	    bitmap.recycle();
		canvas.drawBitmap(img, 0, 0, paint);

		int tmp = 0;

		for (APIObject obj : response.getObjects()) {
			pText.setColor(colors[tmp]);
			tmp = (tmp+1)%colors.length;
			for (int i = 0; i < obj.getLocation().size(); i++) {
				Point p1 = obj.getLocation().get(i);
				Point p2 = obj.getLocation().get(
						(i + 1) % obj.getLocation().size());
				canvas.drawLine(p1.getX()*xRatio, p1.getY()*yRatio, p2.getX()*xRatio, p2.getY()*yRatio, p);
			}
			int textX = obj.getLocation().get(0).getX() + size;
			if (textX > bitmap.getWidth())
				textX = bitmap.getWidth() - size;
			int textY = obj.getLocation().get(0).getY() + 2*size;
			if (textY > bitmap.getHeight())
				textY = bitmap.getHeight() - 2 * size;
				
			canvas.drawText(obj.getName(), textX*xRatio, textY*yRatio, pText);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(final SurfaceHolder holder) {
		new Thread(){
			@SuppressLint("WrongCall")
			public void run() {
				Canvas canvas = null;
				try {
		            canvas = holder.lockCanvas(null);
		            synchronized (holder) {
		            	ExtendedSurfaceView.this.onDraw(canvas);
					}
				 } finally {
					if (canvas != null) {
						holder.unlockCanvasAndPost(canvas);
					}
				 }
			}
		}.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
}
