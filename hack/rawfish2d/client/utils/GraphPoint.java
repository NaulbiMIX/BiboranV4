package hack.rawfish2d.client.utils;

public class GraphPoint {
	public int x;
	public int y;
	public int frametime;
	
	public GraphPoint(int x, int y, int ft) {
		this.frametime = ft;
		this.x = x;
		this.y = y - ft;
	}
	
	public void draw() {
		
	}
}