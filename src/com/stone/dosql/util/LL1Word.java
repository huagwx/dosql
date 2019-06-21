package com.stone.dosql.util;

import com.stone.dosql.accessories.ST_LL1_FLAG;

public class LL1Word {
	private String vnWord;
	private int flag;

	public LL1Word(String vnWord,int flag) {
		this.vnWord=vnWord;
		this.flag=flag;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getVnWord() {
		return vnWord;
	}

	public void setVnWord(String vnWord) {
		this.vnWord = vnWord;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}



}
