package com.stone.dosql.lexical.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.accessories.ST_VT;
import com.stone.dosql.util.Token;

public class TokenAnalyze {
	private String input; // 输入的select语句
	private List tokens; // 分析后的token的list
	// private List allTokens; // 定义好的tokens
	private List keyTokens;
	private List operTokens;
	private List boundTokens;
	private int cWordSize = 30; //
	private char[] cWord;
	private int inputLeng;
	private int inputCharPosition;
	private int cWordPosition;
	private char cSelectChar;
	private boolean isDotAterIdent;
	
	private List errList=new ArrayList();
	public List getErrList(){
		return this.errList;
	}

	public TokenAnalyze(String inputSelect) {
		this.isDotAterIdent=false;
		this.input = inputSelect.trim()+" ";  //添加空格作为结尾
		this.tokens = new ArrayList();
		this.initAttribute();
		this.doInputSelect();
	}

	private void initAttribute() {
		SqlTokens sqlTokens = new SqlTokens();
		this.keyTokens = sqlTokens.getKeyTokens();
		this.operTokens = sqlTokens.getOperTokens();
		this.boundTokens = sqlTokens.getBoundTokens();
	}

	// 返回分析后的token列表
	public List getTokens() {
		
		return tokens;
	}

	// 关键字 key
	private Token getKeyToken(String word) {
		boolean flag = false;
		Token token = new Token();
		Iterator iter = this.keyTokens.iterator();
		while (iter.hasNext()) {
			Token tokenTemp = (Token) iter.next();
			if (tokenTemp.getWordUpper().equals(word.toUpperCase())) {
				token = tokenTemp;
				// word得到初始化
				token.setWord(word);
				flag = true;
				break;
			}
		}
		if (flag) {
			return token;
		} else {
			return null;
		}
	}

	// 操作符 oper
	private Token getOperToken(String word) {
		boolean flag = false;
		Token token = new Token();
		
		Token tokenLast=(Token)this.tokens.get(this.tokens.size()-1);
		if((tokenLast.getWord().equals(".")||tokenLast.getWord().equals(","))&&!word.equals("(")){
			
			if(this.isDotAterIdent){
				this.isDotAterIdent=false;
			}
			token.setWord(word);
			token.setType(ST_TYPE.ST_IDENT);
			token.setWordId(ST_VT.ST_STAR_ID);
			token.setWordUpper(word.toUpperCase());
			flag=true;
		}else{
			Iterator iter = this.operTokens.iterator();
			while (iter.hasNext()) {
				Token tokenTemp = (Token) iter.next();
				if (tokenTemp.getWordUpper().equals(word.toUpperCase())) {
					token = tokenTemp;
					// word得到初始化
					token.setWord(word);
					flag = true;
					break;
				}
			}
		}

		
		if (flag) {
			return token;
		} else {
			return null;
		}
	}

	// 分界符 bound
	private Token getBoundToken(String word) {
		boolean flag = false;
		Token token = new Token();
		Iterator iter = this.boundTokens.iterator();
		while (iter.hasNext()) {
			Token tokenTemp = (Token) iter.next();
			if (tokenTemp.getWordUpper().equals(word.toUpperCase())) {
				token = tokenTemp;
				// word得到初始化
				token.setWord(word);
				flag = true;
				break;
			}
		}
		if (flag) {
			return token;
		} else {
			return null;
		}
	}

	// 标识符 ident
	private Token getIdentToken(String word) {
		boolean isIdent = true;
		boolean isStartWord = true;
		Token token = new Token();
		int word_leng = word.length();
		if (word_leng >= 0) {
			char cFirst = word.charAt(0);
			if (cFirst >= 'a' && cFirst <= 'z' || cFirst >= 'A'
					&& cFirst <= 'Z' || cFirst == '_') {
				for (int i = 1; i < word_leng; i++) {
					char cTemp = word.charAt(i);
					if (cTemp >= 'a' && cTemp <= 'z' || cTemp >= 'A'
							&& cTemp <= 'Z' || cTemp == '_' || cTemp >= '0'
							&& cTemp <= '9') {
					} else {
						isStartWord = false;
						break;
					}
				}
			} else {
				isStartWord = false;
			}

		} else {
			isIdent = false;
		}

		if (isIdent && isStartWord) {
			token.setType(ST_TYPE.ST_IDENT);
			token.setWord(word);
			token.setWordUpper(word.toUpperCase());
			token.setWordId(ST_VT.ST_IDENT_ID);
			return token;
		} else {
			return null;
		}
	}
	
	//数字常量
	private Token getDigitalConst(String word){
		boolean isDigital=true;
		int dotNum=0;
		Token token = new Token();
		int word_leng=word.length();
		if(word_leng>=0){
			for(int i=0;i<word_leng;i++){
				char c=word.charAt(i);
				if(c>='0'&&c<='9' || c=='.'){
					if(c=='.'){
						dotNum++;
					}
				}else{
					isDigital=false;
					break;
				}
			}
			//判断两端 
			if(word.charAt(0)=='.' || word.charAt(word_leng-1)=='.'){
				isDigital=false;
			}
			
		}else{
			isDigital=false;
		}
		//判断是数字常量
		if(isDigital && (dotNum<=1)){
			token.setType(ST_TYPE.ST_DIGITAL_CONST);
			token.setWordId(ST_VT.ST_DIGITAL_CONST_ID);
			token.setWord(word);
			token.setWordUpper(word.toUpperCase());
			return token;
		}else{
			return null;
		}
	}
	
	//字符串常量
	private Token getStringConst(String word){
		Token token =new Token();
		token.setType(ST_TYPE.ST_STRING_CONST);
		token.setWord(word);
		token.setWordId(ST_VT.ST_STRING_CONST_ID);
		token.setWordUpper(word.toUpperCase());
		return token;
	}
	
	//处理 空格， 逗号，oper ,前面得内容;
	private void doBeforeWord(){

		Token tokenTemp;
		// 前面有字符
		if(this.cWordPosition>0){
			boolean isExist=false;
			String word = new String(cWord, 0, cWordPosition);
			//dot .的后面是标识符
			if(this.isDotAterIdent){
				this.isDotAterIdent=false;
				if(this.isIdent(word)){
					tokenTemp = this.getIdentToken(word);
					if(tokenTemp !=null){
						isExist =true;
						this.tokens.add(tokenTemp);
						this.cWord = new char[this.cWordSize];
						this.cWordPosition=0;
					}
				}else{
					this.errList.add(word+"名称不符合规则");
					//ST_APP.ERR_List.add(word + ST_ERROR.ST_NAME);
				}
				
			}else{
					
				
				// 关键字
				if(!isExist){
					tokenTemp=this.getKeyToken(word);
					if(tokenTemp != null){
						isExist=true;
						this.tokens.add(tokenTemp);
						this.cWord = new char[this.cWordSize];
						this.cWordPosition=0;
					}
				}
				// 标识符
				if(! isExist){
					tokenTemp = this.getIdentToken(word);
					if(tokenTemp !=null){
						isExist =true;
						this.tokens.add(tokenTemp);
						this.cWord = new char[this.cWordSize];
						this.cWordPosition=0;
					}
				}
				
				//数字常量
				if( ! isExist){
					tokenTemp = this.getDigitalConst(word);
					if(tokenTemp != null){
						isExist = true;
						this.tokens.add(tokenTemp);
						this.cWord = new char[this.cWordSize];
						this.cWordPosition=0;
					}
				}
				//有误  得放到最后
				if( ! isExist){
					this.errList.add(word+"名称不符合规则");
					//ST_APP.ERR_List.add(word + ST_ERROR.ST_NAME);
				}
			}
					
		}
		this.inputCharPosition++;
	}

	// 处理 '\n','\t'
	private void doNewLineEnter() {
//		this.inputCharPosition++;
//		char c=this.input.charAt(this.inputCharPosition);
//		if(c=='r'||c=='t'||c=='n'){
//			this.inputCharPosition++;
//		}
		this.inputCharPosition++;
	}

	// 处理 ' ' 空格 前面的内容为即 cWord字符数组的内容为: 空,关键字，标识符，数字常量，错误
	private void doSpace(){		
		this.doBeforeWord();

	}
	
	//处理单双引号 
	private void doQuotes(char cQuote){
		//词法有错误
		if(this.cWordPosition>0){
			this.inputCharPosition++;
			this.errList.add(new String(this.cWord,0,this.cWordPosition)+cQuote+"名称不符合规则");
			//ST_APP.ERR_List.add(new String(this.cWord,0,this.cWordPosition)+cQuote+ST_ERROR.ST_NAME);
		}else{
				
			
			Token tokenTemp =new Token();
			String word=new String(this.cSelectChar+"");
			tokenTemp = this.getBoundToken(word);
			this.tokens.add(tokenTemp);
			
			char lastChar=cQuote;  //直到碰到这个lastChar 为止
			char helpChar='\\';   // \ 为借助符号 后便可以加入 ' 或 "
			//获取下一个input字符串得位置 后面得内容为一个字符常量 要考虑 ' 在里面
			this.inputCharPosition++;
			
			boolean isFind=false;
			while(this.inputCharPosition<this.inputLeng){
				char c=this.input.charAt(this.inputCharPosition);

	//			System.out.println("QQPosition:"+this.inputCharPosition+" Char:"+c);
				
				if(c != lastChar){  // ！= ' 不等于单引号
					if(c == helpChar){  // c为\ 判断是否为help字符
						if(this.inputCharPosition+1<this.inputLeng){
							char cNext=this.input.charAt(this.inputCharPosition+1);
							if(cNext == lastChar){
								this.inputCharPosition++;
								c=this.input.charAt(this.inputCharPosition);
							}
						}
					}
					this.cWord[this.cWordPosition]=c;
					this.cWordPosition++;
				}else{  //等到得字符有 lastChar 
					isFind=true;   //找到 下一个 ' 单引号
					if(this.cWordPosition>0){
						String wordTemp=new String(this.cWord,0,this.cWordPosition);
						Token tokenStrConst=this.getStringConst(wordTemp);
						this.tokens.add(tokenStrConst);
						this.cWord=new char[this.cWordSize];
						this.cWordPosition=0;
						
					}
					//保存 ' 单引号得token 引用函数开头得定义
					this.tokens.add(tokenTemp);
				}
				this.inputCharPosition++;
				//如果找到对应得 单双引号 就退出
				if(isFind){
					break;
				}
			}
			//找不到下个 ' 时  说明有错误
			if(!isFind){
				this.errList.add(cQuote+"没有一对");
				//ST_APP.ERR_List.add(cQuote+ST_ERROR.ST_DOUBLE);
			}
		}
	}
	
	//处理单引号
	private void doSingleQuotes(){
		//保存 ' 单引号得token
		char cQuote='\'';
		this.doQuotes(cQuote);	
	}
	//处理双引号
	private void doDoubleQuotes(){
		char cQuote='"';
		this.doQuotes(cQuote);
	}
	//处理 点.   为一个table.column 或小数
	private void doDot(){
		Token tokenTemp =new Token();
		
		if(this.cWordPosition>0){
			String word=new String(this.cWord,0,this.cWordPosition);
			if(this.isInt(word)){ //前面是整数
				this.cWord[this.cWordPosition]=this.cSelectChar;
				this.cWordPosition++;
				//this.inputCharPosition++;  //不做处理
			}else if(this.isIdent(word)){  //前面是标识符
				//添加前面得标识符
				
				tokenTemp.setType(ST_TYPE.ST_IDENT);
				tokenTemp.setWord(word);
				tokenTemp.setWordId(ST_VT.ST_IDENT_ID);
				tokenTemp.setWordUpper(word.toUpperCase());
				this.tokens.add(tokenTemp);
				
				this.cWord=new char[this.cWordSize];
				this.cWordPosition=0;
				
				//添加 点 .
				Token dotToken=this.getKeyToken(this.cSelectChar+"");
				this.tokens.add(dotToken);
				
				this.isDotAterIdent=true; //................点后面是表示符号 标识

			}
			
		}else{
			this.errList.add(new String(this.input.toCharArray(),this.inputCharPosition-2,4)+" 附近有错误");
			//ST_APP.ERR_List.add(new String(this.input.toCharArray(),this.inputCharPosition-2,4)+" 附近有错误");
		}
		this.inputCharPosition++;
	 }
	
	
	//********************
	   //在处理 函数 doBeforeWord()是已经处理了 this.inputCharPosition++
	//**********
	
	//处理  逗号  
	private void doComma(){
		this.doBeforeWord();
		Token token=this.getBoundToken(this.cSelectChar+"");
		this.tokens.add(token);
	}
	//处理 ( )左右括号
    private void doBracket(){
    	this.doBeforeWord();
    	Token token=this.getOperToken(this.cSelectChar+"");
    	this.tokens.add(token);
    }
    
    //处理 = 等于号
    private void doEquation(){
    	this.doBeforeWord();
    	Token token=this.getOperToken(this.cSelectChar+"");
    	this.tokens.add(token);
    }
    
    //处理 < 小于号 查看下一个是否是 >或=
    private void doLess(){   	
    	this.doBeforeWord();
    	Token token;
    	String word;
    	//在this.doBeforeWord() 有 this.inputCharPosition++
    	if(this.inputCharPosition<this.inputLeng){
    		char c=this.input.charAt(this.inputCharPosition);
    		if(c=='>'||c=='='){
    			word=this.cSelectChar+""+c;
    			token=this.getOperToken(word);
    			this.inputCharPosition++;
    		}else{
    			word=this.cSelectChar+"";
    			token=this.getOperToken(word);
    		}
    	}else{
    		word=this.cSelectChar+"";
    		token=this.getOperToken(word);
    		
    	}
    	this.tokens.add(token);
    	
    }
    //处理 > 大于号 查看下一个是否为 = 
    private void doMore(){
    	this.doBeforeWord();
    	Token token;
    	String word;
    	if(this.inputCharPosition<this.inputLeng){
    		char c=this.input.charAt(this.inputCharPosition);
    		if(c=='='){
    			word=this.cSelectChar+""+c;
    			token=this.getOperToken(word);
    			this.inputCharPosition++;
    		}else{
    			word=this.cSelectChar+"";
    			token=this.getOperToken(word);
    		}
    	}else{
    		word=this.cSelectChar+"";
    		token=this.getOperToken(word);
    	}
    	this.tokens.add(token);
    }
    
    private void doOneOper(char cOper){
    	this.doBeforeWord();
    	String word=cOper+"";
    	Token token=this.getOperToken(word);
    	this.tokens.add(token);
    }
    //处理* 
    private void doStar(){
    	this.doOneOper(this.cSelectChar);
    }
    //处理+
    private void doAdd(){
    	this.doOneOper(this.cSelectChar);
    }
    //处理 -
    private void doMinus(){
    	this.doOneOper(this.cSelectChar);
    }
    //处理 / 
    private void doDiv(){
    	this.doOneOper(this.cSelectChar);
    }

	// 对select语句的处理 得到token列表
	// table 和column的以 字母开头 接 数字和字母 (A-Z|a-z) + (1-9|A-Z|a-z)
	// 字符串为任意的符号 ****
	// 常量为数字 1-9和点
	// 遇到 . 的时候要判断是 小数点 还是 table.column 的点 向前的一个token查看
	// * 为操作符 或 乘号 table.* 或 a*a 向前的一个token查看 默认是oper 对得到的token要修改

	private void doInputSelect() {
		try {
			this.inputLeng = this.input.length();

			// 对存放字符的数组的初始化
			this.cWord = new char[this.cWordSize];
			this.cWordPosition=0;
			this.inputCharPosition = 0;

			while (this.inputCharPosition < this.inputLeng) {
				this.cSelectChar = this.input.charAt(this.inputCharPosition);
//			System.out.println("Position:"+this.inputCharPosition+" Char:"+cSelectChar);
				switch (this.cSelectChar) {
				case '\n':
				case '\r':
				case '\t': {
					this.doNewLineEnter();
					break;
				}
				case ' ': {
					this.doSpace();
					break;
				}
				case '\'':{
					this.doSingleQuotes();
					break;
				}
				case '"':{
					this.doDoubleQuotes();
					break;
				}
				case '.':{
					this.doDot();
					break;
				}
				case ',':{
					this.doComma();
					break;
				}
				case '(':
				case ')':{
					this.doBracket();
					break;
				}
				case '=':{
					this.doEquation();
					break;
				}
				case '<':{
					this.doLess();
					break;
				}
				case '>':{
					this.doMore();
					break;
				}
				case '*':{
					this.doStar();
					break;
				}
				case '+':{
					this.doAdd();
					break;
				}
				case '-':{
				    this.doMinus();	
					break;
				}
				case '/':{
					this.doDiv();
					break;
				}
				
				
				default:{
					this.cWord[this.cWordPosition]=this.cSelectChar;
					this.cWordPosition++;
					this.inputCharPosition++;
				}
				}

			}
		} catch (RuntimeException e) {
			this.errList.add("词法有误！不支持中文名");
			
			//e.printStackTrace();
			return;
		}
	}
	public static void main(String[] args) {
		String testStr = ST_APP.testSql;
		testStr = "select a.*,* from c ";
		testStr="select id from user where name like '2' escape '\' ";
		testStr="select (((sum(id)))),(id+1) from user";
		testStr="select * from user where id in( select id from user where id =2)";
		System.out.println("testStr:"+testStr);
		TokenAnalyze ta = new TokenAnalyze(testStr);
		List list=ta.getTokens();
		for(int i=0;i<list.size();i++){
			Token t=(Token)list.get(i);
			System.out.println(i+"  :"+t.getType()+"  :"+t.getWord()+"  :"+t.getWordUpper());
		}
		for(int i=0;i<ta.getErrList().size();i++){
			String str=(String)ta.getErrList().get(i);
			System.out.println(str);
		}
		List listt=new ArrayList();
		listt.add(2);
		if(listt.get(0).equals(2)){
			System.out.println(listt.get(0));	
		}
		

	}
	//判断是否为ident
	private boolean isIdent(String word){
		boolean yes=true;
		int leng =word.length();
		if(leng>0){
			char cFirst=word.charAt(0);
			if(cFirst>='a'&&cFirst<='z' || cFirst>='A'&&cFirst<='Z' ||cFirst=='_'){
				for(int i=1;i<leng;i++){
					char c=word.charAt(i);
					if(c>='a'&&c<='z' || c>='A'&&c<='Z' ||c=='_' || c>='0'&&c<='9'){
						
					}else{
						yes=false;
						break;
					}
				}
			}else{
				yes=false;
			}

		}else{
			yes=false;
		}
		return yes;
	}
	private boolean isInt(String word){
		boolean yes=true;
		int leng=word.length();
		if(leng >0){
			for(int i=0;i<leng;i++){
				char c=word.charAt(i);
				if(c>='0'&&c<='9'){
					
				}else{
					yes=false;
					break;
				}
			}
		}else{
			yes=false;
		}
		return yes;
	}

}
