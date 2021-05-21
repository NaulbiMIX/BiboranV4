package hack.rawfish2d.client.PathFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import hack.rawfish2d.client.utils.MiscUtils;
import hack.rawfish2d.client.utils.Vector3d;
import hack.rawfish2d.client.utils.Vector3i;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class PathScanner {
	private Vector3i from;
	private Vector3i to;
	private double distance;
	private int maxdistance;
	private World world;
	private int falldist;
	private int steph;
	
	public boolean done;
	
	//просканнированные точки
	private ConcurrentHashMap<Vector3i, Point> points;
	public CopyOnWriteArrayList<Point> path;
	
	public CopyOnWriteArrayList<Point> unchecked = new CopyOnWriteArrayList<Point>();
	public CopyOnWriteArrayList<Point> checked = new CopyOnWriteArrayList<Point>();
	
	public PathScanner(Vector3i from, Vector3i to, World world, int maxdistance, int falldist, int steph) {
		this.points = new ConcurrentHashMap<Vector3i, Point>();
		this.path = new CopyOnWriteArrayList<Point>();
		this.from = from;
		this.to = to;
		this.world = world;
		this.maxdistance = maxdistance;
		this.falldist = falldist;
		this.steph = steph;
		this.done = false;
	}
	
	public Point getPoint(int index) {
		if(index < path.size())
			return path.get(index);
		
		return null;
	}
	
	public int pathSize() {
		return path.size();
	}
	
	public void scan() {
		this.done = false;
		
		unchecked.clear();
		checked.clear();
		
		Point start = new Point(from);
		start.dist = 0;
		unchecked.add(start);
		
		int allowedLoops = 65536;
		int loop = 0;
		Point end = null;
		
		while(!unchecked.isEmpty()) {
			for(Point point : unchecked) {
				if(loop > allowedLoops) {
					unchecked.clear();
					break;
				}
				
				List<Point> nearlist = getNear(point);
				
				unchecked.remove(point);
				checked.add(point);
				
				if(
					point.pos.x == to.x &&
					point.pos.y == to.y &&
					point.pos.z == to.z) {
					unchecked.clear();
					end = point;
					break;
				}
				
				for(Point near : nearlist) {
					if(!contains(unchecked, near) && !contains(checked, near)) {
						unchecked.add(near);
					}
				}
				
				loop++;
			}
		}
		
		points.clear();
		path.clear();
		
		for(Point point : checked) {
			points.put(point.pos, point);
		}
		
		if(end == null)
			return;
		
		Point Next = end.prev;
		path.add(end);
		while(Next != null) {
			//System.out.println("[loop] loop:" + loop);
			
			path.add(Next);
			Next = Next.prev;
		}

		path = reverseList(path);
		
		this.done = true;
	}
	
	public boolean contains(CopyOnWriteArrayList<Point> list, Point find) {
		for(Point point : list) {
			if(
					point.pos.x == find.pos.x &&
					point.pos.y == find.pos.y &&
					point.pos.z == find.pos.z) {
					return true;
				}
		}
		return false;
	}
	
	public static<T> CopyOnWriteArrayList<T> reverseList(List<T> list) {
		CopyOnWriteArrayList<T> reverse = new CopyOnWriteArrayList<T>(list);
		Collections.reverse(reverse);
		return reverse;
	}
	
	private List<Point> getNear(Point point) {
		List<Point> list = new ArrayList<Point>();
		
		if(point.dist >= maxdistance) {
			return list;
		}
		
		Vector3i temp_pos;
		
		for(int yoffset = falldist; yoffset < steph + 1; ++yoffset) {
			temp_pos = new Vector3i(point.pos.x - 1, point.pos.y + yoffset, point.pos.z);
			if(this.canStandOn(temp_pos)) {
				Point near = new Point(temp_pos);
				near.prev = point;
				near.dist = point.dist + 1;
				list.add(near);
			}
			
			temp_pos = new Vector3i(point.pos.x + 1, point.pos.y + yoffset, point.pos.z);
			if(this.canStandOn(temp_pos)) {
				Point near = new Point(temp_pos);
				near.prev = point;
				near.dist = point.dist + 1;
				list.add(near);
			}
			
			temp_pos = new Vector3i(point.pos.x, point.pos.y + yoffset, point.pos.z - 1);
			if(this.canStandOn(temp_pos)) {
				Point near = new Point(temp_pos);
				near.prev = point;
				near.dist = point.dist + 1;
				list.add(near);
			}
			
			temp_pos = new Vector3i(point.pos.x, point.pos.y + yoffset, point.pos.z + 1);
			if(this.canStandOn(temp_pos)) {
				Point near = new Point(temp_pos);
				near.prev = point;
				near.dist = point.dist + 1;
				list.add(near);
			}
		}
		return list;
	}
	
	private int getBlockId(Vector3i pos) {
		return world.getBlockId(pos.x, pos.y, pos.z);
	}
	
	private Block getBlock(Vector3i pos) {
		return Block.blocksList[getBlockId(pos)];
	}
	
	
	//может ли игрок стоять на этом блоке
	private boolean canStandOn(Vector3i pos) {
		Vector3i pos0 = new Vector3i(pos.x, pos.y - 1, pos.z);
		
		//если мы не можем стоять на полу то
		if(isPassable(pos0))
			return false;
		
		//если мы не можем находится над полом то
		Vector3i pos1 = new Vector3i(pos.x, pos.y + 0, pos.z);
		if(!isPassable(pos1))
			return false;
		
		//если мы не можем находится над полом то
		Vector3i pos2 = new Vector3i(pos.x, pos.y + 1, pos.z);
		if(!isPassable(pos2))
			return false;
		
		return true;
	}
	
	//может ли игрок находится в этом блоке
	private boolean isPassable(Vector3i pos) {
		Block block = this.getBlock(pos);
		if(block == null) {
			return true;
		}
		
		boolean ok = false;
		
		if(block.getBlocksMovement(world, pos.x, pos.y, pos.z)) {
			ok = true;
		}
		//block.isCollidable()
		
		return ok;
	}
}