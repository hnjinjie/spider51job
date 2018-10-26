package com.zyo.core;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.zyo.bean.JobCount;
/**
 * 统计图表类
 * @author oyjj
 *
 */
public class SumChart extends JFrame {
	/**
	 * 绘制条形图
	 * 
	 * @return
	 */
	public void BarChart(List<JobCount> counlist) {
		JFrame frame = new JFrame("条形统计图");
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		// 遍历JobCount集合取出值添加到条形图中
		for (JobCount jc : counlist) {
			dataset.addValue(jc.getCtsum(), jc.getCity(), jc.getTitle());
		}
		ChartPanel frame1;
		JFreeChart chart = ChartFactory.createBarChart3D("各城市岗位数量", // 图表标题
				"", // 目录轴的显示标签
				"数量", // 数值轴的显示标签
				dataset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, // 是否显示图例(对于简单的柱状图必须是false)
				false, // 是否生成工具
				false // 是否生成URL链接
		);
		CategoryPlot plot = chart.getCategoryPlot();// 获取图表区域对象
		CategoryAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
		domainAxis.setLabelFont(new Font("楷体", Font.BOLD, 14)); // 水平底部标题
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // 垂直标题
		ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
		chart.getTitle().setFont(new Font("行楷", Font.BOLD, 20));// 设置标题字体
		// 在磁盘目录下生成文件
		File file = new File("chat/BarChart.jpeg");
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame1 = new ChartPanel(chart, true);
		frame.add(frame1);
		frame.setBounds(0, 100, 800, 600);// (左，上，宽，高)
		// 将窗口显示出来
		frame.setVisible(true);
	}

	/**
	 * 表格
	 * 
	 * @param counlist
	 * @return
	 */
	public void getTable(List<JobCount> counlist) {
		JFrame jf = new JFrame("城市中各岗位需求数量");
		JTable table = null;

		// 读取配置文件
		ResourceBundle rb = ResourceBundle.getBundle("com.zyo.ui/citytitle");
		// 分别读取配置文件中城市和岗位信息，以逗号分割转数组
		// String[] city = rb.getString("city").split(",");
		String[] names = rb.getString("cityname").split(",");
		String[] gw = rb.getString("title").split(",");
		List<String> ls = new ArrayList<>();
		for (int i = 0; i < names.length; i++) {
			ls.add(names[i]);
			for (JobCount jb : counlist) {
				if ((jb.getCity()).equals(names[i])) {
					ls.add(jb.getCtsum() + "");
				}
			}
		}
		String str = String.join(",", ls);
		String[] str2 = str.split(",");
		// 定义二维数组作为表格数据
		List<String[]> lss = ChartDataPrc.getListIntArray(str2, gw.length + 1);
		// 城市行，岗位列+1
		Object[][] tableData = new Object[names.length][gw.length + 1];

		for (int i = 0; i < lss.size(); i++) {
			String s = ChartDataPrc.toString(lss.get(i));
			// System.out.println(s);
			String[] str1 = s.split(",");
			for (int j = 0; j < gw.length + 1; j++) {
				tableData[i][j] = str1[j];
			}
		}
		// 先把列标题数组转为list，然后往list集合中第一个位置加入信息，再转为数组
		List<String> sname = new ArrayList<String>();
		Collections.addAll(sname, gw);
		sname.add(0, "城市|岗位");
		String[] strArray = sname.toArray(new String[sname.size()]);
		// 定义一维数据作为列标题
		Object[] columnTitle = strArray;
		// 以二维数组和一维数组来创建一个JTable对象
		table = new JTable(tableData, columnTitle);
		// 将JTable对象放在JScrollPane中，并将该JScrollPane放在窗口中显示出来
		jf.add(new JScrollPane(table));

		jf.pack();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(0, 100, 800, 600);// (左，上，宽，高)
		jf.setVisible(true);
		// 将窗体上的内容以图片形式保存
		ChartDataPrc.savePic(jf);

	}

	/**
	 * 饼型图
	 * 岗位数量及占比
	 * 
	 * 
	 * @param counlist
	 */
	public void PieChart(List<JobCount> counlist) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		// 读取配置文件
		ResourceBundle rb = ResourceBundle.getBundle("com.zyo.ui/citytitle");
		// 分别读取配置文件中城市和岗位信息，以逗号分割转数组
		String[] city = rb.getString("city").split(",");
		// String[] names = rb.getString("cityname").split(",");
		String[] gw = rb.getString("title").split(",");
		List<String> ls = new ArrayList<>();

		for (int i = 0; i < gw.length; i++) {
			for (JobCount jb : counlist) {
				if ((jb.getTitle()).equals(gw[i])) {
					ls.add(String.valueOf(jb.getCtsum()));
				}
			}
		}
		// 将集合转以逗号分割的字符串
		String str = String.join(",", ls);
		String[] str2 = str.split(",");
		// 定义一个int数组长度为岗位的长度，用于存放各地区中各岗位数量的和（总数量）
		int[] titleSum = new int[gw.length];
		// 将字符串数字拆分成多个数组
		List<String[]> lss = ChartDataPrc.getListIntArray(str2, city.length);
		for (int i = 0; i < lss.size(); i++) {
			String s = ChartDataPrc.toString(lss.get(i));
			String[] str1 = s.split(",");
			int sum1 = 0;
			for (int m = 0; m < str1.length; m++) {
				// 将str1数组中的数据累加
				sum1 += Integer.parseInt(str1[m]);
			}
			titleSum[i] = sum1;
		}
		// 给dataset设值
		for (int i = 0; i < gw.length; i++) {
			dataset.setValue(gw[i], titleSum[i]);
		}
		/*如果只是想得到每个岗位在当前城市中的数量及占比图，
		那么只需要遍历集合每遍历一次变保存一张城市饼图，//dataset.setValue(gw[i], titleSum[i]);
		也就是每个城市每个岗位都生成一张图表，这种饼图意义不大
		*/
		
		JFreeChart chart = ChartFactory.createPieChart3D("岗位数量及占比", // 主标题的名称
				dataset, // 图标显示的数据集合
				true, // 是否显示子标题
				true, // 是否生成提示的标签
				true); // 是否生成URL链接
		// 处理主标题的乱码
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
		// 处理子标题乱码
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
		// 获取图表区域对象
		PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
		// 处理图像上的乱码
		categoryPlot.setLabelFont(new Font("宋体", Font.BOLD, 15));
		// 设置图形的生成格式为（上海 2 （10%））
		String format = "{0} {1} ({2})";
		categoryPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));

		// 在磁盘目录下生成图片
		File file = new File("chat/PieChart.jpg");
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 使用ChartFrame对象显示图像
		ChartFrame frame = new ChartFrame("饼型图", chart);
		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * 折线图
	 * 
	 * @param counlist
	 */
	public void foldLine(List<JobCount> counlist) {
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
		mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
		mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
		ChartFactory.setChartTheme(mChartTheme);
		DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
		for (JobCount jb : counlist) {
			mDataset.addValue(jb.getCtsum(), jb.getTitle(), jb.getCity());
		}
		JFreeChart mChart = ChartFactory.createLineChart("折线图", "", "数量", mDataset, PlotOrientation.VERTICAL, true,
				true, false);
		CategoryPlot mPlot = (CategoryPlot) mChart.getPlot();
		mPlot.setBackgroundPaint(Color.LIGHT_GRAY);
		mPlot.setRangeGridlinePaint(Color.BLUE);// 背景底部横虚线
		mPlot.setOutlinePaint(Color.RED);// 边界线
		// 在磁盘目录下生成图片
		File file = new File("chat/foldLine.jpeg");
		try {
			ChartUtilities.saveChartAsJPEG(file, mChart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
		mChartFrame.pack();
		mChartFrame.setVisible(true);
	}

	
}
