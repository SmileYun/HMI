package com.cqupt.model;


import com.cqupt.model.CanMsgCache.Segment.LEVEL;

import android.bluetooth.BluetoothSocket;

public class RecvThread {

	private BluetoothSocket mBluetoothSocket;

	private boolean isRecv = true;

	private char[] mCanMsgCharBuffer = new char[10];
	
	private CanMsgCache mCanMsgCache = CanMsgCache.getCacheInstance();
	
	private RawCanMsgHandler mHandler = null;
	

	public RecvThread(BluetoothSocket mBluetoothSocket) {
		this.mBluetoothSocket = mBluetoothSocket;
	}

	public void start() {
		new Thread(r).start();
	}

	private Runnable r = new Runnable() {

		@Override
		public void run() {
			int looper_recv = 10;
		    int _readIntResult = -1;
			
			try {
				while (isRecv) {
					while (looper_recv > 0 && ((_readIntResult = mBluetoothSocket.getInputStream().read()) != -1)) {

						mCanMsgCharBuffer[10 - looper_recv] = (char) (_readIntResult & 0xFF);

						if (mCanMsgCharBuffer[0] != 0x41 && mCanMsgCharBuffer[0] != 0x31 && mCanMsgCharBuffer[0] != 0x21) {
							looper_recv = 11;
						}

						if (looper_recv == 9 && mCanMsgCharBuffer[1] != 0x05) {
							looper_recv = 11;
						}
						--looper_recv;
					}
					looper_recv = 10;
					if(mHandler != null){
						CanMsgCache.Segment _s = mHandler.handlerRawCanMsg(mCanMsgCharBuffer);
						mCanMsgCache.update(_s);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public interface RawCanMsgHandler{
		CanMsgCache.Segment handlerRawCanMsg(char[] mCanMsgCharBuffer);
	}

	public void setHandler(RawCanMsgHandler handler) {
		this.mHandler = handler;
	}
	
}
