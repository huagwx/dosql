package com.stone.dosql.sqlcompiler.ll1.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_LL1_FLAG;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.LL1Word;
import com.stone.dosql.util.SelectSetWord;
import com.stone.dosql.util.SetWord;
import com.stone.dosql.util.Syntax;
import com.stone.dosql.util.VWord;

public class SelectSet {
	private Syntaxs syntaxs;  //得到grammar的对象
	private SyntaxFile syntaxFile;  //得到非终结符的对象
	private List grammarList;
	private int vnSize;
	private List vtFirst;
	private List vnFirst;
	private LL1Word[] table;
	private List vnList;
	private List vtList;
	private List vnFollow;
	private List vnSelect=new ArrayList();
	
	public SelectSet(String selectFile) {
		VnEnduceNull vnEnduceNull=new VnEnduceNull(selectFile);
		table=vnEnduceNull.getVnDuceNullTable();
		syntaxs = new Syntaxs(selectFile);
		grammarList = syntaxs.getAllSyntaxs();
		syntaxFile = new SyntaxFile(selectFile);
		vnList=syntaxFile.getVnList();
		vtList=syntaxFile.getVtList();
		vnSize=vnList.size();
		FirstSet firstSet=new FirstSet(selectFile);
		this.vnFirst=firstSet.getVnFirst();
		this.vtFirst=firstSet.getVtFirst();
		FollowSet followSet=new FollowSet(selectFile);
		this.vnFollow=followSet.getVnFollow();
	}
	// 计算Select集
	public void computeSelect() {
		
		for(int i=0;i<this.vnList.size();i++){

			
			//取得每一个非终结符的名字
			String vnName=(String)this.vnList.get(i);
			
			//取得相同左部的产生式list
			List sameLeftList=this.getSameLeftGrammer(vnName);
			int size_same=sameLeftList.size();
			for(int j=0;j<size_same;j++){
				//求每一个非终结符的select集合
				SelectSetWord selectSetWord=new SelectSetWord();
				selectSetWord.setSyntax((Syntax)sameLeftList.get(j));
				List set=new ArrayList();
				
				//计算每一个产生式的select集合
				List right=((Syntax)sameLeftList.get(j)).getRight();
				
				int notNullId=-1;  //表示右部能推导出null
				int type=-1;   //不能退出null 的类型 vn或vt
				for(int k=0;k<right.size();k++){
					Syntax syntaxk=(Syntax)right.get(k);
					VWord vword=syntaxk.getLeft();
					if(vword.getType()==ST_TYPE.ST_VT){
						notNullId=k;
						type=ST_TYPE.ST_VT;
						break;
					}else if(vword.getType()==ST_TYPE.ST_VN){
						int llvnArrayFlag=this.getFlagFormll1WordArray(vword.getName());
						//非终结符不能推导出null
						if(llvnArrayFlag==ST_LL1_FLAG.ST_NO){
							notNullId=k;
							type=ST_TYPE.ST_VN;
							break;
						}else{
							continue;
						}
					}else if(vword.getType()==ST_TYPE.ST_NULL_VT){
						//直接导出null不进行其他操作
					}
					
				}
				//右部能推导出null
				if(notNullId==-1){
					for(int l=0;l<right.size();l++){
						Syntax syntaxk=(Syntax)right.get(l);
						VWord vword=syntaxk.getLeft();
						if(vword.getType()==ST_TYPE.ST_VN){
							List firstList=this.getFirstByName(vword.getName());
							for(int l1=0;l1<firstList.size();l1++){
								if(((String)firstList.get(l1)).equals(ST_APP.ST_NULL_S)){
									//去掉空 ST_NULL_S
									
								}else{
									set.add(firstList.get(l1));
								}
							}
						}else if(vword.getType()==ST_TYPE.ST_NULL_VT){
							
						}
					}
					List followList=this.getFollowByName(vnName);
					for(int f1=0;f1<followList.size();f1++){
						set.add(followList.get(f1));
					}
					
				}else{   //右部不能导出null 
					//计算  前面的vn 能导出null的first
					for(int ni=0;ni<=notNullId;ni++){
						Syntax syntaxk=(Syntax)right.get(ni);
						VWord vword=syntaxk.getLeft();
						if(vword.getType()==ST_TYPE.ST_VN){
							List firstList=this.getFirstByName(vword.getName());
							for(int l1=0;l1<firstList.size();l1++){
								if(ni==notNullId){  //最后一个
									set.add(firstList.get(l1));
								}else{
									if(((String)firstList.get(l1)).equals(ST_APP.ST_NULL_S)){
										//去掉空 ST_NULL_S
										
									}else{
										set.add(firstList.get(l1));
									}
								}

							}
						}else if(vword.getType()==ST_TYPE.ST_VT){
							List list=this.getVtFirstByName(vword.getName());
							for(int vti=0;vti<list.size();vti++){
								set.add(list.get(vti));
							}
						}
					}
					
				}
				
				
				
				set=this.getDistinctFollowSet(set);
				
				
				selectSetWord.setSet(set);
				
				this.vnSelect.add(selectSetWord);
				
			}
			
		}
//		//输出每一个select集合
//		for(int i=0;i<this.vnSelect.size();i++){
//			SelectSetWord ssw=(SelectSetWord)this.vnSelect.get(i);
//			String grammar="";
//			Syntax syn=ssw.getSyntax();
//			grammar+=syn.getLeft().getName()+"  ->  ";
//			
//			List synRight=syn.getRight();
//			for(int j=0;j<synRight.size();j++){
//				Syntax synr=(Syntax)synRight.get(j);
//				grammar+=synr.getLeft().getName()+"   ";
//			}
//			grammar+="   select集合:";
//			List set=ssw.getSet();
//			for(int j=0;j<set.size();j++){
//				grammar+=(String)set.get(j)+"   ";
//			}
//			System.out.println(i+" ::: "+grammar);
//			
//			
//		}
		
	}
	//获得终结符的List 通过name
	private List getVtFirstByName(String vtName){
		List list=new ArrayList();
		for(int i=0;i<this.vtFirst.size();i++){
			if(((SetWord)this.vtFirst.get(i)).getName().equals(vtName)){
				list=((SetWord)this.vtFirst.get(i)).getSet();
				break;
			}
		}
//		System.out.println("............................................................................vtFirstBynName ::::::List.size:::::"+list.size());
//		if(list.size()==0){
//			System.out.println(vtName);
//		}
		return list;
	}
	
	//select集合删除相同的部分
	private List getDistinctFollowSet(List set){
		for(int i=0;i<set.size();i++){
			String si=(String)set.get(i);
			if(si==null){
				continue;
			}
			for(int j=i+1;j<set.size();j++){
				String sj=(String)set.get(j);
				if(sj==null){
					continue;
				}
				if(si.equals(sj)){
					set.set(j, null);
				}
			}
		}
		//删除null
		boolean hasNull=true;
		while(hasNull){
			if(set.remove(null)){
				
			}else{
				hasNull=false;
			}
		}
		return set;
	}
	
	
	//select 获取follow 通过产生式的左部的非终结符
	private List getFollowByName(String vnName){
		List list =new ArrayList();
		for(int i=0;i<this.vnFollow.size();i++){
			if(((SetWord)this.vnFollow.get(i)).getName().equals(vnName)){
				list=((SetWord)this.vnFollow.get(i)).getSet();
				break;
			}
		}
		return list;
			
		
	}
	
	//select 获取first包括有null  通过name来取得  list	
	private List getFirstByName(String vnName){
		List list=new ArrayList();
		for(int i=0;i<this.vnFirst.size();i++){
			if(((SetWord)this.vnFirst.get(i)).getName().equals(vnName)){
				list=((SetWord)this.vnFirst.get(i)).getSet();
				break;
			}
		}
		return list;
	}
	
	//select 通过名字name获得非终结符是否推导出null在this.ll1WordArray查找
	private int getFlagFormll1WordArray(String vnName){
		int flag=-1;
		for(int i=0;i<this.table.length;i++){
			if(this.table[i].getVnWord().equals(vnName)){
				flag=this.table[i].getFlag();
				break;
			}
		}
		return flag;
	}
	
	
	//select 获取左部相同的产生式 list 
	private List getSameLeftGrammer(String vnName){
		List list=new ArrayList();
		for(int i=0;i<this.grammarList.size();i++){
			Syntax si=(Syntax)this.grammarList.get(i);
			if(si.getLeft().getName().equals(vnName)){
				list.add(si);
			}
		}
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SelectSet ss=new SelectSet(ST_APP.DBMS_SQL_SELECT_EXP);
		ss.getVnSelect();		
	}
	public List getVnSelect(){
		this.computeSelect();
		return this.vnSelect;
	}

}
