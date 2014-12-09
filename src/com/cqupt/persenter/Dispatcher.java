package com.cqupt.persenter;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.model.RecvThread;
import com.cqupt.model.threaten.BMHandler;
import com.cqupt.model.threaten.SVHandler;
import com.cqupt.ui.base.IUI;

public class Dispatcher {
	private static String TAG = "Dispatcher";
	
	private IUI mContentView;
	
	private AbHandler mBMAbHandler, mSVHandler;
	
	private RecvThread mRecvThread;
	
	private BluetoothSocket mBluetoothSocket;
	
	
	public Dispatcher(IUI contentView){
		mContentView = contentView;
		mBMAbHandler = new BMHandler();
		mSVHandler = new SVHandler();
		mBMAbHandler.setNext(mSVHandler);
	}
	
	public void connectBTandRecv(BluetoothDevice device){
		try {
			mBluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			// 连接
			mBluetoothSocket.connect();
			
			new RecvThread(mBluetoothSocket).start();

			if (mBluetoothSocket != null) {
//				Toast.makeText(MainActivity.this, "成功连接！", Toast.LENGTH_SHORT).show();
			}

		} catch (IOException e) {
//			Toast.makeText(MainActivity.this, "连接失败！", Toast.LENGTH_SHORT).show();
		}
	}
	
	public interface ICommand{
		void excute();
	}
	
	public static abstract class AbHandler {
		private int level = 0;

		private AbHandler handler;

		public AbHandler(int _level) {
			this.level = _level;
		}

		public void setNext(AbHandler h) {
			handler = h;
		}

		public final void handleMessage(CanMsgInfo info) {
			if (info.getType() == this.level) {
				this.response(info);
			} else if (this.handler != null) {
				this.handler.handleMessage(info);
			} else {
				Log.e(TAG, " CanMsgInfo can't be handled !");
			}
		}

		public abstract void response(CanMsgInfo info);
	}
}
