/**
 * @authors Chrissy MacPherson & Jo Phillips
 */

package a01_percolation;


public class PercolationApp {

	public static void main(String[] args) {
		int N = 15;
		
		
		Percolation perc = new Percolation(N);
		while(!perc.percolates()) {
			int randI = (int)(Math.random() * N);
			int randJ = (int)(Math.random() * N);
			
			if (!perc.isOpen(randI, randJ)) {
				perc.open(randI, randJ);
				PercolationVisualizer.draw(perc, N);

			}
		}
		
		PercolationStats percStats = new PercolationStats(200,100);
		System.out.println("Mean: " + percStats.mean());
		System.out.println("Std Dev: " + percStats.stddevs());
		System.out.println("Confidence High: " + percStats.confidenceHigh());
		System.out.println("Confidence Low: " + percStats.confidenceLow());
		
	}

}
