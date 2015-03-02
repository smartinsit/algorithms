import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Percolation {

	private static final Logger log4j = LogManager.getLogger(Percolation.class
			.getName());

	private WeightedQuickUnionUF quickFindMatrix;
	private int sizeOfMatrix = 0;
	private int openCount = 0;
	private Boolean[][] siteIsOpen = null;
	private static final int MAX_ROWS_COLS = 100;
	private static final int MIN_ROWS_COLS = 1;

	// Constructor: create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		quickFindMatrix = new WeightedQuickUnionUF(N * N); // Initialize the
															// matrix
		sizeOfMatrix = N; // Initialize the number of rows and columns expected
							// for the matrix
		// instantiate the object Boolean[N][N] and pass the reference to
		// siteIsOpen
		this.siteIsOpen = new Boolean[N][N];

		// Check if it is not out boundaries
		if (N < MIN_ROWS_COLS) {
			throw new java.lang.IllegalArgumentException(
					"N must be equal greater than " + MIN_ROWS_COLS);
		} else if (N > MAX_ROWS_COLS) {
			throw new java.lang.IllegalArgumentException(
					"N must be equal less than " + MAX_ROWS_COLS);
		}

		// Close every single site
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				this.siteIsOpen[row][col] = false;
			}
		}
	}

	/**
	 * <pre>
	 * Functions:
	 * - open site (row i, column j) if it is not open already 
	 * - Check if is connected and make the union
	 * Notes: 
	 * (1) In the quickFindMatrix.union(p,q) it passes the value of each cell. 
	 * For instance, a matrix with size 6 will have 36 cells, with values 
	 * ranging from 0 to 35. Hence the formula sizeOfMatrix * (row -1) + (col -1) 
	 * to find the value of the current cell, and so on
	 * 
	 * @param row
	 * @param col
	 * </pre>
	 */
	public void open(int row, int col) {
		// check if row or col is greater than matrix size
		if (row < 1 || row > sizeOfMatrix || col < 1 || col > sizeOfMatrix) {
			throw new java.lang.IndexOutOfBoundsException();
		}

		log4j.info("open() Open site siteIsOpen[row - 1][col - 1]: "
				+ (row - 1) + " " + (col - 1));
		siteIsOpen[row - 1][col - 1] = true; // open the site
		this.openCount++;

		// Union top row
		if (row - 1 > 0) {
			log4j.info("open() Check Top Site - isOpen(row - 1, col): "
					+ (row - 1) + " " + (col));
			if (col > 0 && isOpen(row - 1, col)) {
				log4j.info("open() Top Row - quickFindMatrix.union(sizeOfMatrix * (row - 1) + (col - 1), sizeOfMatrix * (row - 2) + (col - 1)) : "
						+ (sizeOfMatrix * (row - 1) + (col - 1))
						+ " and "
						+ (sizeOfMatrix * (row - 2) + (col - 1)));
				quickFindMatrix.union(sizeOfMatrix * (row - 1) + (col - 1),
						sizeOfMatrix * (row - 2) + (col - 1));
			}
		}

		// Union buttom row
		if (row + 1 <= sizeOfMatrix) {
			log4j.info("open() Check Buttom Site - isOpen(row + 1, col): "
					+ (row + 1) + " " + (col));
			if (isOpen(row + 1, col)) {
				log4j.info("open() Button row - quickFindMatrix.union(sizeOfMatrix * (row - 1) + (col - 1), sizeOfMatrix * (row) + (col - 1)) : "
						+ (sizeOfMatrix * (row - 1) + (col - 1))
						+ " and "
						+ (sizeOfMatrix * (row) + (col - 1)));
				quickFindMatrix.union(sizeOfMatrix * (row - 1) + (col - 1),
						sizeOfMatrix * (row) + (col - 1));
			}
		}

		// Union left
		if (col - 1 > 0) {
			log4j.info("open() Check Left Site - isOpen(row, col - 1): "
					+ (row) + " " + (col - 1));
			if (isOpen(row, col - 1)) {
				log4j.info("open() Left Row - quickFindMatrix.union(sizeOfMatrix * (row - 1) + (col - 1), sizeOfMatrix * (row - 1) + (col - 2)) : "
						+ (sizeOfMatrix * (row - 1) + (col - 1))
						+ " and "
						+ (sizeOfMatrix * (row - 1) + (col - 2)));
				quickFindMatrix.union(sizeOfMatrix * (row - 1) + (col - 1),
						sizeOfMatrix * (row - 1) + (col - 2));
			}
		}

		// Union right
		if (col + 1 <= sizeOfMatrix) {
			log4j.info("open() Check Right Site - isOpen(row, col + 1): "
					+ (row) + " " + (col + 1));
			if (isOpen(row, col + 1)) {
				log4j.info("open() Right row - quickFindMatrix.union(sizeOfMatrix * (row - 1) + (col - 1), sizeOfMatrix * (row - 1) + (col)) : "
						+ (sizeOfMatrix * (row - 1) + (col - 1))
						+ " and "
						+ (sizeOfMatrix * (row - 1) + (col)));
				quickFindMatrix.union(sizeOfMatrix * (row - 1) + (col - 1),
						sizeOfMatrix * (row - 1) + (col));
			}
		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int row, int col) {
		// The array goes from 0 to 5, but drawing goes 1 to 6
		if (row < 1 || row > sizeOfMatrix || col < 1 || col > sizeOfMatrix) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		log4j.info("isOpen() siteIsOpen[row - 1][col - 1]: "
				+ siteIsOpen[row - 1][col - 1] + " (row - 1): " + (row - 1)
				+ " (col - 1): " + (col - 1));
		return siteIsOpen[row - 1][col - 1];
	}

	// is site (row i, column j) full?
	/**
	 * <pre>
	 * A full site is an open site that can be connected to 
	 * an open site in the top row via a chain of neighboring 
	 * (left, right, up, down) open sites
	 * Notes:
	 * (1) quickFindMatrix.connected checks a specific site is part
	 * of a component which is connected to a top row component (root). 
	 * That is why the loop below checks just from 0 to the sizeOfMatrix.
	 * This covers the entire top row.  You need to pass the integer of  
	 * sites. For instance, the value of cell in the top row and 
	 * the current cell which can range from 0 to 35.
	 * @param row
	 * @param col
	 * @return
	 * </pre>
	 */
	public boolean isFull(int row, int col) {
		if (row < 1 || row > sizeOfMatrix || col < 1 || col > sizeOfMatrix) {
			throw new java.lang.IndexOutOfBoundsException();
		}
		log4j.info("isFull() calling IsOpen(row, col): " + isOpen(row, col)
				+ " row: " + row + " col: " + col);
		if (isOpen(row, col)) {
			// Loop through the size of matrix
			for (int i = 0; i < sizeOfMatrix; i++) {
				log4j.info("isFull() calling quickFindMatrix.connected (i, sizeOfMatrix * (row - 1) + col - 1)): "
						+ i + "," + (sizeOfMatrix * (row - 1) + (col - 1)));
				if (quickFindMatrix.connected(i, sizeOfMatrix * (row - 1) + col
						- 1)) {
					return true;
				}
			}
		}

		return false;
	}

	// does the system percolate?
	public boolean percolates() {
		for (int row = 0; row < sizeOfMatrix; row++) {
			for (int col = 0; col < sizeOfMatrix; col++) {
				int cur = (sizeOfMatrix - 1) * sizeOfMatrix + col;
				if (isOpen(1, row + 1) && isOpen(sizeOfMatrix, col + 1)
						&& quickFindMatrix.connected(row, cur)) {
					// System.out.println("It does perculate.");
					log4j.info("percolates() - It does perculate.");
					return true;
				}
			}
		}
		// System.out.println("It does NOT perculate.");
		log4j.info("percolates() - It does NOT perculate.");
		/*
		 * try { Thread.sleep(2000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); };
		 */
		return false;
	}

	public static void main(String[] args) {
		// test client (optional)
	}
}
