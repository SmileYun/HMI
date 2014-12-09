package com.cqupt.ui;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cqupt.model.RecvThread;
import com.cqupt.ui.base.HMIActivity;

public class MainActivity extends HMIActivity {
	BluetoothAdapter bta;
	BluetoothDevice btd;
	OutputStream MsgWriter;
	BluetoothSocket socket;
	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_DISCOVERY = 2;
	
	

	// 蓝牙连接
	public void connectBlueth() {
		bta = BluetoothAdapter.getDefaultAdapter();
		if (bta != null) {
			Toast.makeText(MainActivity.this, "蓝牙可用", Toast.LENGTH_SHORT).show();
			if (!bta.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(enableBtIntent);
			}
			Intent intent = new Intent(MainActivity.this, ActivityBluetoothDeviceList.class);
			MainActivity.this.startActivityForResult(intent, REQUEST_DISCOVERY);
		} else {
			Toast.makeText(MainActivity.this, "不能打开蓝牙,程序即将关闭", Toast.LENGTH_SHORT).show();
			return;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {

				Toast.makeText(MainActivity.this, "成功打开蓝牙", Toast.LENGTH_SHORT)
						.show();
			} else {

				Log.d("error", "BT 没开");
				Toast.makeText(MainActivity.this, "不能打开蓝牙,程序即将关闭",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		case REQUEST_DISCOVERY:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				btd = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				Toast.makeText(MainActivity.this, btd.getName(),
						Toast.LENGTH_SHORT).show();
				connect(btd);
				break;
			}
		}
		return;
	}

	
	static int x = 0;

	public void connect(BluetoothDevice device) {

		try {

			socket = device.createRfcommSocketToServiceRecord(UUID
					.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			// 连接
			socket.connect();
//			btConnState.setText("已连接");
			/**
			 * 连接后开启接收线程，接收数据
			 */
//			new Thread(new RecvThread(MainActivity.this,socket)).start();

			Log.d("sock", "nx");
			if (socket != null) {
				Toast.makeText(MainActivity.this, "成功连接！", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (IOException e) {
			Toast.makeText(MainActivity.this, "连接失败！", Toast.LENGTH_SHORT)
					.show();
		}
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
			quit_app();
		}		
		return true;

	}

	// 点击菜单中退出程序，退出程序
	private void quit_app() {
		MainActivity.this.finish();
		System.exit(0);
	}
}
