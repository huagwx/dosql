package com.stone.dosql.util;

public class Token {
	private int type;
	private int wordId;
	private String word;
	private String wordUpper;
	
	public String getWordUpper() {
		return wordUpper;
	}

	public void setWordUpper(String wordUpper) {
		this.wordUpper = wordUpper;
	}

	public Token() {
		
	}
	public String getUPPER(){
		return this.word.toUpperCase();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getWordId() {
		return wordId;
	}

	public void setWordId(int wordId) {
		this.wordId = wordId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
