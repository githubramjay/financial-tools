public class Matrix {
	private static double[][] values;
	
	public Matrix() {
		// Simply for ease of initialization
		values = null;
	}
	
	public Matrix(int rows, int columns) {
		// Initializes to 0s
		values = new double[rows][columns];
	}
		
	public Matrix(double[][] inputs) {
		// Initializes a 2D array
		this.set(inputs);
	}
	
	public void set(double[][] inputs) {
		values = inputs;
	}
	
	public void insert(double target, int row, int column) {
		// Indexes by 1; standard matrix convention
		values[row - 1][column - 1] = target;
	}
	
	public void append(double[] new_row) {
		// Adds a new row
		// Accounts for dimension mismatches
		if (new_row.length <= values.length) {
			double[][] temp = new double[values.length + 1][values[0].length];
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values[0].length; j++) {
					temp[i][j] = values[i][j];
				}
			}
			// Supplants empty dimensions with 0s
			for (int i = 0; i < temp.length; i++) {
				if (i < new_row.length) {
					temp[temp.length - 1][i] = new_row[i];
				}
			}
			values = temp;
		} else {
			int max = new_row.length;
			double[][] temp = new double[values.length + 1][max];
			// Extending original matrix
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < max; j++) {
					if (j < max) {
						temp[i][j] = values[i][j];
					}
				}
			}
			// Adding row as usual
			for (int i = 0; i < temp.length; i++) {
				temp[temp.length - 1][i] = new_row[i];
			}
			values = temp;
		}
	}
	
	public void remove(int row, int column) {
		values[row - 1][column - 1] = 0;
	}
	
	public double get(int row, int column) {
		return values[row - 1][column - 1];
	}
	
	public void empty() {
		values = new double[values.length][values[0].length];
	}
	
	public void swap_row(int row_a, int row_b) {
		row_a -= 1;
		row_b -= 1;
		double[] temp = values[row_a];
		for (int i = 0; i < values.length; i++) {
			values[row_a][i] = values[row_b][i];
			values[row_b][i] = temp[i];
		}
	}
	
	public void swap_column(int column_a, int column_b) {
		column_a -= 1;
		column_b -= 1;
		double[] temp = values[column_a];
		for (int i = 0; i < values.length; i++) {
			values[i][column_a] = values[i][column_b];
			values[i][column_b] = temp[i];
		}
	}
	
	public int count(double target) {
		int counter = 0;
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
				if (values[i][j] == target) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	public Matrix add(Matrix target) {
		try {
			
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public Matrix times(Matrix target) {
		try {
			
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public Matrix invert() {
		return null;
	}
	
	public Matrix cholesky_factorization() {
		return null;
	}
	
	public Matrix[] LU_Decomposition() {
		return null;
	}
	
	public double determinant() {
		return 0;
	}
	
	public Matrix transpose() {
		return null;
	}
	
	public Matrix reduce() {
		return null;
	}
	
	private static void test() {
		
	}
	
	public static void main(String[] args) {
		test();
	}
}
