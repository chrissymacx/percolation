/**
 * @authors Chrissy MacPherson and Jo Phillips
 * Percolation contains a constructor for a Percolation system object on an NxN
 * grid, private methods ufConnect() and validate(), and public methods
 * validate(), isOpen(), isFull(), and percolates(). These are used
 * for opening connections and checking if the system percolates. It uses
 * libraries from algs4 to create two WeightedQuickUnionUF union-find 
 * objects to find if the system percolates. Two are used to insure
 * that there is no "backwash" once the system has percolated.
 */

package a01_percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int N;
	private boolean[][] boolArr;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf2;
	private int topVirtual;
	private int bottomVirtual;

	/**
	 * Creates the percolation system on an NxN grid. N is used to create the
	 * WeightedQuickUnionUF objects, a boolean array for tracking which boxes are
	 * open, as well as top and bottom virtual points.
	 * 
	 * @param N
	 */
	public Percolation(int N) {
		this.N = N;
		boolArr = new boolean[N][N];
		uf = new WeightedQuickUnionUF(N * N + 2);
		uf2 = new WeightedQuickUnionUF(N * N + 2);
		topVirtual = N * N;
		bottomVirtual = N * N + 1;
	}

	/**
	 * ufConnect() creates unions in both UF objects. This will be important for
	 * finding if the system percolates while also avoiding backwash.
	 * 
	 * @param i
	 * @param j
	 */
	private void ufConnect(int i, int j) {
		uf.union(i, j);
		uf2.union(i, j);
	}

	/**
	 * validate() determines whether the indices given are valid -- if the indices
	 * are not between 0 and N-1, an error is thrown.
	 * 
	 * @param i
	 * @param j
	 */
	private void validate(int i, int j) {
		if (i >= N || i < 0) {
			throw new IllegalArgumentException("The row number " + i + " must be between 0 and " + (N - 1));
		}
		if (j >= N || j < 0) {
			throw new IllegalArgumentException("The column number " + j + " must be between 0 and " + (N - 1));
		}
	}

	/**
	 * open() will open a particular box within the grid. It will then check if
	 * there are any open boxes nearby and create a connection between those two
	 * boxes within uf and uf2. If the box is on the N-1 row, a connection between
	 * that box and bottomVirtual will be created, but only in uf2. There must only
	 * be a connection between bottomVirtual and the box within uf if there is
	 * already a connection within uf2 for topVirtual and bottomVirtual. This avoids
	 * backwash while maintaining linear time.
	 * 
	 * @param i
	 * @param j
	 */
	public void open(int i, int j) {
		validate(i, j);
		boolArr[i][j] = true;

		if (i == 0) {
			ufConnect(j, topVirtual);
		}
		if (i > 0) {
			if ((boolArr[i - 1][j])) {
				ufConnect((i - 1) * N + j, i * N + j);
			}
		}
		if (i < (N - 1)) {
			if (boolArr[i + 1][j]) {
				ufConnect((i + 1) * N + j, i * N + j);
			}
		}
		if (j > 0) {
			if (boolArr[i][j - 1]) {
				ufConnect((i * N) + (j - 1), (i * N) + j);
			}
		}
		if (j < (N - 1)) {
			if (boolArr[i][j + 1]) {
				ufConnect((i * N) + (j + 1), (i * N) + j);
			}
		}
		if (i == (N - 1)) {
			uf2.union(N * i + j, bottomVirtual);
		}
		if (uf2.connected(N * i + j, bottomVirtual) && isFull(i, j)) {
			uf.union(N * i + j, bottomVirtual);
		}
	}

	/**
	 * isOpen() returns a boolean value, testing whether or not the box is open.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isOpen(int i, int j) {
		validate(i, j);
		if (boolArr[i][j]) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isFull() returns a boolean value, testing whether or not the box is full.
	 * This returns true if the box is open and if there is a connection between the
	 * box and topVirtual.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j) {
		validate(i, j);
		if (uf.connected(topVirtual, (i * N) + j)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * percolates() returns a boolean value for whether or not the system
	 * percolates. This returns true if there is a connection in uf between
	 * topVirtual and bottomVirtual.
	 * 
	 * @return
	 */
	public boolean percolates() {
		if (uf.connected(topVirtual, bottomVirtual)) {
			return true;
		} else {
			return false;
		}
	}
}