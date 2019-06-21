package com.stone.dosql.util;

import com.stone.dosql.accessories.ST_TYPE;

public class Scalar_exp_one {
	//在select中的某一个表达式的类型 和别名取值
	private Syntax scalar_exp_one;
	private String scalar_name="";
	private String param="";
	public Scalar_exp_one(Syntax scalar_exp_one,String param) {
		this.scalar_exp_one=scalar_exp_one;
		this.param=param;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Syntax getScalar_exp_one() {
		return scalar_exp_one;
	}

	public void setScalar_exp_one(Syntax scalar_exp_one) {
		this.scalar_exp_one = scalar_exp_one;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}


	public String getScalar_name() {
		this.scalar_name="";
		this.doScalarAction();
		return scalar_name;
	}
	private void doScalarAction(){
		this.getTree(this.scalar_exp_one);
	}
	private  void getTree(Syntax syntax){
		int i=syntax.getRight().size();
		if(i==0){
			if(syntax.getLeft().getType()!=ST_TYPE.ST_NULL_VT){
				this.scalar_name+=syntax.getLeft().getName();
			}
			
		}else {
			for(int j=0;j<i;j++){
				Syntax s=(Syntax)syntax.getRight().get(j);
				getTree(s);
			}
		}
		
	}


	public void setScalar_name(String scalar_name) {
		this.scalar_name = scalar_name;
	}

}
