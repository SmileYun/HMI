package com.cqupt.model.threaten;

import android.R.integer;
import android.media.AudioManager;
import android.os.Bundle;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.entity.CanMsgInfo.DISPLAYTYPE;
import com.cqupt.hmi.R;
import com.cqupt.persenter.Dispatcher;
import com.cqupt.persenter.Dispatcher.AbHandler;

public class SVHandler extends Dispatcher.AbHandler {

private static final DISPLAYTYPE LEVEL = DISPLAYTYPE.SURFACEVIEW;
	
	public SVHandler() {
		super(LEVEL);
	}
	private static final int LEVEL1TIME=500;
	private static final int LEVEL2TIME=200;
	private static final int AUDIO=AudioManager.STREAM_ALARM;
	private static final int[] RID1=new int[]{
		R.drawable.cb,
		R.drawable.uvr,
		R.drawable.dsr,
		R.drawable.sg_green,
		R.drawable.sg_yellow,
		R.drawable.sg_red,
		R.drawable.ojr,
		R.drawable.ahs,
		R.drawable.cpp_1,
		R.drawable.cpp_2
	};
	@Override
	public Bundle response(CanMsgInfo cinfo) {
		Bundle bd=new Bundle();
		
		byte[] info = cinfo.getData();
		//报警级别
		int alaLevel=info[0] % 4;
		//信号灯颜色
		int color=(info[4] >> 4) & 0x03;
		// 最大引导速度
		int maxSpeed = (info[1] & 0x7f);
		// 最小引导速度
		int	minSpeed = (info[2] & 0x7f);
		// 当前行车速度
		int currentSpeed = (info[5] & 0xff);
		// 剩余时间
		int remainTime = (info[6] & 0x7f);
		// 危险点距离本车距离
		int dgrDis = (((info[4] & 0x03) << 8) + (info[5] & 0xff));
								
		switch (((info[0] & 0xfc) >> 2)){
		case 0x04: //cb
			if (alaLevel == 0x01) {
				
				bundlePutInt(bd,RID1[0],AUDIO,LEVEL1TIME);
				
			} 
			break;
		case 0x07:// uvr
			if (alaLevel == 0x01) {
				
				bundlePutInt(bd,RID1[1],dgrDis,0.63f,0.655f,AUDIO,LEVEL1TIME);
				
			} 
			break;
		case 0x08:// dsr
			if (alaLevel == 0x01) {
				
				bundlePutInt(bd,RID1[2],AUDIO,LEVEL1TIME);
				
			} 
			break;
		case 0x09:// sg
			if (alaLevel == 0x01) {
				if (color==0x00) {
					//green
					bundlePutInt(bd,RID1[3],maxSpeed,0.512f,0.595f,
							currentSpeed,0.53f,0.692f,
							remainTime,0.26f,0.439f, AUDIO,LEVEL1TIME);
					
				}else if (color==0x01) {
					//yellow
					bundlePutInt(bd,RID1[4],maxSpeed,0.512f,0.595f,
							currentSpeed,0.53f,0.692f,
							remainTime,0.538f,0.439f, AUDIO,LEVEL1TIME);
					
				}else if (color==0x02) {
					//red
					bundlePutInt(bd,RID1[5],maxSpeed,0.512f,0.595f,
							currentSpeed,0.53f,0.692f,
							remainTime,0.82f,0.439f, AUDIO,LEVEL1TIME);
					
				}	
			} 
			break;
		case 0x0a:// ojr
			if (alaLevel == 0x01) {
				
				bundlePutInt(bd,RID1[6],dgrDis,0.686f,0.675f,AUDIO,LEVEL1TIME);
				
			} 
			break;

		case 0x0b:// ahs
			if (alaLevel == 0x01) {

				bundlePutInt(bd,RID1[7],AUDIO,LEVEL1TIME);
				
			}
			break;
		case 0x0c:// cpp
			if (alaLevel == 0x01) {

				bundlePutInt(bd,RID1[8],dgrDis,0.585f,0.732f,AUDIO,LEVEL1TIME);
				
			}else if(alaLevel == 0x02){
				
				bundlePutInt(bd,RID1[8],dgrDis,0.585f,0.732f,AUDIO,LEVEL2TIME);
								
			}
			break;
		}
		
		
		return null;
	}
	//方法重载：
	//方法一：CB、DSR、AHS使用此方法
	private Bundle bundlePutInt(Bundle bundle,int bmapID1,int audio,int time) {
		bundle.putInt("BMAP1", bmapID1);
		bundle.putInt("AUDIO", audio);
		bundle.putInt("TIME", time);
		return bundle;
	}
	//方法二：UVR、OJR、CPP使用此方法
	private Bundle bundlePutInt(Bundle bundle,int bmapID1,int distance,float xValue,float yValue,int audio,int time) {
		bundle.putInt("BMAP1", bmapID1);
		bundle.putFloat("XVALUE", xValue);
		bundle.putFloat("YVALUE", yValue);
		bundle.putInt("AUDIO", audio);
		bundle.putInt("TIME", time);
		return bundle;
	}
	//方法三：SG使用此方法
	private Bundle bundlePutInt(Bundle bundle,int bmapID1,int maxSpeed,float maxSpeedX,float maxSpeedY,
			int currentSpeed,float currentSpeedX,float currentSpeedY,
			int remainTime,float TimeX,float TimeY,int audio,int time){
		bundle.putInt("BMAP1", bmapID1);
		bundle.putInt("MAX", maxSpeed);
		bundle.putFloat("MAXX", maxSpeedX);
		bundle.putFloat("MAXY", maxSpeedY);
/*		bundle.putInt("MIN", minSpeed);
		bundle.putFloat("MINX", minSpeedX);
		bundle.putFloat("MINY", minSpeedY);*/
		bundle.putInt("CUR", currentSpeed);
		bundle.putFloat("CURX", currentSpeedX);
		bundle.putFloat("CURY", currentSpeedY);
		bundle.putInt("REM", remainTime);
		bundle.putFloat("REMX", TimeX);
		bundle.putFloat("REMY", TimeY);
		bundle.putInt("AUDIO", audio);
		bundle.putInt("TIME", time);
		return bundle;
	}
}
