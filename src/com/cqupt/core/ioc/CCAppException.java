package com.cqupt.core.ioc;

public class CCAppException extends Exception {

	/** 
	* @author CC 
	* @date 2014��12��4�� ����2:39:10 
	* @Fields serialVersionUID : The Constant serialVersionUID.
	*/  
	private static final long serialVersionUID = 458961853501479810L;
	private String msg;

	public CCAppException() {
		
	}

	public CCAppException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	/**
	 * ��������ȡ�쳣��Ϣ.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return msg;
	}
}
