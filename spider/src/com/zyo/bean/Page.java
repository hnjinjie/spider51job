package com.zyo.bean;
/**
 * 城市岗位URL
 * 各城市各岗位的第一个页面的信息
 * 用于描述各城市各岗位信息
 * 
 * @author oyjj
 *
 */
public class Page {
	private String city;//城市
	private String jobTitle;//岗位名称
	private int page;//页面数量
	private String joburl;//地址
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public int getNum() {
		return page;
	}
	public void setNum(int page) {
		this.page = page;
	}
	public String getJoburl() {
		return joburl;
	}
	public void setJoburl(String joburl) {
		this.joburl = joburl;
	}
	@Override
	public String toString() {
		return "Info [城市=" + city + ", 岗位=" + jobTitle + ", 数量=" + page + ", joburl=" + joburl + "]";
	}
	
	

}
