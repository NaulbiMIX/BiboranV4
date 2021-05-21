package hack.rawfish2d.client.utils;

public class Vector4f
{
	//public static final Vector4D ZERO = new Vector4D(0, 0, 0, 0);

	//public static final ReusableStack<Vector4D> REUSABLE_STACK = new ReusableStack<>(Vector4D::new);

	public float x, y, z, w;

	public Vector4f()
	{
		this(0, 0, 0, 0);
	}

	public Vector4f(float x, float y, float z, float w)
	{
		set(x, y, z, w);
	}
	
	public Vector4f(double x, double y, double z, double w)
	{
		set((float)x, (float)y, (float)z, (float)w);
	}

	public Vector4f(float v)
	{
		this(v, v, v, v);
	}

	public Vector4f(Vector2f v, float z, float w)
	{
		this(v.x, v.y, z, w);
	}

	public Vector4f(float x, Vector2f v, float w)
	{
		this(x, v.x, v.y, w);
	}

	public Vector4f(float x, float y, Vector2f v)
	{
		this(x, y, v.x, v.y);
	}

	public Vector4f(Vector3f v, float w)
	{
		this(v.x, v.y, v.z, w);
	}

	public Vector4f(float x, Vector3f v)
	{
		this(x, v.x, v.y, v.z);
	}

	public Vector4f(Vector4f v)
	{
		this(v.x, v.y, v.z, v.w);
	}

	@Override
	public int hashCode()
	{
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
		result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vector4f Vector4D = (Vector4f) o;

		return Float.compare(Vector4D.x, x) == 0 &&
			   Float.compare(Vector4D.y, y) == 0 &&
			   Float.compare(Vector4D.z, z) == 0 &&
			   Float.compare(Vector4D.w, w) == 0;
	}

	@Override
	public String toString()
	{
		return "[" + x + ", " + y + ", " + z + ", " + w + "]";
	}

	public Vector4f set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;

		return this;
	}

	public Vector4f add(float x, float y, float z, float w)
	{
		return set(this.x + x, this.y + y, this.z + z, this.w + w);
	}

	public Vector4f copy()
	{
		return new Vector4f(this);
	}

	public Vector4f add(Vector3f v, float w)
	{
		return add(v.x, v.y, v.z, w);
	}

	public Vector4f add(float x, Vector3f v)
	{
		return add(x, v.x, v.y, v.z);
	}

	public Vector4f add(Vector2f v, float z, float w)
	{
		return add(v.x, v.y, z, w);
	}

	public Vector4f add(Vector2f v1, Vector2f v2)
	{
		return add(v1.x, v1.y, v2.x, v2.y);
	}

	public Vector4f add(float x, float y, Vector2f v)
	{
		return add(x, y, v.x, v.y);
	}

	public Vector4f subtract(Vector4f v)
	{
		return add(-v.x, -v.y, -v.z, -v.w);
	}

	public Vector4f subtract(Vector3f v, float w)
	{
		return subtract(v.x, v.y, v.z, w);
	}

	public Vector4f subtract(float x, float y, float z, float w)
	{
		return add(-x, -y, -z, -w);
	}

	public Vector4f subtract(float x, Vector3f v)
	{
		return subtract(x, v.x, v.y, v.z);
	}

	public Vector4f subtract(Vector2f v, float z, float w)
	{
		return subtract(v.x, v.y, z, w);
	}

	public Vector4f subtract(Vector2f v1, Vector2f v2)
	{
		return subtract(v1.x, v1.y, v2.x, v2.y);
	}

	public Vector4f subtract(float x, float y, Vector2f v)
	{
		return subtract(x, y, v.x, v.y);
	}

	public float dot(Vector4f v)
	{
		return x * v.x + y * v.y + z * v.z + w * v.w;
	}

	public Vector4f normalize()
	{
		float l = length();

		if (l == 0 || l == 1)
			return this;

		return set(x / l, y / l, z / l, w / l);
	}

	public Vector4f normalize3()
	{
		float l = (float) Math.sqrt(x * x + y * y + z * z);

		if (l == 0 || l == 1)
			return this;

		return set(x / l, y / l, z / l, w / l);
	}

	public float length()
	{
		return (float) Math.sqrt(lengthSquared());
	}

	public float lengthSquared()
	{
		return x * x + y * y + z * z + w * w;
	}

	public Vector4f negate()
	{
		return set(-x, -y, -z, -w);
	}

	public Vector4f multiply(Vector4f v)
	{
		return scale(v.x, v.y, v.z, v.w);
	}

	public Vector4f scale(float sx, float sy, float sz, float sw)
	{
		return set(x * sx, y * sy, z * sz, w * sw);
	}
/*
	public Vector4D lerp(Vector4D target, float alpha)
	{
		Vector4D temp = Vector4D.REUSABLE_STACK.pop();
		scale(1f - alpha).add(temp.set(target).scale(alpha));
		Vector4D.REUSABLE_STACK.push(temp);

		return this;
	}
*/
	public Vector4f add(Vector4f v)
	{
		return add(v.x, v.y, v.z, v.w);
	}

	public Vector4f scale(float s)
	{
		return scale(s, s, s, s);
	}

	public Vector4f set(Vector4f v)
	{
		return set(v.x, v.y, v.z, v.w);
	}

	public Vector4f set(float v)
	{
		return set(v, v, v, v);
	}

	public Vector4f set(Vector2f v, float z, float w)
	{
		return set(v.x, v.y, z, w);
	}

	public Vector4f set(float x, Vector2f v, float w)
	{
		return set(x, v.x, v.y, w);
	}

	public Vector4f set(float x, float y, Vector2f v)
	{
		return set(x, y, v.x, v.y);
	}

	public Vector4f set(Vector3f v, float w)
	{
		return set(v.x, v.y, v.z, w);
	}

	public Vector4f set(float x, Vector3f v)
	{
		return set(x, v.x, v.y, v.z);
	}
/*
	public Vector4D set(Color color)
	{
		return set(color.r, color.g, color.b, color.a);
	}
   */
}