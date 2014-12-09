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
	
	

	// ��������
	public void connectBlueth() {
		bta = BluetoothAdapter.getDefaultAdapter();
		if (bta != null) {
			Toast.makeText(MainActivity.this, "��������", Toast.LENGTH_SHORT).show();
			if (!bta.isEnabled()) {
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivity(enableBtIntent);
			}
			Intent intent = new Intent(MainActivity.this, ActivityBluetoothDeviceList.class);
			MainActivity.this.startActivityForResult(intent, REQUEST_DISCOVERY);
		} else {
			Toast.makeText(MainActivity.this, "���ܴ�����,���򼴽��ر�", Toast.LENGTH_SHORT).show();
			return;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {

				Toast.makeText(MainActivity.this, "�ɹ�������", Toast.LENGTH_SHORT)
						.show();
			} else {

				Log.d("error", "BT û��");
				Toast.makeText(MainActivity.this, "���ܴ�����,���򼴽��ر�",
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
			// ����
			socket.connect();
//			btConnState.setText("������");
			/**
			 * ���Ӻ��������̣߳���������
			 */
//			new Thread(new RecvThread(MainActivity.this,socket)).start();

			Log.d("sock", "nx");
			if (socket != null) {
				Toast.makeText(MainActivity.this, "�ɹ����ӣ�", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (IOException e) {
			Toast.makeText(MainActivity.this, "����ʧ�ܣ�", Toast.LENGTH_SHORT)
					.show();
		}
	}	
	
	/**
	 * ʵ��SetFonts�ӿ�
	 */
	public void setFontsIs(Typeface mTypeface){
		mTypeface=Typeface.createFromAsset(getAssets(), "fonts/microyahei.ttf");
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
			quit_app();
		}		
		return true;

	}

	// ����˵����˳������˳�����
	private void quit_app() {
		MainActivity.this.finish();
		System.exit(0);
	}
}
