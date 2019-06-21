package com.stone.dosql.accessories;

public interface ST_TYPE_ARRAY {
	
	public static final String[] key={
		"SELECT","FROM","WHERE","GROUP","BY",
		"HAVING","ORDER","ALL","DISTINCT","AS",
		"ASC","DESC",".","NOT","NULL",
		"IN","LIKE","BETWEEN","AND","OR",
		"ANY","EXISTS","ESCAPE","COUNT","SUM",
		"AVG","MAX","MIN","IS","SOME"

	};
	public static final String[] oper={
		"<>","<",">","<=",">=",
		"+","-","*","/","(",
		")","="
	};
	public static final String[] bound={
		",","'","\""
	};
	public static final String[] various={
		"DIGITALCONST","IDENT","STRINGCONST"
	};
	//在语义分析要保存的vt
	public static final String[] analyzeWant={
		"<>","<",">","<=",">=",
		"+","-","*","/","=",
		"ALL","DISTINCT","ASC","DESC",".",
		"NOT","NULL","IN","LIKE","BETWEEN",
		"AND","OR","ANY","EXISTS",
		"COUNT","SUM","AVG","MAX","MIN",
		"IS","SOME"
		
		
		
	};
	
}
