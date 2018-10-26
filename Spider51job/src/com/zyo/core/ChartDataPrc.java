package com.zyo.core;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
/**
 * 图表数据处理类
 * @author oyjj
 *
 */
public class ChartDataPrc {
	/**
	 * 一个数组拆分成多个数组
	 * 
	 * @param str
	 * @param b
	 * @return
	 */
	public static List<String[]> getListIntArray(String[] str, int b) {
		List<String[]> lb = new ArrayList<>();
		// 取整代表可以拆分的数组个数
		int f = str.length / b;
		for (int i = 0; i < f; i++) {
			String[] bbb = new String[b];
			for (int j = 0; j < b; j++) {
				bbb[j] = str[j + i * b] + "";
			}
			lb.add(bbb);
		}
		return lb;
	}

	/**
	 * 重写的 toString
	 * 
	 * @param a
	 * @return
	 */
	public static String toString(Object[] a) {
		if (a == null)
			return "null";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append("");
		for (int i = 0;; i++) {
			b.append(String.valueOf(a[i]));
			if (i == iMax)
				return b.append("").toString();
			b.append(",");
		}
	}

	/**
	 * 将JFrame窗体中的内容以图片形式保存
	 * 
	 * @param jf
	 */
	public static void savePic(JFrame jf) {
		// 得到窗口内容面板
		Container content = jf.getContentPane();
		// 创建缓冲图片对象
		BufferedImage img = new BufferedImage(jf.getWidth(), jf.getHeight(), BufferedImage.TYPE_INT_RGB);
		// 得到图形对象
		Graphics2D g2d = img.createGraphics();
		// 将窗口内容面板输出到图形对象中
		content.printAll(g2d);
		// 保存为图片
		File f = new File("chat/biaoge.jpg");
		try {
			ImageIO.write(img, "jpg", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 释放图形对象
		g2d.dispose();
	}

}
