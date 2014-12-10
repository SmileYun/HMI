package com.cqupt.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class DynamicView extends SurfaceView implements Callback{
	private Bundle info;
	
	private SurfaceHolder mHolder;

	public DynamicView(Context context, Bundle b) {
		this(context, null);
		info = b;
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	public DynamicView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DynamicView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	
	private void drawDynamicView(Canvas canvas, String value, Point poistion){
		
	}
	
}
