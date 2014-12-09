package com.cqupt.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class DynamicView extends SurfaceView {

	public DynamicView(Context context) {
		this(context, null);
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
	
	
}
