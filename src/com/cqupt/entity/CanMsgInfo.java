package com.cqupt.entity;

import com.cqupt.model.threaten.CanMsgCache.Segment;



public class CanMsgInfo {
	private byte[] mCanID; // 2
	
	private byte[] mData; // 8
	
	public enum DISPLAYTYPE{BITMAP, SURFACEVIEW, UNKNOW}
	
	
	

	public CanMsgInfo(Segment info) {
		mCanID = info.getCanID();
		mData = info.getData();
	}

	public CanMsgInfo(byte[] id, byte[] data) {
		super();
		this.mCanID = id;
		this.mData = data;
	}

	public byte[] getId() {
		return mCanID;
	}

	public void setId(byte[] id) {
		this.mCanID = id;
	}

	public byte[] getData() {
		return mData;
	}

	public void setData(byte[] data) {
		this.mData = data;
	}
	
	/**
	 * 
	 * 返回处理类型   BMHandler.LEVEL SVHandler.LEVEL
	 * @return
	 * DISPLAYTYPE    返回类型
	 */
	public DISPLAYTYPE getType(){
		DISPLAYTYPE _type = DISPLAYTYPE.UNKNOW;
		switch (((mData[2] & 0xfc) >> 2)){
		case 0x00:
		case 0x01:
		case 0x02:
		case 0x03:
		case 0x05:
		case 0x06:
			_type=DISPLAYTYPE.BITMAP;
			break;
		case 0x04:
		case 0x07:
		case 0x08:
		case 0x09:
		case 0x0a:
		case 0x0b:
		case 0x0c:
			_type=DISPLAYTYPE.SURFACEVIEW;
			break;
		}
		
		return _type;
	}
	
	
}
