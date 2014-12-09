package com.cqupt.persenter.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AdapterBase extends BaseAdapter{

	protected ArrayList<String> mList;
	
	protected Context mContext;

	public AdapterBase(Context context, ArrayList<String> mList) {
		super();
		this.mList = mList;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
