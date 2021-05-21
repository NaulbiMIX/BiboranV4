package hack.rawfish2d.client.utils;

public final class Vector2f {

	public float x;
	public float y;
	
	public Vector2f() {}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f sub(Vector2f vec) {
		return new Vector2f(x - vec.x, y - vec.y);
	}
	
	public Vector2f add(Vector2f vec) {
		return new Vector2f(x + vec.x, y + vec.y);
	}

	public Vector2f mul(Vector2f v) {
		return new Vector2f(x * v.x, y * v.y);
	}
	
	public Vector2f div(Vector2f v) {
		return new Vector2f(x / v.x, y / v.y);
	}
	
	public double Length() {
		return Math.sqrt(x * x + y * y);
	}

	public float LengthSq() {
		return (x * x + y * y);
	}
	
	public double Dot(Vector2f v) {
		return (x * v.x + y * v.y);
   }
}