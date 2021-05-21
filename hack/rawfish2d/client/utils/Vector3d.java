package hack.rawfish2d.client.utils;

public final class Vector3d {

	public double x;
	public double y;
	public double z;
	
	public Vector3d() {}
	
	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3d(float x, float y, float z) {
		this.x = (double)x;
		this.y = (double)y;
		this.z = (double)z;
	}
	
	public Vector3d add(final Vector3d v) {
		return new Vector3d(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector3d sub(final Vector3d v) {
		return new Vector3d(x - v.x, y - v.y, z - v.z);
	}

	public Vector3d mul(final Vector3d v) {
		return new Vector3d(x * v.x, y * v.y, z * v.z);
	}
	
	public Vector3d div(final Vector3d v) {
		return new Vector3d(x / v.x, y / v.y, z / v.z);
	}
	
	public double Dot(final Vector3d v) {
		return (x * v.x + y * v.y + z * v.z);
	}
	
	public Vector3d Cross(final Vector3d v) {
		return new Vector3d(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}
	
	public double Length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public double LengthSq() {
		return (x * x + y * y + z * z);
	}

	public double Length2D() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public double Length2DSq() {
		return (x * x + y * y);
	}

	public double DistTo(final Vector3d v) {
		return this.sub(v).Length();
	}

	public double DistToSqr(final Vector3d v) {
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
		double len = Length();
		
		this.x /= len;
		this.y /= len;
		this.z /= len;
	}

	public double Distance(Vector3d v) {
		return sub(v).Length();
	}
}