package com.cqupt.model;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.R.integer;

import com.cqupt.core.ioc.CCAppException;

public class CanMsgCache {
	private ConcurrentHashMap<Integer, HashMap<String, Object>> mCache;

	private static final ReadWriteLock lock = new ReentrantReadWriteLock();

	static CanMsgCache mInstanceCache;
	
	private int maxID;

	private CanMsgCache(int capacity) {
		maxID = capacity;

		mCache = new ConcurrentHashMap<Integer, HashMap<String, Object>>();
		for (int i = 1; i <= capacity; i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("canID", new byte[2]);
			hm.put("data", new byte[8]);
			hm.put("flag", 0);
			mCache.put(i, hm);
		}
	}
	
	/**
	 * 
	 * ����Ĭ�ϴ�СΪ 4
	 * @return
	 * CanMsgCache  ʵ��
	 */
	public static CanMsgCache getCacheInstance() {
		if (mInstanceCache == null) {
			synchronized (CanMsgCache.class) {
				if (mInstanceCache == null) {
					mInstanceCache = new CanMsgCache(4);
				}
			}
		}
		return mInstanceCache;
	}
	
	/**
	 * 
	 * ָ�������С 
	 * @param capacity
	 * @return
	 * CanMsgCache ʵ��
	 */
	public static CanMsgCache getCacheInstance(int capacity) {
		if (mInstanceCache == null) {
			synchronized (CanMsgCache.class) {
				if (mInstanceCache == null) {
					mInstanceCache = new CanMsgCache(capacity);
				}
			}
		}
		return mInstanceCache;
	}
	
	/**
	 * 
	 * ����CanMsg ���ݺͱ�־λ���»����
	 * 
	 * @param level
	 *         		Ҫ���µļ���  <p/>
	 *            {@code LEVEL.HIGH, LEVEL.MIDDLE, LEVEL.LOW, LEVEL.SAFE} <br>by</br> {@code getLevel()}
	 * @param canID
	 *            ֡ID
	 * @param data
	 *            ����
	 * @param flag
	 *            ��־λ
	 * @param void ��������
	 */
	public void update(int level, int canID, byte[] data, int flag) {
		if (level < 1 || level > maxID) {
			try {
				throw new CCAppException("id out of bound ! pleas check!");

			} catch (CCAppException e) {
				e.printStackTrace();
				return;
			}
		}
		// synchronized (mCache) {
		lock.writeLock().lock();
		try {
			HashMap<String, Object> m = mCache.get(level);
			m.put("canID", canID);
			m.put("data", data);
			m.put("flag", flag);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	public void update(Segment s) {
		if (s.getLevel() < 0 || s.getLevel() > maxID) {
			try {
				throw new CCAppException("id out of bound ! pleas check!");

			} catch (CCAppException e) {
				e.printStackTrace();
				return;
			}
		}
		// synchronized (mCache) {
		lock.writeLock().lock();
		try {
			HashMap<String, Object> m = mCache.get(s.getLevel());
			m.put("canID", s.getCanID());
			m.put("data", s.getData());
			m.put("flag", s.getFlag());
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * 
	 * ����id ��ѯ ֵ
	 * 
	 * @param id
	 * @return HashMap<String,Object> ["data", "flag"]
	 */
	public HashMap<String, Object> query(int id) {
		lock.readLock().lock();
		try {
			if (mCache.contains(id)) {
				return mCache.get(id);
			}
			return null;
		} finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * 
	 * �������ȼ�����߳���
	 * 
	 * @return HashMap<String,Object>, ���������, �򷵻�null
	 */
	public HashMap<String, Object> queryHighLevel() {
		ConcurrentHashMap<Integer, HashMap<String, Object>> _Cache;
		lock.readLock().lock();
		try {
			_Cache = new ConcurrentHashMap<Integer, HashMap<String, Object>>(mCache);
		} finally {
			lock.readLock().unlock();
		}
		for (int i = 1; i <= maxID; i++) {
			HashMap<String, Object> m = _Cache.get(i);

			if ((Integer) m.get("flag") == 1) {
				return m;
			}
		}
		return null;
	}

	public void cleanAll() {
		lock.writeLock().lock();
		try {
			mCache.clear();
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void cleanByID(int id) {
		lock.writeLock().lock();
		try {
			mCache.remove(id);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	public static final class Segment {
		
		private LEVEL level = LEVEL.SAFE;
		
		private byte[] canID = null;
		
		private byte[] data = null;
	
		private int flag = 0;
		
		public static enum LEVEL{
			HIGH, MIDDLE, LOW, SAFE;
			public int getLevel(){
				return this.ordinal()+1;
			}
		}

		public byte[] getCanID() {
			return canID;
		}

		public void setCanID(byte[] canID) {
			this.canID = canID;
		}

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		public int getLevel() {
			return level.ordinal()+1;
		}

		public void setLevel(LEVEL level) {
			this.level = level;
		}
	}

}
