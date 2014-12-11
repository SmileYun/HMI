package com.cqupt.ui.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.cqupt.ui.base.HMIActivity;

public class DynamicView extends SurfaceView implements Callback {
	private Bundle info;

	private SurfaceHolder mHolder;

	private int nowBitmapResID = -1;
	
	private Bitmap nowBitmap = null;
	
	private Point[] positions = new Point[3];
	
	private Paint[] paints = new Paint[3];
	
	private float[] textFactors = {0.099f, 0.17f, 0.14f};
	
	private Context mContext;
	
	private RectF rectF;
	
	private int mScreenW, mScreenH;
	
	
	public DynamicView(Context context) {
		this(context, null);
	}

	public DynamicView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DynamicView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		// 初始化画笔
		for (int i = 0; i < paints.length; i++) {
			paints[i] = new Paint();
			paints[i].setColor(Color.RED);
			Typeface mTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/microyahei.ttf");
			paints[i].setTypeface(mTypeface);
			paints[i].setTextSize(textFactors[i % 3]);
			paints[i].setAntiAlias(true);
		}
		if(mContext instanceof HMIActivity){
			mScreenW = ((HMIActivity)mContext).mScreenWidth;
			mScreenH = ((HMIActivity)mContext).mScreenHeight;
			rectF = new RectF(0, 0, mScreenW, mScreenH); // w和h分别是屏幕的宽和高，也就是你想让图片显示的宽和高
		}
	}


	private void drawDynamicView(Canvas canvas, Bundle moreInfo) {
		if (moreInfo.getInt("background_res_id") != nowBitmapResID) {
			nowBitmap = loadOptiBitmap(moreInfo.getInt("background_res_id"));
			Point[] _positions = (Point[]) moreInfo.getParcelableArray("locations");
			for(int i = 0; i < positions.length; i++){
				positions[i] = _positions[i];
			}
		}
		drawBackground(canvas, nowBitmap);
		
		if (positions != null) {
			drawText(canvas, positions, paints);
		}
		mHolder.unlockCanvasAndPost(canvas);
	}
	
	
	private void drawBackground(Canvas canvas, Bitmap bitmap) {
		// 清除原来绘制的
		paints[0].setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		canvas.drawPaint(paints[0]);
		paints[0].setXfermode(new PorterDuffXfermode(Mode.SRC));
		canvas.drawColor(Color.TRANSPARENT);
		canvas.drawBitmap(bitmap, null, rectF, null);
	}
	
	private void drawText(Canvas canvas, Point[] positions, Paint... text) {
		for(Point p : positions){
			String textDraw = info.getString("time");
			float textDrawLen = text[0].measureText(textDraw);
			canvas.drawText(textDraw, p.x - textDrawLen, p.y, text[0]);
		}
		
//		//时间			
//		String timeStr = String.valueOf(info.getInt("time"));
//		float timeLen = paints[0].measureText(timeStr);
//		canvas.drawText(timeStr, mScreenW * info.getFloat("")-timeLen, (mScreenH * info.getFloat("")), paints[0]);
//		
//		//推荐速度
//		String maxSpeed = String.valueOf(info.getInt("maxspeed"));
//		float SpeedLen = paints[1].measureText(maxSpeed);
//		canvas.drawText(maxSpeed, mScreenW * info.getFloat("") - SpeedLen, (mScreenH * info.getFloat("")), paints[1]);
//		
//		//当前速度
//		String curSpeed = String.valueOf(info.getInt("curspeed"));
//		float cSpeedLen = paints[2].measureText(curSpeed);
//		canvas.drawText(curSpeed, mScreenW * info.getFloat("") - cSpeedLen, (mScreenH * info.getFloat("")), paints[2]);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		switch (info.getInt("type")) {
		case 1:
			drawDynamicView(mHolder.lockCanvas(), info);
			break;

		case 2:

			break;

		default:
			break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	
	private Bitmap loadOptiBitmap(int resId) {
		nowBitmapResID = resId;
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inInputShareable = true;
		options.inPurgeable = true;
		BitmapFactory.decodeResource(getResources(), resId, options);
		// 调用上面定义的方法计算inSampleSize值
//		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inSampleSize = 1;
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId, options);

		return bitmap;
	}
	
	public void updateSV(Bundle info){
		this.info = info;
	}

}
