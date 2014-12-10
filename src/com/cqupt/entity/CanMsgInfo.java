package com.cqupt.entity;



public class CanMsgInfo {
	private byte[] id; // 2
	private byte[] data; // 8

	public CanMsgInfo() {
	}

	public CanMsgInfo(byte[] id, byte[] data) {
		super();
		this.id = id;
		this.data = data;
	}

	public byte[] getId() {
		return id;
	}

	public void setId(byte[] id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	/**
	 * 
	 * 返回处理类型   BMHandler.LEVEL SVHandler.LEVEL
	 * @return
	 * int    返回类型
	 */
	public int getType(){
		int _return = 0;
		
		return _return;
	}
}
