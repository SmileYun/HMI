package com.cqupt.ui;


import java.util.Timer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cqupt.core.ioc.CCIoCView;
import com.cqupt.hmi.R;
import com.cqupt.persenter.Dispatcher;
import com.cqupt.ui.base.HMIActivity;

public class MainActivity extends HMIActivity implements Callback {
	protected String TAG = "MainActivity";
	
	private BluetoothAdapter bta;
	
	private static final int REQUEST_ENABLE_BT = 1;
	
	private static final int REQUEST_DISCOVERY = 2;
	
	private Dispatcher mDispatcher;
	
	@CCIoCView(id = R.id.img1)
	private ImageView display_1;
	
	@CCIoCView(id = R.id.img2)
	private ImageView display_2;
	
	private Handler mHandler;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainContentView(R.layout.threaten_layout);
		mHandler = new Handler(this);
	}
	
	// 蓝牙连接
	public void connectBlueth() {
		bta = BluetoothAdapter.getDefaultAdapter();
		if (bta != null) {
			if (!bta.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				MainActivity.this.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}else {
				Intent intent = new Intent(MainActivity.this, ActivityBluetoothDeviceList.class);
				MainActivity.this.startActivityForResult(intent, REQUEST_DISCOVERY);
			}
		} else {
			Toast.makeText(MainActivity.this, "不能打开蓝牙,程序即将关闭", Toast.LENGTH_SHORT).show();
			return;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
				Intent intent = new Intent(MainActivity.this, ActivityBluetoothDeviceList.class);
				MainActivity.this.startActivityForResult(intent, REQUEST_DISCOVERY);
			} else {
				Log.d("error", "BT 没开");
				Toast.makeText(MainActivity.this, "不能打开蓝牙,程序即将关闭", Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
		case REQUEST_DISCOVERY:
			if (resultCode == Activity.RESULT_OK) {
				if(data != null){
					connect((BluetoothDevice)data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
				}
				
				break;
			}
		}
		return;
	}

	
	public void connect(BluetoothDevice device) {
		mDispatcher = Dispatcher.getInstance();
		mDispatcher.connectBTandRecv(device);
	}	
	
	/**
	 * 实现SetFonts接口
	 */
	public void setFontsIs(Typeface mTypeface){
		mTypeface=Typeface.createFromAsset(getAssets(), "fonts/microyahei.ttf");
	}
	
	/**
	 * menu菜单   搜索蓝牙设备，建立连接
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "搜索设备");
		menu.add(0, 1, 1, "退出程序");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {	
			connectBlueth();
		} else if (item.getItemId() == 1) {
			exitApp();
		}		
		return true;
	}

	private void exitApp() {
		System.exit(0);
	}

	@Override
	public void show(Bundle b) {
		int RidImg_1, RidImg_2, time;
		RidImg_1 = b.getInt("");
		RidImg_2 = b.getInt(""); 
		time = b.getInt("");
		
		
		displayImg(RidImg_1, RidImg_2, VOICE_LEVEL, time);
	}
	
	/**
	 * 根据资源ID，设置显示图片&声音
	 * 
	 * @param RImgID
	 *            资源ID
	 * @param RVoiceId
	 *            声源ID
	 * @param time
	 *            1/频率
	 */
	private void displayImg(int RImgID_1, int RImgID_2, int RVoiceId, int time) {
//		if (recv_flag) {
//			recv_flag = false;
//
//			display1.setImageBitmap(BitmapFactory.decodeResource(getResources(), RImgID_1)); // 后面一张不动的图片
//			display1.setVisibility(View.GONE);
//			display2.setImageBitmap(BitmapFactory.decodeResource(getResources(), RImgID_2)); // 闪烁的图片
//			display2.setVisibility(View.GONE);
//			btState_layout.setVisibility(View.GONE);
//
//			if (mToneGenerator == null) {
//				// TODO 此处程序后面要改为150
//				mToneGenerator = new ToneGenerator(RVoiceId, 150);
//			}
//			if (mTimer != null) {
//				mTimer.cancel();
//				mTimer = null;
//			}
//
//			tHandler.obtainMessage().sendToTarget();
//			mTimer = new Timer();
//			mTimer.schedule(new myTask(time), time, time);
//		}
	}
	
	@Override
	public void stop() {
		
	}

	
	public static final int TYPE_BITMAP = 1;
	public static final int TYPE_SURFACE = 2;
	private static final int VOICE_LEVEL = 50;
	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case TYPE_BITMAP:
			
			break;
			
		case TYPE_SURFACE:

			break;
			
		default:
			break;
		}
		return true; //信息都在此处理
	}
}
