package com.stone.test;

import java.io.*;
class Test
{
    public static void main(String args[]) throws Exception
    {
        File aaa = new File("stone//dosql//data//"); 
        aaa.mkdir();
        MyFileFilter mff = new MyFileFilter(true);
        System.out.println(aaa.getAbsolutePath()+aaa.isDirectory());
        
        File[] fileNames = aaa.listFiles(mff);
        for(int i = 0;i<fileNames.length;i++)
        {
            System.out.println(fileNames[i].getName());
        }
        
    }
}
class MyFileFilter implements FileFilter
{
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
