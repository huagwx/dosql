package com.stone.dosql.lexical.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.accessories.ST_TYPE_ARRAY;
import com.stone.dosql.accessories.ST_VT;
import com.stone.dosql.util.Token;

//初始化了三个 wordUpper,wordId,Type 还有word没有初始化
public class SqlTokens {
	//private List allTokens;
	public SqlTokens() {
		//this.allTokens=new ArrayList();
	}
	public List getKeyTokens(){
		List allTokens =new ArrayList();
		int leng = ST_TYPE_ARRAY.key.length;
		String word=null;
		for(int i=0;i<leng;i++){
			Token token=new Token();
			token.setType(ST_TYPE.ST_KEY);
			word=ST_TYPE_ARRAY.key[i].trim();
			
			//大写确定了
			token.setWordUpper(word);
			
			if(word.equals("SELECT")){
				token.setWordId(ST_VT.ST_SELECT_ID);
			}else if(word.equals("FROM")){
				token.setWordId(ST_VT.ST_FROM_ID);
			}else if(word.equals("WHERE")){
				token.setWordId(ST_VT.ST_WHERE_ID);
			}else if(word.equals("GROUP")){
				token.setWordId(ST_VT.ST_GROUP_ID);
			}else if(word.equals("BY")){
				token.setWordId(ST_VT.ST_BY_ID);
			}else if(word.equals("HAVING")){
				token.setWordId(ST_VT.ST_HAVING_ID);
			}else if(word.equals("ORDER")){
				token.setWordId(ST_VT.ST_ORDER_ID);
			}else if(word.equals("ALL")){
				token.setWordId(ST_VT.ST_ALL_ID);
			}else if(word.equals("DISTINCT")){
				token.setWordId(ST_VT.ST_DISTINCT_ID);
			}else if(word.equals("AS")){
				token.setWordId(ST_VT.ST_AS_ID);
			}else if(word.equals("ASC")){
				token.setWordId(ST_VT.ST_ASC_ID);
			}else if(word.equals("DESC")){
				token.setWordId(ST_VT.ST_DESC_ID);
			}else if(word.equals(".")){
				token.setWordId(ST_VT.ST_DOT_ID);
			}else if(word.equals("NOT")){
				token.setWordId(ST_VT.ST_NOT_ID);
			}else if(word.equals("NULL")){
				token.setWordId(ST_VT.ST_NULL_ID);
			}else if(word.equals("IN")){
				token.setWordId(ST_VT.ST_IN_ID);
			}else if(word.equals("LIKE")){
				token.setWordId(ST_VT.ST_LIKE_ID);
			}else if(word.equals("BETWEEN")){
				token.setWordId(ST_VT.ST_BETWEEN_ID);
			}else if(word.equals("AND")){
				token.setWordId(ST_VT.ST_AND_ID);
			}else if(word.equals("OR")){
				token.setWordId(ST_VT.ST_OR_ID);
			}else if(word.equals("ANY")){
				token.setWordId(ST_VT.ST_ANY_ID);
			}else if(word.equals("EXISTS")){
				token.setWordId(ST_VT.ST_EXISTS_ID);
			}else if(word.equals("ESCAPE")){
				token.setWordId(ST_VT.ST_ESCAPE_ID);
			}else if(word.equals("COUNT")){
				token.setWordId(ST_VT.ST_COUNT_ID);
			}else if(word.equals("SUM")){
				token.setWordId(ST_VT.ST_SUM_ID);
			}else if(word.equals("AVG")){
				token.setWordId(ST_VT.ST_AVG_ID);
			}else if(word.equals("MAX")){
				token.setWordId(ST_VT.ST_MAX_ID);
			}else if(word.equals("MIN")){
				token.setWordId(ST_VT.ST_MIN_ID);
			}else if(word.equals("IS")){
				token.setWordId(ST_VT.ST_IS_ID);
			}else if(word.equals("SOME")){
				token.setWordId(ST_VT.ST_SOME_ID);
			}
			allTokens.add(token);
			
		}
		return allTokens;
	}
	public List getOperTokens(){
		List allTokens =new ArrayList();
		int leng = ST_TYPE_ARRAY.oper.length;
		String word=null;
		for(int i=0;i<leng;i++){
			Token token=new Token();
			token.setType(ST_TYPE.ST_OPER);
			word=ST_TYPE_ARRAY.oper[i].trim();
			
			//大写确定了
			token.setWordUpper(word);
			
			if(word.equals("<>")){
				token.setWordId(ST_VT.ST_NOT_EQUATION_ID);
			}else if(word.equals("<")){
				token.setWordId(ST_VT.ST_LESS_ID);
			}else if(word.equals(">")){
				token.setWordId(ST_VT.ST_MORE_ID);
			}else if(word.equals("<=")){
				token.setWordId(ST_VT.ST_LESS_EQUATION_ID);
			}else if(word.equals(">=")){
				token.setWordId(ST_VT.ST_MORE_EQUATION_ID);
			}else if(word.equals("+")){
				token.setWordId(ST_VT.ST_ADD_ID);
			}else if(word.equals("-")){
				token.setWordId(ST_VT.ST_MINUS_ID);
			}else if(word.equals("*")){
				token.setWordId(ST_VT.ST_STAR_ID);
			}else if(word.equals("/")){
				token.setWordId(ST_VT.ST_DIV_ID);
			}else if(word.equals("(")){
				token.setWordId(ST_VT.ST_L_BRACKET_ID);
			}else if(word.equals(")")){
				token.setWordId(ST_VT.ST_R_BRACKET_ID);
			}else if(word.equals("=")){
				token.setWordId(ST_VT.ST_EQUATION_ID);
			}
			allTokens.add(token);
		}
		return allTokens;
	}
	public  List getBoundTokens(){
		List allTokens =new ArrayList();
		int leng = ST_TYPE_ARRAY.bound.length;
		String word=null;
		for(int i=0;i<leng;i++){
			Token token=new Token();
			token.setType(ST_TYPE.ST_BOUND);
			word=ST_TYPE_ARRAY.bound[i].trim();
			
			//大写确定了
			token.setWordUpper(word);
			
			if(word.equals(",")){
				token.setWordId(ST_VT.ST_COMMA_ID);
			}else if(word.equals("'")){
				token.setWordId(ST_VT.ST_SINGLE_QUOTES_ID);
			}else if(word.equals("\"")){
				token.setWordId(ST_VT.ST_DOUBLE_QUOTES_ID);
			}
			allTokens.add(token);
		}
		return allTokens;
	}
//	public  List getAllTokens(){
//		List list=new ArrayList();
//		for(int i=0;i<)
//		doKeyTokens();
//		doOperTokens();
//		doBoundTokens();
//		return this.allTokens;
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		SqlTokens sqlTokens =new SqlTokens();
//		List list= sqlTokens.getAllTokens();
//		for(int i=1;i<=list.size();i++){
//			System.out.println(i+":"+((Token)list.get(i-1)).getWordUpper());
//		}

	}

}
