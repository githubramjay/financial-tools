public class Matrix {
	private static double[][] values;
	
	public Matrix() {
		values = null;
	}
	
	public Matrix(int rows, int columns) {
		values = new double[rows][columns];
	}
		
	public Matrix(double[][] inputs) {
		int max = 1;
		// Finding max column length
		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i].length > max) {
				max = inputs[i].length;
			}
		}
		values = new double[inputs.length][max];
		// Appending 0s to empty entries
		for (int i = 0; i < inputs.length; i++) {
			for (int j = 0; j < max; j++) {
				if (inputs[i].length < max) {
					values[i][j] = 0;
				} else {
					values[i][j] = inputs[i][j];
				}
			}
		}
	}
	
	public void insert(double target, int row, int column) {
		values[row - 1][column - 1] = target;
	}
	
	public void append(double[] new_row) {
		
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
	
	public int[][][] search(double target) {
		return null;
	}
	
	public Matrix add(Matrix target) {
		return null;
	}
	
	public Matrix times(Matrix target) {
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
}
