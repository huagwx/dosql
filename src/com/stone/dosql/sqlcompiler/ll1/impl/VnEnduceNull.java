package com.stone.dosql.sqlcompiler.ll1.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_LL1_FLAG;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.LL1Word;
import com.stone.dosql.util.Syntax;
import com.stone.dosql.util.VWord;

public class VnEnduceNull {
	
	private Syntaxs syntaxs;  //得到grammar的对象
	private SyntaxFile syntaxFile;  //得到非终结符的对象
	private LL1Word[] table;  //vn退出空的table 
	private List vnList;
	private List grammarList;
	private int vnSize;
	
	
	public VnEnduceNull(String selectFile) {
		syntaxs = new Syntaxs(selectFile);
		grammarList = syntaxs.getAllSyntaxs();
		syntaxFile = new SyntaxFile(selectFile);
		vnList=syntaxFile.getVnList();
		vnSize=vnList.size();
		this.table=new LL1Word[vnSize];
		
	}
	//(1)  初始化table 每一个都为未定的
	private void initTable(){
		for(int i=0;i<this.vnSize;i++){
			LL1Word ll1Word=new LL1Word((String)this.vnList.get(i),ST_LL1_FLAG.ST_UNDEFINE);
			this.table[i]=ll1Word;
		}
	}
	//(2) :1 删除右部含有终结符的产生式
	private void delGrammarRightHasVt(){
		
		int syntaxLeftSize = this.grammarList.size();
		for (int i = 0; i < syntaxLeftSize; i++) {
			// 获取一个产生式
			Syntax syntax = (Syntax) this.grammarList.get(i);

			boolean hasRightVt = false; // 右部含有终结符 标识
			// 获取右部
			List right = syntax.getRight();
			int rightSize = right.size();
			for (int ri = 0; ri < rightSize; ri++) {
				Syntax syntaxRight = (Syntax) right.get(ri);
				VWord vword = syntaxRight.getLeft();
				if (vword.getType() == ST_TYPE.ST_VT) {
					hasRightVt = true;
					break;
				}
			}
			if (hasRightVt) {
				//产生式设置为空
				this.grammarList.set(i, null);
			}

		}
		// 删除含null对象的产生式
		this.delGrammarListNull();
		
		// System.out.println(syntaxList.size());
		//得到删除后的产生式的大小
		int leftSyntaxListSize = this.grammarList.size();
		//System.out.println("leftSyntaxListSize..........."+leftSyntaxListSize);

		for (int i = 0; i < this.vnSize; i++) {
			LL1Word ll1word = this.table[i];
			boolean isExist = false;
			for (int j = 0; j < leftSyntaxListSize; j++) {
				Syntax syntax = (Syntax) this.grammarList.get(j);
				VWord vWord = syntax.getLeft();
				if (ll1word.getVnWord().equals(vWord.getName())) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				this.table[i].setFlag(ST_LL1_FLAG.ST_NO);
			}
		}
		
	}
	
	
	//删除vn-> ST_NULL_S 设置为 YES
	private void delRightDuceNull(){
		int leftSyntaxListSize = this.grammarList.size();
		for (int i = 0; i < leftSyntaxListSize; i++) {
			Syntax syntax = (Syntax) this.grammarList.get(i);
			if(syntax==null){
				continue;
			}
			
			List right = syntax.getRight();
			
			//文法的右部只有一个ST_NULL_S
			if (right.size() == 1) {
				Syntax synRig0 = (Syntax) right.get(0);
				if (synRig0.getLeft().getType() == ST_TYPE.ST_NULL_VT) {
					for (int j = 0; j < vnSize; j++) {
						LL1Word ll1word = this.table[j];
						if (ll1word.getVnWord().equals(
								syntax.getLeft().getName())) {
							this.table[j].setFlag(ST_LL1_FLAG.ST_YES);
							//删除相同名字的产生式
							for(int k=0;k<leftSyntaxListSize;k++){
								Syntax syntaxDel = (Syntax) this.grammarList.get(k);
								if(syntaxDel==null){
									continue;
								}
								if(syntaxDel.getLeft().getName().equals(ll1word.getVnWord())){
									this.grammarList.set(k, null);
								}
							}
							break;
						}
					}
				}
			}
		}
		// 删除含null对象的grammarList
		this.delGrammarListNull();
		

	}
	// (2) 扫描文法中的产生式 
	private void scanExpression1(){
		this.delGrammarRightHasVt();
		this.delRightDuceNull();

	}
	
	// 得到 非终结符得类型
	private int getVnFlagFormArray(String vnWord) {
		int type = -1;
		for (int i = 0; i < this.table.length; i++) {
			LL1Word llword = this.table[i];
			if (llword.getVnWord().equals(vnWord)) {
				type = llword.getFlag();
				break;
			}

		}
		return type;
	}

	//扫描产生式右部的每一个符号
	private boolean scanExpression2Continue(){
		boolean has=false;
		for(int i=0;i<this.vnSize;i++){
			if(this.table[i].getFlag()==ST_LL1_FLAG.ST_UNDEFINE){
				has=true;
				break;
			}
		}
		//还有为确定的vn
		if(has){
			
			int leftGrammarSize=this.grammarList.size();
	//	System.out.println("leftGrammarSize:"+leftGrammarSize);
			//查找剩下的每一个产生式
			for(int i=0;i<leftGrammarSize;i++){
				Syntax syntaxi = (Syntax) this.grammarList.get(i);
				if(syntaxi==null){
					continue;
				}
				VWord vWordi = syntaxi.getLeft();
	//	System.out.println(vWordi.getName());
				String namei = vWordi.getName();
				List listRight = syntaxi.getRight();
				//查看每一个产生式的右部每一符号
				for(int j=0;j<listRight.size();j++){
					if ((Syntax) this.grammarList.get(i) == null) {
						break;
					}	
					Syntax syntaxj = (Syntax) listRight.get(j);
					if (syntaxj == null) {
						continue;
					}
					VWord vWordj = syntaxj.getLeft();
					String namej = vWordj.getName();
					int flag = this.getVnFlagFormArray(namej);
					if(flag==ST_LL1_FLAG.ST_UNDEFINE){
						continue;
					}
					//右部的某一个vn符号的类型为yes
					if(flag==ST_LL1_FLAG.ST_YES){
						((Syntax) this.grammarList.get(i)).getRight().set(
								j, null);
						boolean isNull = true;
						for (int j1 = 0; j1 < listRight.size(); j1++) {
							if (listRight.get(j1) != null) {
								isNull = false;
								break;
							}
						}
						if (isNull) {
							for (int j2 = 0; j2 < this.vnSize; j2++) {
								if (this.table[j2].getVnWord().equals(namei)) {
									this.table[j2]
											.setFlag(ST_LL1_FLAG.ST_YES);
									break;
								}
							}
							for (int j3 = 0; j3 < leftGrammarSize; j3++) {
								Syntax delSyn=(Syntax)this.grammarList.get(j3);
								if(delSyn==null){
									continue;
								}
								if (delSyn.getLeft().getName().equals(namei)) {
									this.grammarList.set(j3, null);
								}
							}
						}
					//右部的某一个vn符号的类型为no
					}else if(flag==ST_LL1_FLAG.ST_NO){

						this.grammarList.set(i, null);
						for (int j1 = 0; j1 < this.vnSize; j1++) {
							String name3 = this.table[j1].getVnWord();
							if (name3.equals(namei)) {
								boolean isExist = false;
								for (int j2 = 0; j2 <leftGrammarSize; j2++) {
									Syntax syntaxj2 = (Syntax) this.grammarList
											.get(j2);
									if (syntaxj2 == null) {
										continue;
									}
									if (syntaxj2.getLeft().getName().equals(name3)) {
										isExist = true;
										break;
									}
								}
								if (!isExist) {
									this.table[j1].setFlag(ST_LL1_FLAG.ST_NO);
									break;
								}
								break;
							}

						}

					
					}
				}
			}
					
		}
		this.delGrammarListNull();
		
		return has;
	}
	private void delGrammarListNull(){
		// 删除含null对象的grammarList
		boolean hasNull = true;
		while (hasNull) {
			if (this.grammarList.remove(null)) {

			} else {
				hasNull = false;
			}
		}
	}
	private void scanExpression2(){
		boolean isContinue=true;
		while(isContinue){
			if(!this.scanExpression2Continue()){
				isContinue=false;
			}
		}

	}
	public LL1Word[] getVnDuceNullTable(){
		this.initTable();
		//this.outPutTable();
		this.scanExpression1();
	//	this.outPutTable();
		//System.out.println("1");
		this.scanExpression2();
	//	System.out.println("2");
//		this.outPutTable();
		return this.table;
	}
	public static void main(String[] args) {
		VnEnduceNull enduce=new VnEnduceNull(ST_APP.DBMS_SQL_SELECT_EXP);
		LL1Word[] table=enduce.getVnDuceNullTable();
		//enduce.outPutTable();  //输出table表的信息
		

	}
//	private void outPutTable(){
//		 System.out.println("leftSyntaxListSize:::::"+this.grammarList.size());
//		 for(int i=0;i<this.grammarList.size();i++){
//		 Syntax syntax=(Syntax)this.grammarList.get(i);
//		 VWord left=syntax.getLeft();
//		 List right=syntax.getRight();
//		 System.out.print(i+": "+left.getName()+" -> ");
//		 for(int j=0;j<right.size();j++){
//		 System.out.print(((Syntax)right.get(j)).getLeft().getName()+" ");
//		 }
//		 System.out.println();
//		 }
//		
//		
//		 for(int i=0;i<vnSize;i++){
//		 System.out.println(i+":..: "+this.table[i].getVnWord()+"	 flag:"+this.table[i].getFlag());
//		 }
//	}

}
