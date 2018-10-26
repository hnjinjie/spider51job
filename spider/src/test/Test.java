package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


import com.zyo.bean.JobCount;

public class Test {
	

	public static void main(String[] args) {
		List<JobCount> list=new ArrayList<>();
		JobCount jb1=new JobCount();
		jb1.setCity("北京");
		jb1.setTitle("java");
		jb1.setCtsum(12416);
		list.add(jb1);
		
		JobCount jb2=new JobCount();
		jb2.setCity("北京");
		jb2.setTitle("php");
		jb2.setCtsum(2187);
		list.add(jb2);
		
		JobCount jb3=new JobCount();
		jb3.setCity("北京");
		jb3.setTitle("python");
		jb3.setCtsum(6241);
		list.add(jb3);
		
		JobCount j1=new JobCount();
		j1.setCity("上海");
		j1.setTitle("java");
		j1.setCtsum(1346);
		list.add(j1);
		
		JobCount j2=new JobCount();
		j2.setCity("上海");
		j2.setTitle("php");
		j2.setCtsum(87);
		list.add(j2);
		
		JobCount j3=new JobCount();
		j3.setCity("上海");
		j3.setTitle("python");
		j3.setCtsum(2141);
		list.add(j3);
		
		
		
		JobCount j=new JobCount();
		j.setCity("长沙");
		j.setTitle("java");
		j.setCtsum(146);
		list.add(j);
		
		JobCount j21=new JobCount();
		j21.setCity("长沙");
		j21.setTitle("php");
		j21.setCtsum(27);
		list.add(j21);
		
		JobCount j31=new JobCount();
		j31.setCity("长沙");
		j31.setTitle("python");
		j31.setCtsum(41);
		list.add(j31);
		

		
		
		//读取配置文件
		ResourceBundle rb = ResourceBundle.getBundle("com.zyo.ui/citytitle"); 
		//分别读取配置文件中城市和岗位信息，以逗号分割转数组
	//	String[] city = rb.getString("city").split(",");
		String[] names= rb.getString("cityname").split(",");
		String[] gw = rb.getString("title").split(",");
		 List<String> ls=new ArrayList<>();;
		for(int i=0;i<names.length;i++) {
			
				for (JobCount jb : list) {
					
					if((jb.getCity()).equals(names[i])) {
						
						ls.add(jb.getCtsum()+"");
						}
				}
		}
		//System.out.println(ls);
		String str =String.join(",", ls);
	//	System.out.println(str);
		String[] str2=str.split(",");
		
		List<Object[]> lss=getListIntArray(str2, gw.length);
		for (Object[] objects : lss) {
			System.out.println(Arrays.toString(objects));
				
		}
		
		}
	
	private static List<Object[]> getListIntArray(String[] dd, int b) {
		List<Object[]> aa = new ArrayList<>();
		//  取整代表可以拆分的数组个数
		int f = dd.length / b;
		for (int i = 0; i < f; i++) {
			String[] bbb = new String[b];
			for (int j = 0; j < b; j++) {
				bbb[j] = dd[j + i * b]+"";
			}
			aa.add(bbb);
		}
		return aa;
	}
       
	

}
