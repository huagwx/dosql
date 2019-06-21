package com.stone.test;

import java.util.List;

import com.stone.dosql.lexical.impl.TokenAnalyze;
import com.stone.dosql.util.Token;

public class Cifafenxi {

	public Cifafenxi() {
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TokenAnalyze analyze=new TokenAnalyze("select *,a.*,b,a.c, e+1 from a,b as c where a.d=1 and g='s*!@#$%^&*()sd' and sd in(select * from b) group by id having sum(s)>1 order by id");
        List list=analyze.getTokens();
        for(int i=0;i<list.size();i++){
        	System.out.println(i+"type:"+((Token)list.get(i)).getType()+"  word:"+((Token)list.get(i)).getWord());
        }
	}

}
