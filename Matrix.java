public class Matrix {
	private double[][] values;
	
	public Matrix() {
		// Simply for ease of initialization
		values = new double[1][1];
	}
	
	public Matrix(int rows, int columns) {
		// Initializes to 0s
		values = new double[rows][columns];
	}
		
	public Matrix(double[][] inputs) {
		// Initializes a 2D array
		this.set(inputs);
	}
	
	public String toString() {
		String s = "[";
		String row = "[";
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < this.columns(); j++) {
				row += values[i][j];
				row += " ";
			}
			row = row.substring(0, row.length() - 1);
			row += "]";
			s += row;
			row = "[";
			s += "\n";
		}
		return s.substring(0, s.length() - 1) + "]";
	}
	
	public int rows() {
		// Gets rows
		return values.length;
	}
	
	public int columns() {
		// Gets columns
		return values[0].length;
	}
	
	public void set(double[][] inputs) {
		// Sets internal 2D array
		values = inputs;
	}
	
	public void put(double target, int row, int column) {
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
		// Supplants value at row/column with 0
		values[row - 1][column - 1] = 0;
	}
	
	public double get(int row, int column) {
		// Retrieves value at row/column
		return values[row - 1][column - 1];
	}
	
	public void empty() {
		// Supplants all values with 0
		values = new double[values.length][values[0].length];
	}
	
	public void swap_row(int row_a, int row_b) {
		// Switches two rows
		row_a -= 1;
		row_b -= 1;
		double[] temp = values[row_a];
		for (int i = 0; i < values.length; i++) {
			values[row_a][i] = values[row_b][i];
			values[row_b][i] = temp[i];
		}
	}
	
	public void swap_column(int column_a, int column_b) {
		// Switches two columns
		column_a -= 1;
		column_b -= 1;
		double[] temp = values[column_a];
		for (int i = 0; i < values.length; i++) {
			values[i][column_a] = values[i][column_b];
			values[i][column_b] = temp[i];
		}
	}
	
	public int count(double target) {
		// Number of times some value appears
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
		// Adds two matrices together
		Matrix m = new Matrix();
		try {
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values[0].length; j++) {
					m.put(values[i][j] + target.get(i + 1, j + 1), i + 1, j + 1);
				}
			}
		} catch (Exception e) {
			System.out.println("Dimension mismatch!");
		}
		return m;
	}
	
	public Matrix times(Matrix target) {
		// Multiplies two matrices
		Matrix m = new Matrix(values[0].length, target.rows());
		try {
			
		} catch (Exception e) {
			System.out.println("Dimension mismatch!");
		}
		return m;
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
	
	private static void tests() {
		// Testing matrix constructors
		Matrix a = new Matrix();
		Matrix b = new Matrix(2, 3);
		Matrix c = new Matrix(new double[5][6]);
		
		// Testing matrix manipulations
		b.put(2, 1, 1);
		b.put(10.5, 1, 2);
		b.put(5.2, 1, 3);
		b.put(2, 2, 1);
		b.put(72, 2, 2);
		b.put(12.424, 2, 3);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		
	}
	
	public static void main(String[] args) {
		tests();
	}
}
