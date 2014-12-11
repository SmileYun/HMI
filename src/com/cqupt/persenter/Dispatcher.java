package com.cqupt.persenter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.entity.CanMsgInfo.DISPLAYTYPE;
import com.cqupt.model.RecvThread;
import com.cqupt.model.RecvThread.SegmentMsgHandler;
import com.cqupt.model.threaten.BMHandler;
import com.cqupt.model.threaten.ByteArrayToSegment;
import com.cqupt.model.threaten.CanMsgCache.Segment;
import com.cqupt.model.threaten.SVHandler;
import com.cqupt.ui.base.IUI;

public class Dispatcher implements SegmentMsgHandler{
	private static String TAG = "Dispatcher";
	
	private static Dispatcher mInstanceDispatcher;
	
	private IUI mContentView;
	
	private AbHandler mBMAbHandler, mSVHandler;
	
	private RecvThread mRecvThread;
	
	private BluetoothSocket mBluetoothSocket;
	
//	private HandlerThread mHandler;
	
	private Timer mTQueryTBAT;
	
	
	
	private Dispatcher(){
		initResponseChain();
	}
	
	public static Dispatcher getInstance(){
		if (mInstanceDispatcher == null) {
			synchronized (Dispatcher.class) {
				if(mInstanceDispatcher == null)
					mInstanceDispatcher = new Dispatcher();
			}
		}
		return mInstanceDispatcher;
	}
	
	public void setIUI(IUI ui){
		this.mContentView = ui;
	}

	/**
	 * 
	 * 初始化责任链
	 */
	private void initResponseChain() {
		mBMAbHandler = new BMHandler();
		mSVHandler = new SVHandler();
		mBMAbHandler.setNext(mSVHandler);
	}
	
	public void connectBTandRecv(BluetoothDevice device){
		try {
			mBluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			mBluetoothSocket.connect();
			
			if (mBluetoothSocket != null) {
				//建立连接后，初始化线程并设置处理后启动
				mRecvThread = new RecvThread(mBluetoothSocket);
				mRecvThread.setHandler(new ByteArrayToSegment());
				mRecvThread.setHandler(this);
				mRecvThread.start();
				//设置定时器， 0.5s 轮询缓存表
				mTQueryTBAT = new Timer();
				mTQueryTBAT.scheduleAtFixedRate(new ObserveThreatenTableAtRate(), 1000, 500);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public interface ICommand{
		void excute();
	}
	
	public void postCommand(Runnable command){
		
	}*/
	
	public static abstract class AbHandler {
		private DISPLAYTYPE level = DISPLAYTYPE.UNKNOW;

		private AbHandler nextHandler;

		public AbHandler(){}
		
		public AbHandler(DISPLAYTYPE _level) {
			this.level = _level;
		}

		public void setNext(AbHandler h) {
			nextHandler = h;
		}

		public final Bundle handleMessage(CanMsgInfo info) {
			if (info.getType() == this.level) {
				return this.response(info);
			} else if (this.nextHandler != null) {
				return this.nextHandler.handleMessage(info);
			} else {
				Log.e(TAG, " CanMsgInfo can't be handled !");
				return null;
			}
		}

		public abstract Bundle response(CanMsgInfo info);
	}

	@Override
	public void handlerMsg(Segment sg) {
		CanMsgInfo info = null;
		//TODO Segment convert to CanMsgInfo
		Bundle _b = mBMAbHandler.handleMessage(info);
		mContentView.show(_b);
	}
	
	final class ObserveThreatenTableAtRate extends TimerTask{

		@Override
		public void run() {
			CanMsgInfo info = null;
			Segment _s = mRecvThread.queryHighLevelReturnSg();
			//TODO Segment convert to CanMsgInfo
			Bundle _b = mBMAbHandler.handleMessage(info);
			mContentView.show(_b);
		}
		
	}
}
