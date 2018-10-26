package com.zyo.ui;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.zyo.bean.JobCount;
import com.zyo.bean.Page;
import com.zyo.core.ClimbingCore;
import com.zyo.core.SumChart;
public class Startup {

	public static void main(String[] args) {
		ClimbingCore core=new ClimbingCore();
		String url = "https://search.51job.com/list/%s,000000,0000,00,9,99,%s,2,%s.html";
		File file=new File("json51job");
		List<Page> list = core.getPagelist(url);
		
		List<JobCount> countlist =new ArrayList<>();
		for (Page p : list) {
			System.out.println("url:"+p.getJoburl());
			core.parserHtmlWriterJson(p,file,countlist);
		}
//		for (JobCount job : countlist) {
//			//输出各城市各岗位的信息
//			System.out.println(job.toString());
//		}
		//调用图表
		SumChart sc=new SumChart();	
		sc.foldLine(countlist);
		sc.BarChart(countlist);
		sc.PieChart(countlist);
		sc.getTable(countlist);
			
		} 
	

		

}
