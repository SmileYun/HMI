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
import com.cqupt.ui.widget.DynamicView;

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
	
	@CCIoCView(id = R.id.surface)
	private DynamicView mDynamicView;
	
	private Handler mHandler;
	
	private ToneGenerator mToneGenerator;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainContentView(R.layout.threaten_layout);
		mHandler = new Handler(this);
	}
	
	// ��������
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
			Toast.makeText(MainActivity.this, "���ܴ�����,���򼴽��ر�", Toast.LENGTH_SHORT).show();
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
				Log.d("error", "BT û��");
				Toast.makeText(MainActivity.this, "���ܴ�����,���򼴽��ر�", Toast.LENGTH_SHORT).show();
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
	 * menu�˵�   ���������豸����������
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "�����豸");
		menu.add(0, 1, 1, "�˳�����");
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
		
		if (b.getInt("bitmap_or_surfaceview") == 1) {
			mDynamicView.setVisibility(View.GONE);
			display_1.setVisibility(View.VISIBLE);
			display_2.setVisibility(View.VISIBLE);
			displayImg(RidImg_1, RidImg_2, VOICE_LEVEL, time);
		}else {
			mDynamicView.setVisibility(View.VISIBLE);
			display_1.setVisibility(View.GONE);
			display_2.setVisibility(View.GONE);	
			mDynamicView.updateSV(b);
		}
	}
	
	/**
	 * ������ԴID��������ʾͼƬ&����
	 * 
	 * @param RImgID
	 *            ��ԴID
	 * @param RVoiceId
	 *            ��ԴID
	 * @param time
	 *            1/Ƶ��
	 */
	private void displayImg(int RImgID_1, int RImgID_2, int RVoiceId, int time) {
			display_1.setImageBitmap(BitmapFactory.decodeResource(getResources(), RImgID_1)); // ����һ�Ų�����ͼƬ
			display_1.setVisibility(View.GONE);
			display_2.setImageBitmap(BitmapFactory.decodeResource(getResources(), RImgID_2)); // ��˸��ͼƬ
			display_2.setVisibility(View.GONE);

			if (mToneGenerator == null) {
				mToneGenerator = new ToneGenerator(RVoiceId, VOICE_LEVEL);
			}

			if (mTimer != null) {
				mTimer.cancel();
				mTimer = null;
			}
			mHandler.obtainMessage().sendToTarget();
			mTimer = new Timer();
//			mTimer.schedule(new myTask(time), time, time);
	}
	
	private Timer mTimer;
	
	
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
		return true; //��Ϣ���ڴ˴���
	}
}
