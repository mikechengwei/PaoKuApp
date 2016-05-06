package com.example.chengwei.notebook.bean;


import com.example.chengwei.notebook.R;
import com.example.chengwei.notebook.fragment.AllFragment;
import com.example.chengwei.notebook.fragment.DianpuFragment;
import com.example.chengwei.notebook.fragment.PostFragment;

public enum MainTab {

	NEWS(0, R.string.renwufragment, R.drawable.icon_renwu,
			AllFragment.class),

	TWEET(1, R.string.dianpufragment, R.drawable.icon_dianpu,
			DianpuFragment.class);



	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
