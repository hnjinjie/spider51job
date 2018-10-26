package com.zyo.ui;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.zyo.bean.JobCount;
import com.zyo.bean.JobUrlPage;
import com.zyo.core.ClimbingCore;
import com.zyo.core.SumChart;
public class Startup {

	public static void main(String[] args) {
		ClimbingCore core=new ClimbingCore();
		String url = "https://search.51job.com/list/%s,000000,0000,00,9,99,%s,2,%s.html";
		File file=new File("json51job");
		List<JobUrlPage> list = core.getPagelist(url);
		
		//每爬去完一个城市岗位就添加数据到countlist集合，用于生成图表
		List<JobCount> countlist =new ArrayList<>();
		for (JobUrlPage p : list) {
			System.out.println("爬取"+p.getCity()+p.getJobTitle()+"岗位的所有页面,"+p.getJoburl());
			core.parserHtmlWriterJson(p,file,countlist);
		}

		//调用图表
		SumChart sc=new SumChart();	
		sc.foldLine(countlist);
		sc.BarChart(countlist);
		sc.PieChart(countlist);
		sc.getTable(countlist);
			
		} 
	

		

}
