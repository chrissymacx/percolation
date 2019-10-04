/**
 * @authors Chrissy MacPherson & Jo Phillips
 * PercolationStats() finds statistics of Percolation objects on an
 * NxN using T series of tests, including mean, standard deviation,
 * and confidence low and high percentages of the number of open
 * boxes in a percolated system.
 */

package a01_percolation;

import static edu.princeton.cs.algs4.StdStats.*;

import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int N;
	private int T;
	private Percolation p;
	private double[] tests;

	public PercolationStats(int n, int t) // perform T independent experiments on a N by N grid
	{
		if (n <= 1 || t <= 1) {
			throw new java.lang.IllegalArgumentException("T and N must be more than 1.");
		}
		N = n;
		T = t;

		tests = test();
	}

	/**
	 * test runs the T tests and stores them so all the other methods can access and
	 * use the data
	 * 
	 * @return
	 */
	private double[] test() {
		double[] count = new double[T];
		for (int i = 0; i < T; i++) {
			p = new Percolation(N);
			while (!p.percolates()) {
				int randI = (int) (Math.random() * N);
				int randJ = (int) (Math.random() * N);
				if (!p.isOpen(randI, randJ)) {
					p.open(randI, randJ);
					count[i]++;
				}

			}
			count[i] /= (N * N);
		}
		return count;
	}

	// sample mean of percolation threshold
	
	public double mean() {
		return StdStats.mean(tests);
	}

	// sample standard deviation of percolation threshold
	public double stddevs() {
		return stddev(tests);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLow() {

		return mean() - 1.96 * stddevs() / Math.sqrt(T);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHigh() {
		return mean() + 1.96 * stddevs() / Math.sqrt(T);
	}

}