package hack.rawfish2d.client.utils;

public final class Vector3f {

	public float x;
	public float y;
	public float z;
	
	public Vector3f() {}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(double x, double y, double z) {
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
	}
	
	public Vector3f add(final Vector3f v) {
		return new Vector3f(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector3f sub(final Vector3f v) {
		return new Vector3f(x - v.x, y - v.y, z - v.z);
	}

	public Vector3f mul(final Vector3f v) {
		return new Vector3f(x * v.x, y * v.y, z * v.z);
	}
	
	public Vector3f div(final Vector3f v) {
		return new Vector3f(x / v.x, y / v.y, z / v.z);
	}
	
	public float Dot(final Vector3f v) {
		return (x * v.x + y * v.y + z * v.z);
	}
	
	public Vector3f Cross(final Vector3f v) {
		return new Vector3f(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}
	
	public float Length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public float LengthSq() {
		return (x * x + y * y + z * z);
	}

	public float Length2D() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float Length2DSq() {
		return (x * x + y * y);
	}

	public float DistTo(final Vector3f v) {
		return this.sub(v).Length();
	}

	public float DistToSqr(final Vector3f v) {
		return this.sub(v).LengthSq();
	}
	
	public void normalizeAngles()
	{
		if (this.x != this.x)
			this.x = 0;
		if (this.y != this.y)
			this.y = 0;
		if (this.z != this.z)
			this.z = 0;

		if (this.x > 89.f)
			this.x = 89.f;
		if (this.x < -89.f)
			this.x = -89.f;

		while (this.y > 180)
			this.y -= 360;
		while (this.y <= -180)
			this.y += 360;

		if (this.y > 180.f)
			this.y = 180.f;
		if (this.y < -180.f)
			this.y = -180.f;

		this.z = 0;
	}
	
	public void normalize()
	{
		float len = Length();
		
		this.x /= len;
		this.y /= len;
		this.z /= len;
	}

	public float Distance(Vector3f v) {
		return sub(v).Length();
	}
}