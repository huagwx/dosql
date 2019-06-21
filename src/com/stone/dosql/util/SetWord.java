package com.stone.dosql.util;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_TYPE;

public class SetWord {
	private String name;
	private List set;
	
	//setType的Follow或first 只是用在 求follow集合中，在求first是没有起作用的
	private int setType;  //默认为first类型
	public int getSetType() {
		return setType;
	}

	public void setSetType(int setType) {
		this.setType = setType;
	}

	public SetWord() {
		set=new ArrayList();
		this.setType=ST_TYPE.SetWord_TYPE_FIRST;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getSet() {
		return set;
	}

	public void setSet(List set) {
		this.set = set;
	}

}
