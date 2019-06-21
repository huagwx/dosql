/**
 * 
 */
package com.stone.dosql.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
//在DBMS_URL目录下的所有数据库的名称如DBMS_URL="stone//dosql//data//"
public class DbName {
	private String DBMS_URL;
	private List dbList=new ArrayList();
	public DbName(String DBMS_URL) {
		this.DBMS_URL=DBMS_URL;
		this.doAction();
	}
	private void doAction(){
	    {
	        File aaa = new File(this.DBMS_URL); 
	        aaa.mkdir();
	        MyFileFilter mff = new MyFileFilter(true);
	        //System.out.println(aaa.getAbsolutePath()+aaa.isDirectory());
	        
	        File[] fileNames = aaa.listFiles(mff);
	        for(int i = 0;i<fileNames.length;i++)
	        {
	        	this.dbList.add(fileNames[i].getName());
	           // System.out.println(fileNames[i].getName());
	        }
	        
	    }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String getDBMS_URL() {
		return DBMS_URL;
	}
	public void setDBMS_URL(String dbms_url) {
		DBMS_URL = dbms_url;
	}
	public List getDbList() {
		return dbList;
	}
	public void setDbList(List dbList) {
		this.dbList = dbList;
	}
	

}
class MyFileFilter implements FileFilter{
    private boolean isDirectory;
    public MyFileFilter(boolean isDir)
    {
        this.isDirectory = isDir;
    }
    public boolean accept(File pathname)
    {
        if(pathname.isDirectory() == isDirectory)
        {
            return true;
        }
        else
            return false;
    }

}
