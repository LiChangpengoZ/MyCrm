package utils;

import java.util.ArrayList;
import java.util.List;

public class Tools {

	/**
	 * 传入string的1248的和，返回对应的集合
	 * @param sumStr
	 * @return
	 */
	public static List<Integer> seriesStr(String sumStr){
		if(!sumStr.matches("\\d+")) {
			return null;
		}
		double sum=Integer.valueOf(sumStr);
		if(sum<=0) {
			List<Integer> temp=new ArrayList<Integer>();
			temp.add(0);
			return temp;
		}
		int finalSum=(int) sum;				//用于比较
		int finalTemp=0;				//用于比较
		int mathSum=(int) sum;			//减去后的sum
		List<Integer> list=new ArrayList<Integer>();
		a:while(true){
			//每次除以2的是减去数的sum
			sum=mathSum;
			sum=sum/2;
			int temp=1;
			while(true) {
				if(temp>sum) {
					list.add(temp);
					finalTemp=finalTemp+temp;
					mathSum=mathSum-temp;
					if(finalSum==finalTemp) {
						break a;
					}
					break;
				}
				temp*=2;
			}
			
			if(sum<=1) {
				break;
			}
		}
		return list;
	}
	
//	public static void main(String[] args) {
//		String str="100a";
//		System.out.println(seriesStr(str));
//	}
	
}	
	
