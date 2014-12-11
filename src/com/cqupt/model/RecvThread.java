package com.cqupt.model;



import java.util.HashMap;

import com.cqupt.model.threaten.CanMsgCache;
import com.cqupt.model.threaten.CanMsgCache.Segment;

import android.bluetooth.BluetoothSocket;

public class RecvThread {

	private BluetoothSocket mBluetoothSocket;

	private boolean isRecv = true;

	private char[] mCanMsgCharBuffer = new char[10];
	
	private CanMsgCache mCanMsgCache = CanMsgCache.getCacheInstance();
	
	private RawCanMsgHandler mHandler = null;
	
	private SegmentMsgHandler mSenter = null;
	

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
						//��
						mCanMsgCache.update(_s);
						//֪ͨ
						mSenter.handlerMsg(_s);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public HashMap<String, Object> queryHighLevel(){
		HashMap<String, Object> _m;
		_m = mCanMsgCache.queryHighLevel();
		return _m;
	}
	
	public Segment queryHighLevelReturnSg(){
		Segment _s;
		_s = mCanMsgCache.queryHighLevelReturnSg();
		return _s;
	}
	
	/**
	 * 
	 * @Description   ����ԭʼ��Ϣ�Դ��뻺��
	 */
	public interface RawCanMsgHandler{
		CanMsgCache.Segment handlerRawCanMsg(char[] mCanMsgCharBuffer);
	}
	
	/**
	 * 
	 * @Description   �ӵ���Ϣ��֪ͨ
	 */
	public interface SegmentMsgHandler{
		void handlerMsg(CanMsgCache.Segment sg);
	}

	
	public void setHandler(RawCanMsgHandler handler) {
		this.mHandler = handler;
	}
	
	public void setHandler(SegmentMsgHandler handler) {
		this.mSenter = handler;
	}
	
}
