import java.awt.Font;
import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;

public class PercolationVisualizer {

	// delay in miliseconds (controls animation speed)
	private static final int DELAY = 100;
	
	private static final Logger log4j = LogManager.getLogger(PercolationVisualizer.class 
	        .getName());

	// draw N-by-N percolation system
	public static boolean draw(Percolation perc, int N) {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setXscale(-.05 * N, 1.05 * N);
		StdDraw.setYscale(-.05 * N, 1.05 * N); // leave a border to write text
		StdDraw.filledSquare(N / 2.0, N / 2.0, N / 2.0);

		// draw N-by-N grid
		int opened = 0;
		for (int row = 1; row <= N; row++) {
			for (int col = 1; col <= N; col++) {
				if (perc.isFull(row, col)) {
					log4j.info("draw() - BLUE " + row + " " + col);
					StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
					opened++;
				} else if (perc.isOpen(row, col)) {
					log4j.info("draw() - White " + row + " " + col);
					StdDraw.setPenColor(StdDraw.WHITE);
					opened++;
				} else {
					log4j.info("draw() - BLACK " + row + " " + col);
					StdDraw.setPenColor(StdDraw.BLACK);					
				}
				StdDraw.filledSquare(col - 0.5, N - row + 0.5, 0.45);
			}
		}

		// write status text
		StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(.25 * N, -N * .025, opened + " open sites");
		log4j.info("draw() - Check if it percolates");

		if (perc.percolates()) {
			StdDraw.text(.75 * N, -N * .025, "percolates");
			return true;
		} else {
			StdDraw.text(.75 * N, -N * .025, "does not percolate");
		}
		return false;
	}

	/**
	 * In main I put an if to check if it percolates. As soon as
	 * it percolates the program will not test the other pairs
	 * If you want to see ALL percolations just remove the IF below
	 * @param args
	 */
	public static void main(String[] args) {
		In in = new In(args[0]); // input file
		int N = in.readInt(); // N-by-N percolation system

		boolean doesPercolate = new Boolean(false);

		// turn on animation mode
		StdDraw.show(0);
				
		log4j.info("main() - Call Percolation Constructor");
		// repeatedly read in sites to open and draw resulting system
		Percolation perc = new Percolation(N);
		log4j.info("main() - Call draw(perc, N) " + N);
		doesPercolate = draw(perc, N); // Here is draws everything BLACK
		StdDraw.show(DELAY);
		while (!in.isEmpty()) {
			if (!doesPercolate) {
				int row = in.readInt();
				int col = in.readInt();
				log4j.info("main() - Call perc.open - row " + row + " and col " + col);
				perc.open(row, col);
				log4j.info("main() - Call draw(perc,N) ");
				doesPercolate = draw(perc, N);
				StdDraw.show(DELAY);				
			}
		}
	}
}
