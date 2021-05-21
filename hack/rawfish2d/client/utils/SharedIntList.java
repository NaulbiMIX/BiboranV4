package hack.rawfish2d.client.utils;

import java.util.concurrent.CopyOnWriteArrayList;

public class SharedIntList {
	private CopyOnWriteArrayList<Integer> list;
	
	public SharedIntList() {
		list = new CopyOnWriteArrayList<Integer>();
	}
	
	public void clear() {
		list.clear();
	}
	
	public int size() {
		return list.size();
	}
	
	public boolean contains(int value) {
		for(Integer in : list) {
			if(in.intValue() == value) {
				return true;
			}
		}
		return false;
	}
	
	public int get(int index) {
		return list.get(index).intValue();
	}
	
	public void add(int value) {
		list.add(new Integer(value));
	}
	
	public void remove(int index) {
		list.remove(index);
	}
	
	public void removeByValue(int value) {
		for(Integer in : list) {
			if(in.intValue() == value) {
				list.remove(in);
			}
		}
	}
	
	public CopyOnWriteArrayList<Integer> getList() {
		return list;
	}
}
