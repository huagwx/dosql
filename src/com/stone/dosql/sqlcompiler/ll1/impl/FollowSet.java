package com.stone.dosql.sqlcompiler.ll1.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.accessories.ST_LL1_FLAG;
import com.stone.dosql.accessories.ST_TYPE;
import com.stone.dosql.util.LL1Word;
import com.stone.dosql.util.SetWord;
import com.stone.dosql.util.Syntax;

public class FollowSet {
	private Syntaxs syntaxs;  //得到grammar的对象
	private SyntaxFile syntaxFile;  //得到非终结符的对象
	private List grammarList;
	private int vnSize;
	private List vtFirst;
	private List vnFirst;
	private LL1Word[] table;
	private List vnList;
	private List vtList;
	private List vNullFirst = new ArrayList();
	private List vnFollow=new ArrayList();
	private String followSharp="#";
	
	public FollowSet(String selectFile) {
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
	}
	//初始化所有的非终结符的Follow 只有名称
	private void initFollow(){
		int vn_size=this.vnList.size();
		for(int i=0;i<vn_size;i++){
			SetWord _setWord=new SetWord();
			
			
			//类型为follow集合
			_setWord.setSetType(ST_TYPE.SetWord_TYPE_FOLLOW);
			_setWord.setName((String)this.vnList.get(i));
			this.vnFollow.add(_setWord);
		}
	}
	// 计算Follow集	
	//this.vnFollow.set 的值都是setWord对象*********************
	private void computeFollow1(){
		this.initFollow();
		 boolean meetQuery_spec=false;
		int vn_size=this.vnList.size();
		
		//对每一个非终结符开始找follow
		for(int i0=0;i0<vn_size;i0++){
			String vnName=(String)this.vnList.get(i0);
			
			
			//对文法的开始符号 query_spec 添加 #
			if(!meetQuery_spec){
				if(vnName.equals(ST_APP.ST_query_spec)){
					meetQuery_spec=true;
					SetWord sharp=new SetWord();
					sharp.setSetType(ST_TYPE.SetWord_TYPE_FIRST);
					sharp.setName(this.followSharp);
					List sharpList=new ArrayList();
					sharpList.add(this.followSharp);
					sharp.setSet(sharpList);
					((SetWord)this.vnFollow.get(i0)).getSet().add(sharp);
				}
			}
			//右部的有该终结符
			List vnList=this.getRightHasNeedVn(vnName);
			
			//右部的有该终结符 的产生式 为 0
			if(vnList.size()==0){
				//System.out.println(i0+":"+vnName+"  在右部没<<<<<<<<<<??????????有对应的");
				continue;
			}else{
				int vnList_size=vnList.size();
				for(int i1=0;i1<vnList_size;i1++){
					
					//取出每一个对应的产生式
					Syntax syn1=(Syntax)vnList.get(i1);
					
					
					
					String name_each=syn1.getLeft().getName();
					int id_name_follow1=-1; //????
					for(int fi=0;fi<this.vnFirst.size();fi++){
						if(name_each.equals(((SetWord)this.vnFollow.get(fi)).getName())){
							id_name_follow1=fi;
							break;
						}
					}
					
					
					
					
					
					//System.out.println(syn1.getLeft().getName());
					List eachRight=syn1.getRight();
					int size_eachRight=eachRight.size();
					
			//		System.out.println("左边de Follow位置:"+id_leftFollow);
					
					//每一个产生式的right
					for(int i2=0;i2<size_eachRight;i2++){
						Syntax r2=(Syntax)eachRight.get(i2);
						
						
						if(r2.getLeft().getName().equals(vnName)){ //开始加入follow集合
							//最后一个
							
							if(i2+1==size_eachRight){
								
								((SetWord)this.vnFollow.get(i0)).getSet().add(this.vnFollow.get(id_name_follow1));
							}else if(i2+1<size_eachRight){
								
								int after_id=i2+1;  //后面的id

									//获取后面 first的id
									
									
									int afterposition=-1;
									for(int ai=after_id;ai<size_eachRight;ai++){
										Syntax safter=(Syntax)eachRight.get(ai);
										String safterName=safter.getLeft().getName();
										if(safter.getLeft().getType()==ST_TYPE.ST_VT){
											afterposition=ai;
											break;
										}else if(safter.getLeft().getType()==ST_TYPE.ST_VN){
											boolean isEnd=true;
											for(int i4=0;i4<this.table.length;i4++){
												if(safterName.equals(this.table[i4].getVnWord())){
													if(this.table[i4].getFlag()==ST_LL1_FLAG.ST_YES){
														isEnd=false;
													}
													break;
												}
											}
											if(isEnd){
												afterposition=ai;
												break;
											}
										}
									}
										//后面的所有都推出空 null
										if(afterposition==-1){
											afterposition=size_eachRight-1;
											((SetWord)this.vnFollow.get(i0)).getSet().add(this.vnFollow.get(id_name_follow1));
										}
									for(int aai=after_id;aai<=afterposition;aai++){
											Syntax ssynt=(Syntax)eachRight.get(aai);
											String ssyntName=ssynt.getLeft().getName();
											if(ssynt.getLeft().getType()==ST_TYPE.ST_VN){
												int vni=-1;
												for(int fi=0;fi<this.vnFirst.size();fi++){
													if(ssyntName.equals(((SetWord)this.vnFirst.get(fi)).getName())){
														vni=fi;
														break;
													}
												}
												((SetWord)this.vnFollow.get(i0)).getSet().add(this.vnFirst.get(vni));
											}else if(ssynt.getLeft().getType()==ST_TYPE.ST_VT){
												int vni=-1;
												for(int fi=0;fi<this.vtFirst.size();fi++){
													if(ssyntName.equals(((SetWord)this.vtFirst.get(fi)).getName())){
														vni=fi;
														break;
													}
												}
												((SetWord)this.vnFollow.get(i0)).getSet().add(this.vtFirst.get(vni));
											}

											
										}
										
										
							
									}
									
																							
							break;
							}
							
							
						}
					}
					
				
			}
			

		}
//		System.out.println("start 初始化得到得follwo集合关系 ，包括first和follow........");
//		for(int i=0;i<this.vnFollow.size();i++){
//			SetWord setWord=(SetWord)this.vnFollow.get(i);
//			List set=setWord.getSet();
//			System.out.print(i+" :"+setWord.getName()+" fffffffffff ");
//		for(int j=0;j<set.size();j++){
//				System.out.print("("+((SetWord)set.get(j)).getName()+"  ");
//				int type=((SetWord)set.get(j)).getSetType();
//				if(type==ST_TYPE.SetWord_TYPE_FOLLOW){
//					System.out.print(" type:Follow ");
//				}else if(type==ST_TYPE.SetWord_TYPE_FIRST){
//					System.out.print(" type:First ");
//				}
//				System.out.print(")");
//		}
//			System.out.println();
//		}
//		System.out.println("end 初始化得到得follwo集合关系 ，包括first和follow........");
	}
	
	//删除   跟左边相同的  和  右边里面有相同的
	private void deleteSamenessFollow(){
		for(int i=0;i<this.vnFollow.size();i++){
			SetWord setWord=(SetWord)this.vnFollow.get(i);
			String namei=setWord.getName();
			List list=setWord.getSet();
			for(int j=0;j<list.size();j++){
				SetWord swj=(SetWord)list.get(j);
				if(swj==null){
					continue;
				}
				String namej=swj.getName();
				//删除跟左边重名的
				if(namej.equals(namei)&&swj.getSetType()==ST_TYPE.SetWord_TYPE_FOLLOW){
					((SetWord)this.vnFollow.get(i)).getSet().set(j, null);
					continue;
				}
			}
			boolean hasNull=true;
			while(hasNull){
				if(((SetWord)this.vnFollow.get(i)).getSet().remove(null)){
					
				}else{
					hasNull=false;
				}
			}
		}
		for(int i=0;i<this.vnFollow.size();i++){
			SetWord setWord=(SetWord)this.vnFollow.get(i);
			String namei=setWord.getName();
			List list=setWord.getSet();
			for(int j=0;j<list.size();j++){
				SetWord swj=(SetWord)list.get(j);
				if(swj==null){
					continue;
				}
				for(int k=j+1;k<list.size();k++){
					SetWord swk=(SetWord)list.get(k);
					if(swk==null){
						continue;
					}
					
					if(swj.getName().equals(swk.getName())&&swj.getSetType()==swk.getSetType()){
						((SetWord)this.vnFollow.get(i)).getSet().set(k, null);
						continue;
					}
				}
				//删除跟左边重名的

			}
			boolean hasNull=true;
			while(hasNull){
				if(((SetWord)this.vnFollow.get(i)).getSet().remove(null)){
					
				}else{
					hasNull=false;
				}
			}
		}
//		System.out.println("start follwo集合关系 ，包括first和follow.");
//		for(int i=0;i<this.vnFollow.size();i++){
//			SetWord setWord=(SetWord)this.vnFollow.get(i);
//			List set=setWord.getSet();
//			System.out.print(i+" :"+setWord.getName()+" ffffDDfffff ");
//			for(int j=0;j<set.size();j++){
//				System.out.print("("+((SetWord)set.get(j)).getName()+"  ");
//				int type=((SetWord)set.get(j)).getSetType();
//				if(type==ST_TYPE.SetWord_TYPE_FOLLOW){
//					System.out.print(" type:Follow ");
//				}else if(type==ST_TYPE.SetWord_TYPE_FIRST){
//					System.out.print(" type:First ");
//				}else{
//					System.out.println("....");
//				}
//				System.out.print(")");
//			}
//			System.out.println();
//		}
//		System.out.println("end follwo集合关系 ，包括first和follow........");
	}
	
	//follow集合初始化所有vn被访问 都为false 没被访问过
	private boolean[] getInitAllVnSearch(){
		int vnSize=this.vnFollow.size();
		boolean[] allFalseVn=new boolean[vnSize];
		for(int i=0;i<vnSize;i++){
			allFalseVn[i]=false;
		}
		return allFalseVn;
	}
	private boolean getFindVnBooleanResult(boolean[] compareVnBoolean,String followName){
		int vnNamePosition=-1;
		for(int i=0;i<this.vnFollow.size();i++){
			if(((SetWord)this.vnFollow.get(i)).getName().equals(followName)){
				vnNamePosition=i;
				break;
			}
		}
		return compareVnBoolean[vnNamePosition];
		
	}
	
	//返回  右边的follow的等价集合 
	private List getRightFollowEqueal(String followName){
		List list=new ArrayList();
		for(int i=0;i<this.vnFollow.size();i++){
			if(((SetWord)this.vnFollow.get(i)).getName().equals(followName)){
				list=((SetWord)this.vnFollow.get(i)).getSet();
				break;
			}
			
		}
		return list;
	}
	private boolean[] setFindTrue(boolean[] compareVnBoolean,String followName){
		int vnNamePosition=-1;
		for(int i=0;i<this.vnFollow.size();i++){
			if(((SetWord)this.vnFollow.get(i)).getName().equals(followName)){
				vnNamePosition=i;
				break;
			}
		}
		compareVnBoolean[vnNamePosition]=true;
		return compareVnBoolean;
	}
	
	public void computeFollow() {
		
		//最终的非终结符的follow集合
		List resultVnFollow=new ArrayList();
		
		
		//得到各个非终结符的Follow集合
		this.computeFollow1();
		//删除相同的项目
		this.deleteSamenessFollow();
		//获得最终的follow集合
	//	System.out.println(this.table.length +" "+ this.vnFollow.size());
		for(int i=0;i<this.vnFollow.size();i++){
	//		System.out.println(i+":"+((SetWord)this.vnFollow.get(i)).getName());
			boolean[] compareVnBoolean=this.getInitAllVnSearch();
			compareVnBoolean[i]=true;  //本身的follow设置为true ？因为直接递归后的follow集合是不变的
			
			//对每一个非终结符的右部的SetWord类型的列表 处理到没有follow类型的SetWord
			boolean isContinue=true;  //直到里面没有 follow的类型
			
			String followName=((SetWord)this.vnFollow.get(i)).getName();

			while(isContinue){
				List right=((SetWord)this.vnFollow.get(i)).getSet();
				int size_right=right.size();
				boolean isFinish=false;
				for(int j=0;j<size_right;j++){
					SetWord rSetWord=(SetWord)right.get(j);
					//右边的setword是follow集合时
					if(rSetWord.getSetType()==ST_TYPE.SetWord_TYPE_FOLLOW){
							boolean hasFind=this.getFindVnBooleanResult(compareVnBoolean,rSetWord.getName());
				//			System.out.println(hasFind+":"+rSetWord.getName());
							//如果被访问过 就删除该setWord
							if(hasFind){
								((SetWord)this.vnFollow.get(i)).getSet().remove(j);
							}else{
								((SetWord)this.vnFollow.get(i)).getSet().remove(j);
								compareVnBoolean=this.setFindTrue(compareVnBoolean, rSetWord.getName());
								List equalList=this.getRightFollowEqueal(rSetWord.getName());
								for(int li=0;li<equalList.size();li++){
									((SetWord)this.vnFollow.get(i)).getSet().add(equalList.get(li));
								}
							}
						break;
					}
					if(j==size_right-1){
						isFinish=true;
					}
					
				}
				//右边的所有都是first类型 得到该vn得follow集合 退出
				if(isFinish){
					SetWord followSetWord=new SetWord();
					followSetWord.setName(followName);
					for(int i1=0;i1<size_right;i1++){
						List l1=((SetWord)right.get(i1)).getSet();
						for(int i2=0;i2<l1.size();i2++){
							String s2=(String)l1.get(i2);
							if(s2.equals(ST_APP.ST_NULL_S)){
								//去掉空
							}else{
								followSetWord.getSet().add(s2);
							}
							
						}
					}
					resultVnFollow.add(followSetWord);
					
					isContinue=false;
				}
				
			}
		}
		
	       this.vnFollow=resultVnFollow;
//	//       System.out.println(vnFollow.size()+"   没有删除相同的 ? 63 63 63 63 63 sdfsdfsjdfl;a sjfl;asjglasdjgl;as sdljg lasdjgsl adjg l;asdgj l;asdgj l;asdgj");
//			
//	       
//	       //输出follow的集合
//	       for(int i=0;i<this.vnFollow.size();i++){
//	    	   SetWord setWord=(SetWord)this.vnFollow.get(i);
//	    	   List right=setWord.getSet();
//	    	   System.out.print(i+" :name: "+setWord.getName()+" follow:");
//	    	   for(int j=0;j<right.size();j++){
//	    		   System.out.print("   "+(String)right.get(j));
//	    	   }
//	    	   System.out.println();
//	       }
//		
//		
//		
//		
	   //删除相同的集
	  for(int i=0;i<resultVnFollow.size();i++){
		  List right=((SetWord)resultVnFollow.get(i)).getSet();
		  for(int j=0;j<right.size();j++){
			  String rj=(String)right.get(j);
			  if(rj==null){
				  continue;
			  }
			  for(int k=j+1;k<right.size();k++){
				  String rk=(String)right.get(k);
				  if(rk==null){
					  continue;
				  }
				  if(rj.equals(rk)){
					  ((SetWord)resultVnFollow.get(i)).getSet().set(k, null); 
				  }
			  }
		  }
		  boolean hasNull=true;
		  while(hasNull){
			  if(((SetWord)resultVnFollow.get(i)).getSet().remove(null)){
				  
			  }else{
				  hasNull=false;
			  }
		  }
	  }
		
	  
	  
	  
		
       this.vnFollow=resultVnFollow;
    
		
//       
//       //输出follow的集合
//       for(int i=0;i<this.vnFollow.size();i++){
//    	   SetWord setWord=(SetWord)this.vnFollow.get(i);
//    	   List right=setWord.getSet();
//    	   System.out.print(i+": "+setWord.getName()+" 的follow集合:");
//    	   for(int j=0;j<right.size();j++){
//    		   System.out.print("   "+(String)right.get(j));
//    	   }
//    	   System.out.println();
//       }
		
	}
	
	//返回 在右部含有该 非终结符 的 产生式
	private List getRightHasNeedVn(String vnword){
		List list=new ArrayList();
		for(int i=0;i<this.grammarList.size();i++){
			Syntax syntax=(Syntax)this.grammarList.get(i);
			List right=syntax.getRight();
			int right_size=right.size();
			boolean isHas=false;
			for(int j=0;j<right_size;j++){
				Syntax syntaxR=(Syntax)right.get(j);
				if(syntaxR.getLeft().getName().equals(vnword)){
					isHas=true;
					break;
				}
			}
			if(isHas){
				list.add(syntax);
			}
			
		}
		return list;
	}
	public List getVnFollow(){
		this.computeFollow();
		return this.vnFollow;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FollowSet fs=new FollowSet(ST_APP.DBMS_SQL_SELECT_EXP);
		fs.getVnFollow();

	}

}
