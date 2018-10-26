package com.zyo.bean;
/**
 * 各城市岗位信息数据统计，方便把数据给图表
 * @author oyjj
 *
 */
public class JobCount {
	private String city;
	private String title;
	private int ctsum;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCtsum() {
		return ctsum;
	}
	public void setCtsum(int ctsum) {
		this.ctsum = ctsum;
	}
	@Override
	public String toString() {
		return "JobCount [city=" + city + ", title=" + title + ", ctsum=" + ctsum + "]";
	}

	
	
}
