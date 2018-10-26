package com.zyo.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.zyo.bean.Job;
import com.zyo.bean.JobCount;
import com.zyo.bean.Page;

import net.sf.json.JSONArray;

/**
 * 
 * @author Jinjie
 *
 */
public class ClimbingCore {

	/**
	 * 
	 * @param url
	 * @return page
	 * 
	 *  得到页码数量
	 */
	public static int getPages(String url) {
		Document doc = null;
		Element element = null;
		try {
			doc = Jsoup.connect(url).get();
			element = doc.select(".dw_table .dw_tlc .rt").get(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String text = element.text();
		int page = Integer.parseInt(StringUtils.substringAfterLast(text, "/ "));
		System.out.println("page:" + page);
		return page;
	}

	/**
	 * 
	 * @param url
	 * @return pagelist 各城市地区各岗位集合
	 * 
	 */
	public List<Page> getPagelist(String url) {
		List<Page> pagelist = new ArrayList<>();
		//读取配置文件
		ResourceBundle rb = ResourceBundle.getBundle("com.zyo.ui/citytitle"); 
		//分别读取配置文件中城市和岗位信息，以逗号分割转数组
		String[] city = rb.getString("city").split(",");
		String[] names= rb.getString("cityname").split(",");
		String[] gw = rb.getString("title").split(",");
		Page p = null;
		for (int i = 0; i < city.length; i++) {
			for (int j = 0; j < gw.length; j++) {
				//String oneurl = null;
				int page = 0;
				// 解析各城市各岗位的第一个页面地址，得到页码数量
				page = getPages(url.format(url, city[i], gw[j], "1"));
				// 得到各城市各岗位的地址
				String joburl = url.format(url, city[i], gw[j], "%s");
				// 将得到的各城市各岗位信息封装到page对象（此时page应该有 city*gw 个对象）
				if (page != 0) {
					p = new Page();
					p.setCity(names[i]);
					p.setJobTitle(gw[j]);
					p.setNum(page);
					p.setJoburl(joburl);
					pagelist.add(p);
				}
			}
		}
		return pagelist;
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	public boolean parserHtmlWriterJson(Page page,File file,List<JobCount> counlist) {
		if (page != null) {
			// 创建10个线程
			ExecutorService es = Executors.newFixedThreadPool(10);
			// 创建线程安全的集合
			List<Job> list = Collections.synchronizedList(new ArrayList<>());
			// 获取joburl地址
			String str = page.getJoburl();
			// System.out.println("获取各城市岗位的："+str);
			for (int i = 1; i <= page.getNum(); i++) {
				String url = str.format(str, i);
			//	System.out.println("具体解析的" + url);
				es.execute(new HtmlParser(url, list));
			}
			es.shutdown();
			// System.out.println(list.size());
			while (true) {
				if (es.isTerminated()) {
					//每解析完一个城市岗位就把封装到JobCount，然后加入集合
					JobCount jc=new JobCount();
					jc.setCity(page.getCity());
					jc.setTitle(page.getJobTitle());
					jc.setCtsum(list.size());
					counlist.add(jc);
					
					boolean bl = listWriterJson(list,
							new File(file+"/" + page.getCity() + "_" + page.getJobTitle() + ".json"));
					return bl;
				}
			}
		}
		return false;
	}

	/**
	 * 集合写josn
	 * 
	 * @param list
	 * @param file
	 * @return
	 */
	private boolean listWriterJson(List<Job> list, File file) {
		JSONArray jsonArr = JSONArray.fromObject(list);
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(jsonArr.toString());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
