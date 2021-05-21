package hack.rawfish2d.client.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.Packet7UseEntity;

public class TestUtils {
	
	public static int ivar1 = 0;
	public static int ivar2 = 1;
	public static int ivar3 = 0;
	public static List<Integer> iarr = new ArrayList<Integer>();
	
	public static float fvar1 = 0;
	public static float fvar2 = 0;
	public static float fvar3 = 0;
	public static List<Float> farr = new ArrayList<Float>();
	
	public static double dvar1 = 0;
	public static double dvar2 = 0;
	public static double dvar3 = 0;
	public static List<Double> darr = new ArrayList<Double>();
	
	public static long lvar1 = 0;
	public static long lvar2 = 0;
	public static long lvar3 = 0;
	public static List<Long> larr = new ArrayList<Long>();
	
	public static String svar1 = "";
	public static String svar2 = "";
	public static String svar3 = "";
	public static List<String> sarr = new ArrayList<String>();
	
	public static Object obj1 = null;
	public static Object obj2 = null;
	public static Object obj3 = null;
	public static List<Object> oarr = new ArrayList<Object>();
	
	public static long prevMs = -1L;
	public static TimeHelper time0 = new TimeHelper();
	public static TimeHelper time1 = new TimeHelper();
	public static TimeHelper time2 = new TimeHelper();
	
	public static String make(String s) {
		String s2 = "";
		
		long var1 = 3000;
		
		while (System.currentTimeMillis() - prevMs >= var1 || ivar2 > 0) {
			prevMs = System.currentTimeMillis();
			
			long var2 = 100; 
			while (System.currentTimeMillis() - lvar1 >= var2) {
				lvar1 = System.currentTimeMillis();
				
				if(ivar2 < s.length())
					ivar2++;
				else {
					ivar2 = 0;
				}
				
				String temp;
				if(ivar2 < s.length())
					s2 += s.substring(ivar1, ivar2);
				
				if(s.length() - ivar2 >= 0)
					for(int a = 0; a < s.length() - ivar2; ++a)
						s2 += " ";
				
				svar1 = s2;
			}
			
			return svar1;
		}
		
		return s;
	}
	
	public static String make2(String s) {
		String s2 = "";
		int ivar3_max = 9;
		
		long var1 = 5000;
		
		while (System.currentTimeMillis() - prevMs >= var1 || ivar2 > 0) {
			prevMs = System.currentTimeMillis();
			
			long var2 = 50; 
			while (System.currentTimeMillis() - lvar1 >= var2) {
				lvar1 = System.currentTimeMillis();
				
				if(ivar2 < s.length()) {
					if(ivar3 == ivar3_max)
						ivar2++;
				}
				else {
					ivar2 = 0;
					ivar3 = ivar3_max - 1;
				}
				
				if(ivar1 < s.length() && ivar1 >= 0 && ivar2 < s.length() && ivar2 >= 0 && ivar1 < ivar2)
					s2 += s.substring(ivar1, ivar2);
				
				if(s.length() - ivar2 >= 0) {
					if(ivar3 == 0)
						s2 += "|";
					else if(ivar3 == 1)
						s2 += "/";
					else if(ivar3 == 2)
						s2 += "-";
					else if(ivar3 == 3)
						s2 += "\\";
					else if(ivar3 == 4)
						s2 += "|";
					else if(ivar3 == 5)
						s2 += "/";
					else if(ivar3 == 6)
						s2 += "-";
					else if(ivar3 == 7)
						s2 += "\\";
					else if(ivar3 == 8)
						s2 += "|";
					
					if(ivar3 < ivar3_max)
						ivar3++;
					else
						ivar3 = 0;
					
					for(int a = 1; a < s.length() - ivar2; ++a) {
						s2 += " ";
					}
				}
				
				svar1 = s2;
			}
			
			return svar1;
		}
		
		return s;
	}
	
	public static String replaceAtIndex(String str, int index, String newstr) {
		String out = str;
		
		int index2 = 0;
		for(int a = index; a < index + newstr.length(); ++a) {
			char []arr = newstr.toCharArray();
			out = replaceCharAtIndex(out, a, arr[index2]);
			index2++;
		}
		return out;
	}
	
	public static String replaceCharAtIndex(String str, int index, char newch) {
		String out = "";
		char []arr = str.toCharArray();
		
		arr[index] = newch;
		out = new String(arr);
		return out;
	}
	
	public static char getCharAt(String str, int index) {
		char []arr = str.toCharArray();
		return arr[index];
	}
	
	public static String make3(String s) {
		//svar1
		//ivar1
		if(svar1.length() < 1) {
			svar1 = s;
		}
		else {
			
		}
		return svar1;
	}
}
