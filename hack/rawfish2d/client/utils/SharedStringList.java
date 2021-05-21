package hack.rawfish2d.client.utils;

import java.util.concurrent.CopyOnWriteArrayList;

public class SharedStringList {
	private CopyOnWriteArrayList<String> list;
	
	public SharedStringList() {
		list = new CopyOnWriteArrayList<String>();
	}
	
	public void clear() {
		list.clear();
	}
	
	public int size() {
		return list.size();
	}
	
	public boolean contains(String str2) {
		for(String str : list) {
			if(str == str2) {
				return true;
			}
		}
		return false;
	}
	
	public String get(int index) {
		return list.get(index);
	}
	
	public void add(String value) {
		list.add(value);
	}
	
	public void remove(int index) {
		list.remove(index);
	}
	
	public void removeByValue(String str2) {
		for(String str : list) {
			if(str == str2) {
				list.remove(str);
			}
		}
	}
	
	public CopyOnWriteArrayList<String> getList() {
		return list;
	}
}
