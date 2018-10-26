package com.zyo.core;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zyo.bean.Job;

/**
 * 解析HTML
 * @author oyjj
 *
 */
public class HtmlParser implements Runnable {
	Document doc = null;
	private String url;
	private List<Job> jobs;

	public HtmlParser(String url, List<Job> jobs) {
		this.url = url;
		this.jobs = jobs;

	}

	@Override
	public void run() {
		parser();
	}

	private void parser() {
		try {
			doc = Jsoup.connect(url).timeout(50000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements es = doc.select(".dw_table [class=el]");
		if (!es.isEmpty()){
			for (Element e : es) {
				String position = e.select(".t1  a").text();// 职位信息
				String position_href = e.select(".t1  a").attr("href");// 职位链接  暂时未封装
				String companyInfo = e.select(".t2 a").attr("title");// 公司信息
				String companyInfo_href = e.select(".t2 a").attr("href");// 公司链接	 暂时未封装
				String address = e.select(".t3").text();// 工作地点
				String salary = e.select(".t4").text();// 薪资
				String fbdate = e.select(".t5").text();// 发布时间

//				System.out.println("___________________________________");
//				System.out.println(position);
//				System.out.println(position_href);
//				System.out.println(companyInfo);
//				System.out.println(companyInfo_href);
//				System.out.println(address);
//				System.out.println(salary);
//				System.out.println(fbdate);

				Job job = new Job();
				job.setPosition(position);
				job.setCompanyInfo(companyInfo);
				job.setAddress(address);
				job.setSalary(salary);
				job.setFbdate(fbdate);

				jobs.add(job);

			}
		}

	}
}
