package com.stone.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.stone.dosql.accessories.ST_APP;
import com.stone.dosql.util.SetWord;

public class TT {
	public String  str;
	public TT(String str) {
		this.str=str;
		// TODO Auto-generated constructor stub
	}
	public String getI(){
		return str;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List list1=new ArrayList();
		list1.add("111");
		list1.add("222");
		List list2=new ArrayList();
		list2.add("333");
		list2.add("44");
		list1.addAll(list2);
		
		for(int i=0;i<list1.size();i++){
			System.out.println(list1.get(i));
		}
		ST_APP.ti=333;
		System.out.println(ST_APP.ti);
		
		//System.out.println(stack.pop());
//		List list1=new ArrayList();
//		TT tt=new TT("testtt");
//		list1.add(tt);
//		TT t2=(TT)list1.get(list1.size()-1);
//		list1.remove(list1.size()-1);
//		System.out.println(list1.size());
//		TT t3=new TT("t3333");
//		list1.add(t3);
//		List list2 =new ArrayList();
//		
//		list2.add(t2);
//		System.out.println(((TT)list2.get(0)).str);
//		tt.str="gaibai";
//		list1.remove(0);
//		System.out.println(((TT)list2.get(0)).str);
		
//		TT t1=new TT();
//		
//		TT t2=new TT();
//		t1.i=t2.i;
//		t2.i=2;
//		
//		System.out.println(t2.i);
//		
//		
//		if(t1.equals(t2)){
//			System.out.println("equal");
//		}else{
//			System.out.println("not");
//		}
//		SetWord setWord=new SetWord();
//		List list1=new ArrayList();
//		SetWord sw=new SetWord();
//		list1.add(sw);
//		list1.add("2");
//		List list2=new ArrayList();
//		for(int i=0;i<list1.size();i++){
////			if(list1.get(i).getClass().equals(class(java.lang.String))){
////				System.out.println("equatlll..");
////			}
//			System.out.println(list1.getClass() +" class:"+list1.get(i).getClass());
//		}
//		list2=list1;
//		
//		sw.setName("swname");
//		
//		setWord.setName("name1");
//		setWord.setSet(list1);
//		System.out.println(((SetWord)setWord.getSet().get(0)).getName());
//		list1.set(1, sw);
//		System.out.println(setWord.getSet().get(1));
		System.out.println("......");
		String str="abccd";
		int leng=str.length();
		str=str.substring(1, leng-1);
		System.out.println(str);
		String t1="1.12df";
		if(t1.toLowerCase().equals(t1.toUpperCase())){
			System.out.println("1..1");
		}
		double i=1;
		double ii=1.00;
		if(i==ii){
			System.out.println(i);
		}
		int []a=new int[2];
		System.out.println(a[0]);
		
		String str1="'''";
		System.out.println("str1:"+str1);
		str1=str1.replaceAll("sdf", "'");
		str1=str1.replaceAll("'", "sdf");
		System.out.println("str1:"+str1);
		
		

	}

}
