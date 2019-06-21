package com.stone.dosql.codegenerator.impl;

import java.util.ArrayList;
import java.util.List;

import com.stone.dosql.codegenerator.from.impl.FromDo;
import com.stone.dosql.codegenerator.groupby.impl.GroupByDo;
import com.stone.dosql.codegenerator.having.impl.HavingGroupByDo;
import com.stone.dosql.codegenerator.having.impl.HavingWhereDo;
import com.stone.dosql.codegenerator.orderby.impl.OrderByDo;
import com.stone.dosql.codegenerator.select.impl.SelectDo;
import com.stone.dosql.codegenerator.selecthasgroup.impl.SelectGroupDo;
import com.stone.dosql.codegenerator.where.impl.WhereDo;
//从语义分析中得到所有的中间代码

//取得所有的中间代码
//得到每一个子查询
//取出每一个子查询，进行fromdo 得到要查找的table信息和记录
//进行wheredo 分析wheredo 是对每一个table的查找优化，得到初步的resultset
//对table的信息要进行复制
//得到的resultset进入groupby
//进入havingdo 
//进入orderby得到的结果
//进入selectdo得到结果
//得到的结果保存 在后面的查询有的是要用到的 潜入子查询


public class DoSelectAction {
	//保存前面的子查询后的每一子查询的resultsetTemp
	private List resultSetTempList;
	
	//在这次的查询后得到的resultSetTemp返回到前面中
	private ResultSetTemp resultSetTemp=new ResultSetTemp();
	
	
	//某一个查询的所有中间代码
	private List allMiddleCodeListOfOneQuery;
	
	//数据库文件的地址
	private String dbUrl;
	
	//List allMiddleCodeList 某一个查询的中间代码,List resultSetTempList前面查询的resultSetTemp的List
	public DoSelectAction(List allMiddleCodeListOfOneQuery,List resultSetTempList,String dbUrl) {
		//allMiddleCodeList为代码生成的所有中间代码
		//获得所有的子查询是从子查询到最终的查询
		this.allMiddleCodeListOfOneQuery=allMiddleCodeListOfOneQuery;
		this.resultSetTempList=resultSetTempList;
		this.dbUrl=dbUrl;
		//进行操作得到resultsetTemp结果
		this.doAction();
	}
	public ResultSetTemp getResultSetTemp(){
		return this.resultSetTemp;
	}

	//进行select的中间代码处理
	private void doAction(){

		//from
		//进行fromdo 得到查找的tablelist 包括记录
		FromDo fromDo=new FromDo(this.allMiddleCodeListOfOneQuery,this.dbUrl);
		//得到查找的所有table的信息和记录
		List recordTableList=fromDo.getTableList();
		//System.out.println("doselectAction fromDo的table数量是:"+recordTableList.size());
	
		
		//where
		//传入三个参数 某一个查询的中间代码，fromdo得到的recordtablelist，前面得到的resultSetTemp的list
		WhereDo whereDo=new WhereDo(this.allMiddleCodeListOfOneQuery,recordTableList,this.resultSetTempList);
		
		ResultSetInit resultSetWhere=whereDo.getResultSet();
		
//		//测试
//		List recordList=resultSetWhere.getRecordList();
//		int size=recordList.size();
//		System.out.println("doSelectAction测试where的resultSet");
//		System.out.println("resultSetWhereColumnSize:"+resultSetWhere.getColumnsList().size());
//		for(int i=0;i<size;i++){
//			List listTemp=(List)recordList.get(i);
//			int tempSize=listTemp.size();
//			for(int j=0;j<tempSize;j++){
//				System.out.print(listTemp.get(j)+"  ");
//			}
//			System.out.println();
//		}
		//order by
		OrderByDo orderByDo=new OrderByDo(this.allMiddleCodeListOfOneQuery,resultSetWhere);
		resultSetWhere=orderByDo.getOrderByResultSetInit();
//		recordList=resultSetWhere.getRecordList();
//		size=recordList.size();
//		System.out.println("doSelectAction测试orderby的resultSet");
//		System.out.println("resultSetorderbyColumnSize:"+resultSetWhere.getColumnsList().size());
//		for(int i=0;i<size;i++){
//			List listTemp=(List)recordList.get(i);
//			int tempSize=listTemp.size();
//			for(int j=0;j<tempSize;j++){
//				System.out.print(listTemp.get(j)+"  ");
//			}
//			System.out.println();
//		}
			
		
		
		//group by
		GroupByDo groupByDo=new GroupByDo(this.allMiddleCodeListOfOneQuery,resultSetWhere);
		List groupbyColumnList=groupByDo.getGroupByColumnList();
	//	System.out.println("groupby columnSize:"+groupbyColumnList.size());
		
		//test
//		int sizegp=groupbyColumnList.size();
//		for(int i=0;i<sizegp;i++){
//			System.out.print((String)groupbyColumnList.get(i)+"  ");
//		}
//		System.out.println("");
		
		//having
		//如果groupby为空的时候
		if(groupbyColumnList.size()==0){
//			System.out.println("groupbyColumnList.size()==0");
			//先做having 在select
//			if(whereDo.getWhereIsNull()){
//				System.out.println("122222222");
//				HavingOnlyDo havingOnlyDo=new HavingOnlyDo(this.allMiddleCodeListOfOneQuery,recordTableList,this.resultSetTempList);
//				resultSetWhere=havingOnlyDo.getResultSet();
//				
//			    orderByDo=new OrderByDo(this.allMiddleCodeListOfOneQuery,resultSetWhere);
//				resultSetWhere=orderByDo.getOrderByResultSetInit();
//				recordList=resultSetWhere.getRecordList();
//				size=recordList.size();
//				System.out.println("doSelectAction测试orderby的resultSet");
//				System.out.println("resultSetorderbyColumnSize:"+resultSetWhere.getColumnsList().size());
//				for(int i=0;i<size;i++){
//					List listTemp=(List)recordList.get(i);
//					int tempSize=listTemp.size();
//					for(int j=0;j<tempSize;j++){
//						System.out.print(listTemp.get(j)+"  ");
//					}
//					System.out.println();
//				}
//				
//				System.out.println("resultSetWhere.getRecordList().size():"+resultSetWhere.getRecordList().size());
//			
//			}else{
			//	System.out.println("3222222");
				HavingWhereDo havingWhereDo=new HavingWhereDo(this.allMiddleCodeListOfOneQuery,resultSetWhere,this.resultSetTempList,recordTableList);
				resultSetWhere=havingWhereDo.getResultSet();
		//	}
	
			SelectDo selectDo=new SelectDo(this.allMiddleCodeListOfOneQuery,resultSetWhere);
			
			//System.out.println("sdfsdfhwx..");
			selectDo.getResultSetTemp();
			this.resultSetTemp=selectDo.getResultSetTemp();
			
		}else{//如果groupby不为空的时候
			//先做groupby 再做select 在having
			//List groupByTempList=groupByDo.getGroupByTempList();
			//List allMiddleCodeListOfOneQuery,ResultSetInit resultSetWhere,List groupByTempList
            SelectGroupDo selectGroupDo=new SelectGroupDo(allMiddleCodeListOfOneQuery,resultSetWhere);
            this.resultSetTemp=selectGroupDo.getResultSetTemp();
            HavingGroupByDo havingGroupByDo =new HavingGroupByDo(allMiddleCodeListOfOneQuery, resultSetTemp,resultSetTempList);
            this.resultSetTemp=havingGroupByDo.getResultSetTemp();
            //开始做having
			System.out.println("groupbyColumnList.size()>0");
			
			//System.out.println(selectGroupDo.getResultSetTemp().getResultSet().getRecordList().size());
			//System.out.println(resultSetWhere.getRecordList().size()+"  size");
		}
		
		

			
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
