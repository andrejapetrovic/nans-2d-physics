package math;

public class Mat22
{

	public float m00, m01;
	public float m10, m11;

	public Mat22()
	{
	}

	public Mat22( float radians )
	{
		set( radians );
	}

	public Mat22( float a, float b, float c, float d )
	{
		set( a, b, c, d );
	}

	/**
	 * Sets this matrix to a rotation matrix with the given radians.
	 */
	public void set( float radians )
	{
		float cos = (float)StrictMath.cos( radians );
		float sin = (float)StrictMath.sin( radians );

		m00 = cos; m01 = -sin;
		m10 = sin; m11 = cos;
	}

	/**
	 * Sets the values of this matrix.
	 */
	public void set( float a, float b, float c, float d )
	{
		m00 = a;
		m01 = b;
		m10 = c;
		m11 = d;
	}

	/**
	 * Sets this matrix to have the same values as the given matrix.
	 */
	public void set( Mat22 m )
	{
		m00 = m.m00;
		m01 = m.m01;
		m10 = m.m10;
		m11 = m.m11;
	}


	/**
	 * Sets the matrix to it's transpose.
	 */
	public void transpose()
	{
		float t = m01;
		m01 = m10;
		m10 = t;
	}

	/**
	 * Sets out to the transpose of this matrix.
	 */
	public Mat22 transpose( Mat22 out )
	{
		out.m00 = m00;
		out.m01 = m10;
		out.m10 = m01;
		out.m11 = m11;
		return out;
	}

	/**
	 * Returns a new matrix that is the transpose of this matrix.
	 */
	public Mat22 transpose2()
	{
		return transpose( new Mat22() );
	}

	/**
	 * Transforms v by this matrix.
	 */
	public Vec2 mul2( Vec2 v )
	{
		return mul( v.x, v.y, v );
	}

	/**
	 * Sets out to the transformation of v by this matrix.
	 */
	public Vec2 mul( Vec2 v, Vec2 out )
	{
		return mul( v.x, v.y, out );
	}

	/**
	 * Returns a new vector that is the transformation of v by this matrix.
	 */
	public Vec2 mul( Vec2 v )
	{
		return mul( v.x, v.y, new Vec2() );
	}

	/**
	 * Sets out the to transformation of {x,y} by this matrix.
	 */
	public Vec2 mul( float x, float y, Vec2 out )
	{
		out.x = m00 * x + m01 * y;
		out.y = m10 * x + m11 * y;
		return out;
	}

	/**
	 * Multiplies this matrix by x.
	 */
	public void mul2( Mat22 x )
	{
		set(
			m00 * x.m00 + m01 * x.m10, m00 * x.m01 + m01 * x.m11,
			m10 * x.m00 + m11 * x.m10, m10 * x.m01 + m11 * x.m11 );
	}

	/**
	 * Sets out to the multiplication of this matrix and x.
	 */
	public Mat22 mul( Mat22 x, Mat22 out )
	{
		out.m00 = m00 * x.m00 + m01 * x.m10;
		out.m01 = m00 * x.m01 + m01 * x.m11;
		out.m10 = m10 * x.m00 + m11 * x.m10;
		out.m11 = m10 * x.m01 + m11 * x.m11;
		return out;
	}

	/**
	 * Returns a new matrix that is the multiplication of this and x.
	 */
	public Mat22 mul( Mat22 x )
	{
		return mul( x, new Mat22() );
	}

	public String toString() {
		return "[ " + m00 + " " + m01 + System.lineSeparator() + " " + m10 + " " + m11 + " ]";
	}
	
}
