package com.stone.dosql.util;

//对syntax进行克隆

public class SyntaxClone {
	Syntax inputSyntax;
	Syntax outputSyntax=new Syntax();
	public SyntaxClone(Syntax insyntax) {
		this.inputSyntax=insyntax;
	}
	private void doClone(Syntax out,Syntax in){
		try {
			out.getLeft().setSyntaxResult(in.getLeft().getSyntaxResult());
			out.getLeft().setRule(in.getLeft().getRule());
			
			out.getLeft().setName(in.getLeft().getName());
			out.getLeft().setTokenType(in.getLeft().getTokenType());
			out.getLeft().setType(in.getLeft().getType());
			
			int size=in.getRight().size();
			for(int i=0;i<size;i++){
				Syntax sin=(Syntax)in.getRight().get(i);
				Syntax sout=new Syntax();
				out.getRight().add(sout);
				this.doClone(sout, sin);
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
	}
	public Syntax getCloneSyntax(){
		this.doClone(this.outputSyntax, this.inputSyntax);
		return this.outputSyntax;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Syntax s1=new Syntax();
		s1.getLeft().setName("s1Name");
		Syntax s2=new Syntax();
		s2.getLeft().setName("s2Name");
		s2.getRight().add(s1);
		
		System.out.println("before:"+((Syntax)s2.getRight().get(0)).getLeft().getName());
		s1.getLeft().setName("s1Name change");
		System.out.println("after:"+((Syntax)s2.getRight().get(0)).getLeft().getName());
		
		SyntaxClone sClone=new SyntaxClone(s2);
		s2=sClone.getCloneSyntax();
		s1.getLeft().setName("s1Name changeClone");
		System.out.println("after:"+((Syntax)s2.getRight().get(0)).getLeft().getName());
		
		

	}

}
