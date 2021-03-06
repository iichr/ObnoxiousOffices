package game.util;

import java.io.Serializable;

/**
 * 
 * @author Atanas K. Harbaliev. Created on 14.02.2017
 *
 * @param <L>
 *            left int of pair
 * @param <R>
 *            right int of pair
 */
public class Pair<L, R> implements Serializable{

	private static final long serialVersionUID = 1L;

	// just for testing
	@Override
	public String toString() {
		return "Pair [l=" + l + ", r=" + r + "]";
	}

	private L l; // left int
	private R r; // right int

	// constructor
	public Pair(L l, R r) {
		this.l = l;
		this.r = r;
	}

	// get methods
	public L getL() {
		return l;
	}

	public R getR() {
		return r;
	}

	// set methods
	public void setL(L l) {
		this.l = l;
	}

	public void setR(R r) {
		this.r = r;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Pair<?, ?> pair = (Pair<?, ?>) o;

		if (l != null ? !l.equals(pair.l) : pair.l != null) return false;
		return r != null ? r.equals(pair.r) : pair.r == null;
	}

	@Override
	public int hashCode() {
		int result = l != null ? l.hashCode() : 0;
		result = 31 * result + (r != null ? r.hashCode() : 0);
		return result;
	}
}
