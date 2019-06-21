package com.stone.dosql.sqlcompiler.ll1.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.util.SelectSetWord;
import com.stone.dosql.util.Syntax;
import com.stone.dosql.util.SyntaxClone;
import com.stone.dosql.util.VWord;

public class LL1 {
	private Syntaxs syntaxs;  //得到grammar的对象
	private SyntaxFile syntaxFile;  //得到非终结符的对象
	private List grammarList;
	private List vnList;
	private List vnSelect;
	private List vtList;
	private int vnSize;
	private int vtSize;
	private Syntax[][] forecastAnalyzeTable;
	public LL1(String selectFile) {
		VnEnduceNull vnEnduceNull=new VnEnduceNull(selectFile);
		syntaxs = new Syntaxs(selectFile);
		grammarList = syntaxs.getAllSyntaxs();
		syntaxFile = new SyntaxFile(selectFile);
		vnList=syntaxFile.getVnList();
		vtList=syntaxFile.getVtList();
		
		vtList.add("#");  //vt加入 # 号
		vtSize=vtList.size();
		vnSize=vnList.size();
		SelectSet selectSet=new SelectSet(selectFile);
		this.vnSelect=selectSet.getVnSelect();
	}
	private int computeIsLL1(){
		int falsenum=0;
		for(int i=0;i<this.vnList.size();i++){
			int i_equal=0;
			String vnName=(String)this.vnList.get(i);
			List oneVnSelectList=this.getSelectByVnName(vnName);
			for(int j=0;j<oneVnSelectList.size();j++){
				String s1=(String)oneVnSelectList.get(j);
				for(int k=j+1;k<oneVnSelectList.size();k++){
					String s2=(String)oneVnSelectList.get(k);
					if(s1.equals(s2)){
						i_equal++;
						System.out.print(s1+"  ");
						
					}
				}
				
			}
			if(i_equal>0){
				falsenum++;
				System.out.println("selectError:"+vnName);
			}
		}
		
		return falsenum;//.....................................开始.........
	}
	//获取某一个非终结符的select 的list
	private List getSelectByVnName(String vnName){
		List list=new ArrayList();
		for(int i=0;i<this.vnSelect.size();i++){
			SelectSetWord select=(SelectSetWord)this.vnSelect.get(i);
			if(select.getSyntax().getLeft().getName().equals(vnName)){
				for(int j=0;j<select.getSet().size();j++){
					String str=(String)select.getSet().get(j);
					list.add(str);
				}
				
			}
		}
		return list;
	}
	
	//得到预测分析表 List 为Syntax的右部 List的内容为Syntax类型
	public Syntax[][] getForecastAnalyzeTable(){
		this.doForecastAnalyzeTable();
		return this.forecastAnalyzeTable;
	}
	private void doForecastAnalyzeTable(){
		int errorNum=this.computeIsLL1();
		//没有错误
		if(errorNum==0){
			this.initForecastAnalyzeTable();
			this.doVnSelect();
			
		}else{
			this.forecastAnalyzeTable=null;
		}
	}
	//初始化都为null 同时vt加入 "#"
	private void initForecastAnalyzeTable(){
		this.forecastAnalyzeTable=new Syntax[this.vnSize][this.vtSize];
		for(int i=0;i<this.vnSize;i++){
			for(int j=0;j<this.vtSize;j++){
				forecastAnalyzeTable[i][j]=null;
			}
		}
	}
	//填入表中
	private void doVnSelect(){
		int vnSelect_size=this.vnSelect.size();
		for(int i=0;i<vnSelect_size;i++){
			SelectSetWord ssw=(SelectSetWord)this.vnSelect.get(i);
			Syntax syntax=ssw.getSyntax();
			String vnName=syntax.getLeft().getName();
			int vnId=this.getVnIdAtTable(vnName);
			List set=ssw.getSet();
			int set_size=set.size();
			for(int j=0;j<set_size;j++){
				String vtName=(String)set.get(j);
				int vtId=this.getVtIdAtTable(vtName);
				SyntaxClone sClone=new SyntaxClone(syntax);
				Syntax syntaxClone=sClone.getCloneSyntax();
				
				this.forecastAnalyzeTable[vnId][vtId]=syntaxClone;
			}
		}
	}
	
	//获得预测分析表的终结符在分析表的位置
	public int getVtIdAtTable(String vtName){
		int id=-1;
		for(int i=0;i<this.vtSize;i++){
			if(((String)this.vtList.get(i)).equals(vtName)){
				id=i;
				break;
			}
		}
		return id;
	}
	
	//获得预测分析表的非终结符在分析表的位置
	public int getVnIdAtTable(String vnName){
		int id=-1;
		for(int i=0;i<this.vnSize;i++){
			if(((String)this.vnList.get(i)).equals(vnName)){
				id=i;
				break;
			}
		}
		return id;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Syntaxs syntax=new Syntaxs(ST_APP.DBMS_SQL_SELECT_EXP);
        List list=syntax.getAllSyntaxs();
//        System.out.println(list.size());
//       
//        for(int i=0;i<list.size();i++){
//        	 SyntaxClone sc=new SyntaxClone((Syntax)list.get(i));
//        	Syntax s=sc.getCloneSyntax();
//        	System.out.println(s.getLeft().getRule());
//        }
        
//	}else if(rule.equals(this.COMPARISON_6)){
//		this.doCOMPARISON_6();
        List listright=new ArrayList();
        for(int i=0;i<list.size();i++){
        	String right="";
        	Syntax syn=(Syntax)list.get(i);
        	VWord left=syn.getLeft();
        	right+=left.getName();
        	List right1=syn.getRight();
//        	System.out.print("<tr><td>&nbsp;&nbsp;  "+left.getName()+"&nbsp;&nbsp; &nbsp;   → &nbsp;&nbsp; &nbsp; ");
//        	for(int j=0;j<right.size();j++){
//        		Syntax syn2=(Syntax)right.get(j);
//        		VWord left2=syn2.getLeft();
//        		System.out.print(left2.getName()+"&nbsp;&nbsp; &nbsp;  ");
//        	}
//        	System.out.println("</td></tr>");
        	//System.out.print("("+i+") "+left.getName()+" → ");
        	for(int j=0;j<right1.size();j++){
        		Syntax syn2=(Syntax)right1.get(j);
        		VWord left2=syn2.getLeft();
        		right+=left2.getName();
        		//System.out.print(left2.getName()+"  ");
        	}
        	listright.add(right);
        	//System.out.println();
        }
		LL1 ll1=new LL1(ST_APP.DBMS_SQL_SELECT_EXP);
		ll1.getForecastAnalyzeTable();
		//int vtSize=ll1.vtSize;
		//int vnSize=ll1.vnSize;
		
		
		System.out.println("          ");
		System.out.println("          ");
		System.out.println("          ");
		System.out.println("<table border='1'>");
		System.out.println("<tr>");
		System.out.println("<td>&nbsp;&nbsp;</td>");
		for(int i=0;i<ll1.vtSize;i++){
			System.out.print("<td>"+(String)ll1.vtList.get(i)+"</td>");
			
		}
		System.out.println("</tr>");
		
        for(int i=0;i<ll1.vnSize;i++){
        	System.out.println("<tr>");
        	System.out.print("<td>"+(String)ll1.vnList.get(i)+"</td>");
        	for(int j=0;j<ll1.vtSize;j++){
        		Syntax syntax1=ll1.getForecastAnalyzeTable()[i][j];
        		if(syntax1==null){
        			System.out.print(" <td>&nbsp;&nbsp;</td> ");
        		}else{
        			String temp="";
        			temp+=syntax1.getLeft().getName();
        			List list1=syntax1.getRight();
        			System.out.print("<td>");
        			
        			for(int k=0;k<list1.size();k++){
        				Syntax s=(Syntax)list1.get(k);
        				temp+=s.getLeft().getName();
        				//System.out.print(s.getLeft().getName()+"&nbsp;&nbsp;");
        			}
        			int allrsize=listright.size();
        			for(int jj=0;jj<allrsize;jj++){
        				String strr=(String)listright.get(jj);
        				if(strr.equals(temp)){
        					System.out.print(jj);
        					break;
        				}
        			}
        			
        			System.out.print("</td>");
        			
        		}
        	}
        	System.out.println("</tr>");
        }
        System.out.println("</table>");

	}
	
	

}
