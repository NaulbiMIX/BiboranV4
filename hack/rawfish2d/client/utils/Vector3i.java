/*
 * Decompiled with CFR 0.144.
 */
package hack.rawfish2d.client.utils;

public class Vector3i {
    public int x;
    public int y;
    public int z;

    public Vector3i() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i add(Vector3i vec) {
        return this.add(vec.x, vec.y, vec.z);
    }

    public Vector3i add(int i) {
        return this.add(i, i, i);
    }

    public Vector3i add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3i sub(Vector3i vec) {
        return this.sub(vec.x, vec.y, vec.z);
    }

    public Vector3i sub(int i) {
        return this.sub(i, i, i);
    }

    public Vector3i sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
	public int Dot(final Vector3i v) {
		return (x * v.x + y * v.y + z * v.z);
	}
	
	public Vector3i Cross(final Vector3i v) {
		return new Vector3i(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}
    
	public double Length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	public int LengthSq() {
		return (x * x + y * y + z * z);
	}

	public double Length2D() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public int Length2DSq() {
		return (x * x + y * y);
	}

	public double DistTo(final Vector3i v) {
		return this.sub(v).Length();
	}

	public int DistToSqr(final Vector3i v) {
		return this.sub(v).LengthSq();
	}

    public Vector3i clone() {
        return new Vector3i(this.x, this.y, this.z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vector3i)) {
            return false;
        }
        Vector3i o = (Vector3i)obj;
        return this.x == o.x && this.y == o.y && this.z == o.z;
    }

    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.x;
        hash = 71 * hash + this.y;
        hash = 71 * hash + this.z;
        return hash;
    }
}

