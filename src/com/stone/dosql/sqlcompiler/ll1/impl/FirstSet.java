package com.stone.dosql.sqlcompiler.ll1.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_LL1_FLAG;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.LL1Word;
import com.stone.dosql.util.SetWord;
import com.stone.dosql.util.Syntax;

public class FirstSet {
	private Syntaxs syntaxs;  //得到grammar的对象
	private SyntaxFile syntaxFile;  //得到非终结符的对象
	private List grammarList;
	private int vnSize;
	private List vtFirst=new ArrayList();
	private List vnFirst = new ArrayList();
	private LL1Word[] table;
	private List vnList;
	private List vtList;
	private List vNullFirst = new ArrayList();
	
	public FirstSet(String selectFile) {
		VnEnduceNull vnEnduceNull=new VnEnduceNull(selectFile);
		table=vnEnduceNull.getVnDuceNullTable();
		syntaxs = new Syntaxs(selectFile);
		grammarList = syntaxs.getAllSyntaxs();
		syntaxFile = new SyntaxFile(selectFile);
		vnList=syntaxFile.getVnList();
		vtList=syntaxFile.getVtList();
		vnSize=vnList.size();
		
	}
	// 终结符的First集合
	private void computeEachVtSymbolFirst() {
		int vt_size = this.vtList.size();
		for (int i = 0; i < vt_size; i++) {
			String name = (String) this.vtList.get(i);
			List set = new ArrayList();
			set.add(name);
			SetWord _setWord = new SetWord();
			//默认 _setWord.setSetType(ST_TYPE.SetWord_TYPE_FIRST);
			_setWord.setName(name);
			_setWord.setSet(set);
			this.vtFirst.add(_setWord);
		}
	}

	// null 推出空的First集合
	private void computeDuceNUllFirst() {
		String name = ST_APP.ST_NULL_S;
		List set = new ArrayList();
		set.add(name);
		SetWord _setWord = new SetWord();
		// 默认 _setWord.setSetType(ST_TYPE.SetWord_TYPE_FIRST);
		_setWord.setName(name);
		_setWord.setSet(set);
		this.vNullFirst.add(_setWord);

	}

	// 非终结符的First集合
	private int computeEachVnSynbolFirst() {

		int finish = 0; // 如果finish==0 说明first完成
		int vn_size = this.table.length;

		// 取出每一个非终结符
		for (int i = 0; i < vn_size; i++) {

			int vnDotNot = 0;
			SetWord _setWord = new SetWord();
			List set = new ArrayList();

			String name = this.table[i].getVnWord();
			boolean has=false;
			for(int ki=0;ki<this.vnFirst.size();ki++){
				if(((SetWord)this.vnFirst.get(ki)).getName().equals(name)){
					has=true;
					break;
				}
			}
			//vn的first集存在了就进入下一个
			//System.out.println("has:"+has);
			if(has){
				continue;
			}

			int flag = this.table[i].getFlag();

			List vnList = this.getOneVnList(name);

			//System.out.println(i + ":" + name + "  flag" + flag);
			//System.out.println("vnListSize:" + vnList.size());

			
			// 取出每一个终结符的所有文法的每一个
			for (int j = 0; j < vnList.size(); j++) {

				// 没有找到终结符的first集合 退出
				// 进入下一个终结符
				//System.out.println("vnDotNot:"+vnDotNot);
				//System.out.println(vnList.size());
				if (vnDotNot > 0) {
					break;
				}
				Syntax syntax = (Syntax) vnList.get(j);
				//System.out.println("j:" + j + " name:"
			//			+ syntax.getLeft().getName());

				List right = syntax.getRight();
				int right_size = right.size();
				boolean isContinue = true;

				// X->a.... a(-Vt

				// 若果a是终结符就保存a 退出本次循环
				if (isContinue) {
					
					//System.out.println("X->a....  a(-Vt");
					if (((Syntax) right.get(0)).getLeft().getType() == ST_TYPE.ST_VT) {
						//System.out.println("yesVT");
						set.add(((Syntax) right.get(0)).getLeft().getName());
						isContinue = false;
						continue;
					}
				}
				// X->null
				if (isContinue) {

					//System.out.println("X->null ");
					String r0_Name = syntax.getLeft().getName();
					boolean isFind = false;
					if (right_size == 1
							&& ((Syntax) right.get(0)).getLeft().getType() == ST_TYPE.ST_NULL_VT) {
						set.add(ST_APP.ST_NULL_S);
						if(name.equals("table")){
							//System.out.println("tableSize...null......"+vnList.size());
							//System.out.println(set.size()+(String)set.get(0)+" "+(String)set.get(1));
						}
						isContinue = false;
						continue;
					}
				}
				// 前面的非终结符是null df
				if (isContinue) {
					int vnv = -1;
					
					for (int rii = 0; rii < right.size(); rii++) {

						Syntax sT = (Syntax) right.get(rii);
						if (sT.getLeft().getType() == ST_TYPE.ST_VN) {
							boolean isNull = false;
							for (int rii0 = 0; rii0 < this.table.length; rii0++) {
								if (sT.getLeft().getName().equals(
										this.table[rii0].getVnWord())) {
									if (this.table[rii0].getFlag() == ST_LL1_FLAG.ST_YES) {
										isNull = true;
//										if(name.equals("table")){
//											System.out.println("yes");
//										}
//										System.out.println("yes");
									} else { // no
//										if(name.equals("table")){
//											System.out.println("no");
//										}
										

									}
									vnv = rii;
									break;
								}
							}
							if (!isNull) {
								break;
							}
						} else if (sT.getLeft().getType() == ST_TYPE.ST_VT) {
							vnv = rii;
							break;
						} else if (sT.getLeft().getType() == ST_TYPE.ST_NULL_VT) {

						}
					}
					// 不全空
					if (vnv <= right.size() - 1) {
//						if(name.equals("table")){
//							System.out.println("tableSize.不全空......"+vnList.size());
//							System.out.println(set.size());
//						}
//						System.out.println("不全空 "+vnv+" "+right.size());
						for (int jji = 0; jji < vnv; jji++) {
							String nri = ((Syntax) right.get(jji)).getLeft()
									.getName();

							List set1 = new ArrayList();
							for (int vii = 0; vii < this.vnFirst.size(); vii++) {
								if ((((SetWord) this.vnFirst.get(vii)))
										.getName().equals(nri)) {
									set1 = ((SetWord) this.vnFirst.get(vii))
											.getSet();
									break;
								}
							}
							if (set1.size() < 1) {
						//	System.out.println("33set1.size()<1");
								finish++;
								vnDotNot++;
								break;
							} else {
							//	System.out.println("33set1.size()>1");
								for (int set1i = 0; set1i < set1.size(); set1i++) {
									// 取出null
									if (((String) set1.get(set1i))
											.equals(ST_APP.ST_NULL_S)) {

									} else {
										set.add(set1.get(set1i));
									}

								}
							}

						}
						if (vnDotNot == 0) {
						//	System.out.println("vnv:"+vnv);
							String nri = ((Syntax) right.get(vnv)).getLeft().getName();
							List set1 = new ArrayList();
							for (int vii = 0; vii < this.vnFirst.size(); vii++) {
								if ((((SetWord) this.vnFirst.get(vii)))
										.getName().equals(nri)) {
									set1 = ((SetWord) this.vnFirst.get(vii))
											.getSet();
									break;
								}
							}
							if (set1.size() < 1) {
						//		System.out.println("33set1.size()<1");
								finish++;
								vnDotNot++;
								break;
							} else {
						//		System.out.println("33set1.size()>1");
								for (int set1i = 0; set1i < set1.size(); set1i++) {
										set.add(set1.get(set1i));
									
								}
							}
						}

						// 全空
					} else if (vnv == - 1) {
//						if(name.equals("table")){
//							System.out.println("tableSize全空ll......"+vnList.size());
//							System.out.println(set.size()+  "vnv:"+vnv);
//						}
//						System.out.println("全空"+vnv+" "+right.size());
						for (int jji = 0; jji < right.size(); jji++) {
								String nri = ((Syntax) right.get(jji)).getLeft()
										.getName();

								List set1 = new ArrayList();
								for (int vii = 0; vii < this.vnFirst.size(); vii++) {
									if ((((SetWord) this.vnFirst.get(vii)))
											.getName().equals(nri)) {
										set1 = ((SetWord) this.vnFirst.get(vii))
												.getSet();
										break;
									}
								}
								if (set1.size() < 1) {
							//		System.out.println("33set1.size()<1");
									finish++;
									vnDotNot++;
									break;
								} else {
						//	System.out.println("33set1.size()>1");
									for (int set1i = 0; set1i < set1.size(); set1i++) {
											set.add(set1.get(set1i));

									}
								}
						}
						set.add(ST_APP.ST_NULL_S);
					}
				}
//		//		System.out.print("set: ");
//				for (int ss = 0; ss < set.size(); ss++) {
//					System.out.print((String) set.get(ss) + " ");
//				}
//				System.out.println();
			}
			if (vnDotNot == 0) {
				_setWord.setName(name);
				int nullNum = 0;
				for (int si = 0; si < set.size(); si++) {
					if (set.get(si).equals(ST_APP.ST_NULL_S)) {
						nullNum++;
					}
				}
				for (int sii = 1; sii < nullNum; sii++) {
					set.remove(ST_APP.ST_NULL_S);
				}
				_setWord.setSet(set);
//				for (int jj = 0; jj < set.size(); jj++) {
//					System.out.println(name + " 集合" + set.get(jj));
//				}

				this.vnFirst.add(_setWord);
//				System.out.println("vnFirstSize:..................."+this.vnFirst.size());
			}
		}
		return finish;
	}

	// 返回要查找的name的所有文法
	private List getOneVnList(String name) {
		List list = new ArrayList();
		int vn_size = this.grammarList.size();
		for (int i = 0; i < vn_size; i++) {
//			System.out.println("vn_size"+vn_size);
			Syntax syntax = (Syntax) this.grammarList.get(i);

			if (syntax.getLeft().getName().equals(name)) {
		//		System.out.println("getOneVnList:"+name);
				list.add(syntax);
			}
		}
		return list;
	}

	// 计算First集
	public void computeFirst() {

		boolean isContinue = true;
		//String s = "..................................................................";
		while (isContinue) {
			int k=this.computeEachVnSynbolFirst();
	//		System.out.println("kkkkkkk:"+k);
			if (k==0) {
				
				isContinue = false;
			}
		}
		//删除相同的终结符或空
		for(int i=0;i<this.vnFirst.size();i++){
			SetWord sw=(SetWord)this.vnFirst.get(i);
			List set=sw.getSet();
			for(int j=0;j<set.size();j++){
				String first=(String)set.get(j);
				if(first==null){
					continue;
				}
				for(int k=j+1;k<set.size();k++){
					if(first.equals((String)set.get(k))){
						((SetWord)this.vnFirst.get(i)).getSet().set(k, null);
					}
				}
				
			}
			boolean hasNull=true;
			while(hasNull){
				if(((SetWord)this.vnFirst.get(i)).getSet().remove(null)){
					
				}else{
					hasNull=false;
				}
			}
		}


//			for(int i=0;i<this.vnFirst.size();i++){
//				SetWord sw=(SetWord)this.vnFirst.get(i);
//				System.out.print(i+":"+sw.getName()+" 的first集合:");
//				for(int j=0;j<sw.getSet().size();j++){
//					System.out.print((String)sw.getSet().get(j)+"  ");
//				}
//				for(int jj=0;jj<this.table.length;jj++){
//					if(sw.getName().equals(this.table[jj].getVnWord())){
//						if(this.table[jj].getFlag()==ST_LL1_FLAG.ST_YES){
//							System.out.print("  有空");
//						}else{
//							System.out.print("  没有空");
//						}
//						break;
//					}
//				}
//				System.out.println();
//			}
//			
//			for(int i=0;i<this.table.length;i++){
//				String vnStr=this.table[i].getVnWord();
//		//		System.out.println(i+":"+vnStr);
//				boolean exist=false;
//				for(int j=0;j<this.vnFirst.size();j++){
//					if(((SetWord)this.vnFirst.get(j)).getName().equals(vnStr))
//					{
//						exist=true;
//						break;
//					}
//				}
//				if(!exist){
//					System.out.println(vnStr);
//				}
//			}
		

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FirstSet fs=new FirstSet(ST_APP.DBMS_SQL_SELECT_EXP);
		fs.getVnFirst();

	}
	public List getVnFirst(){
		this.computeFirst();
		return this.vnFirst;
	}
	public List getVtFirst(){
		this.computeEachVtSymbolFirst();
		return this.vtFirst;
	}
	
	

}
