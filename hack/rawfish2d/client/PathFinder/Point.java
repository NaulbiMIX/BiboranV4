package hack.rawfish2d.client.PathFinder;

import hack.rawfish2d.client.utils.Vector3i;

public class Point {
	public Vector3i pos;
	public int dist;
	public Point prev;
	
	public Point(Vector3i pos) {
		this.pos = pos;
		this.dist = 0;
		this.prev = null;
	}
}
