// R. Jayaraman

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
		// Parses matrix to string
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
	
	public Matrix clone() {
		// Clones matrix 
		Matrix m = new Matrix();
		m.set(values.clone());
		return m;
	}
	
	public static Matrix identity(int dimension) {
		// Returns identity matrix of dimension m
		Matrix m = new Matrix(dimension, dimension);
		for (int i = 1; i <= m.rows(); i++) {
			m.put(1, i, i);
		}
		return m;
	}
	
	public static Matrix toMatrix(Object target) throws Exception {
		// Parses object into matrix form
		Matrix m;
		if (target instanceof double[]) {
			double[] parsed = (double[]) target;
			m = new Matrix(1, parsed.length);
			for (int i = 0; i < m.columns(); i++) {
				m.put(parsed[i], 1, i + 1);
			}
		} else if (target instanceof double[][]) {
			double[][] parsed = (double[][]) target;
			m = new Matrix(parsed);
		} else {
			throw new Exception("Incompatible data types!");
		}
		return m;
	}
	
	public static boolean is_upper_triangular(Matrix target) {
		// Checks if matrix is upper triangular
		return false;
	}
	
	public static boolean is_lower_triangular(Matrix target) {
		// Checks if matrix is lower triangular
		return false;
	}
	public int rows() {
		// Gets rows
		return values.length;
	}
	
	public int columns() {
		// Gets columns
		return values[0].length;
	}
	
	public boolean set(double[][] inputs) {
		// Sets internal 2D array
		try {
			values = inputs;
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public Matrix set_row(Matrix new_row, int row) {
		// Supplants a row 
		return null;
	}
	
	public Matrix set_column(Matrix new_column, int column) {
		// Supplants a column
		return null;
	}
	
	public boolean put(double target, int row, int column) {
		// Indexes by 1; standard matrix convention
		try {
			values[row - 1][column - 1] = target;
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public Matrix append_row(double[] new_row) {
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
					temp[i][j] = values[i][j];
				}
			}
			// Adding row as usual
			for (int i = 0; i < temp.length; i++) {
				temp[temp.length - 1][i] = new_row[i];
			}
			values = temp;
		}
		return this;
	}
	
	public Matrix append_column(double[] new_column) {
		// Adds a new column
		// Accounts for dimension mismatches
		if (new_column.length <= values[0].length) {
			double[][] temp = new double[values.length][values[0].length + 1];
			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values[0].length; j++) {
					temp[i][j] = values[i][j];
				}
			}
			// Supplants empty dimensions with 0s
			for (int i = 0; i < temp.length; i++) {
				if (i < new_column.length) {
					temp[i][temp[0].length - 1] = new_column[i];
				}
			}
			values = temp;
		} else {
			int max = new_column.length;
			double[][] temp = new double[max][values[0].length + 1];
			// Extending original matrix
			for (int i = 0; i < max; i++) {
				for (int j = 0; j < temp[0].length; j++) {
					temp[i][j] = values[i][j];
				}
			}
			// Adding column as usual
			for (int i = 0; i < temp[0].length; i++) {
				temp[i][temp[0].length - 1] = new_column[i];
			}
			values = temp;
		}
		return this;
	}
	
	public boolean remove(int row, int column) {
		// Supplants value at row/column with 0
		try {
			values[row - 1][column - 1] = 0;
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean remove_row(int row) {
		// Supplants a row with 0s
		return false;
	}
	
	public boolean remove_column(int column) {
		// Supplants a column with 0s
		return false;
	}
	
	public double get(int row, int column) {
		// Retrieves value at row/column
		return values[row - 1][column - 1];
	}
	
	public Matrix get_row(int row) {
		// Returns a particular row
		return null;
	}
	
	public Matrix get_column(int column) {
		// Returns a particular column
		return null;
	}
	
	public boolean empty() {
		// Supplants all values with 0
		try {
			values = new double[values.length][values[0].length];
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public Matrix swap_row(int row_a, int row_b) {
		// Switches two rows
		row_a -= 1;
		row_b -= 1;
		double[] temp = values[row_a].clone();
		for (int i = 0; i < values[0].length; i++) {
			values[row_a][i] = values[row_b][i];
			values[row_b][i] = temp[i];
		}
		return this;
	}
	
	public Matrix multiply_row(double multiple) {
		// Multiplies row by some constant
		try {
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return this;
	}
	
	public Matrix add_rows(Matrix new_row, int row) {
		// Adds two rows together
		return this;
	}
	
	public Matrix swap_column(int column_a, int column_b) {
		// Switches two columns
		column_a -= 1;
		column_b -= 1;
		System.out.println(this);
		double[] temp = new double[values.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = values[i][column_a];
		}
		for (int i = 0; i < values.length; i++) {
			values[i][column_a] = values[i][column_b];
			values[i][column_b] = temp[i];
		}
		return this;
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
	
	public Matrix plus(Matrix target) {
		// Adds two matrices together
		Matrix m = new Matrix(this.rows(), this.columns());
		try {
			for (int i = 1; i <= this.rows(); i++) {
				for (int j = 1; j <= this.columns(); j++) {
					m.put(this.get(i, j) + target.get(i, j), i, j);
				}
			}
		} catch (Exception e) {
			System.out.println("Dimension mismatch!");
		}
		return m;
	}
	
	public Matrix minus(Matrix target) {
		// Subtracts two matrices
		Matrix m = new Matrix(this.rows(), this.columns());
		try {
			for (int i = 1; i <= this.rows(); i++) {
				for (int j = 1; j <= this.columns(); j++) {
					m.put(this.get(i, j) - target.get(i, j), i, j);
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
	
	public Matrix to_the(int power) {
		// Returns matrix to the power of some integer constant
		return null;
	}
	
	public Matrix invert() throws Exception {
		// Inverts the matrix
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
	
	private static void constructor_tests() {
		// Testing matrix constructors
		System.out.println("...Testing Constructors...");
		Matrix a = new Matrix();
		Matrix b = new Matrix(2, 3);
		Matrix c = new Matrix(new double[5][6]);
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
	
	private static void manipulation_tests() {
		// Testing matrix manipulations
		System.out.println("...Testing Manipulations...");
		Matrix b = new Matrix(2, 3);
		Matrix c = new Matrix(new double[5][6]);
		c.put(123, 5, 6);
		b.put(2, 1, 1);
		b.put(10.5, 1, 2);
		b.put(5.2, 1, 3);
		b.put(2, 2, 1);
		b.put(72, 2, 2);
		b.put(12.424, 2, 3);
		System.out.println(b);
		System.out.println(c);
		b.remove(2, 2);
		System.out.println(b);
		System.out.println(c);
		System.out.println(c.get(5, 6));
		c.empty();
		System.out.println(c.get(5, 6));
		System.out.println(b);
		System.out.println(b.swap_row(1, 2));
		b.remove(2, 3);
		b.swap_column(3, 2);
		System.out.println(b);
		System.out.println(b.count(0));	
	}
	
	private static void function_tests() {
		// Testing matrix functions
		System.out.println("...Testing Functions...");
		Matrix a = new Matrix(2, 2);
		Matrix b = new Matrix(2, 2);
		for (int i = 1; i <= 2; i++) {
			for (int j = 1; j <= 2; j++) {
				a.put((int) (Math.random() * 10), i, j);
				b.put((int) (Math.random() * 10), i, j);
			}
		}
		System.out.println(a);
		System.out.println(b);
		System.out.println(a.plus(b));
		System.out.println(a.minus(b));
		System.out.println(a.times(b));
		double[] row = {1, 1, 2, 3, 5};
		double[][] dummy = {{1, 2}, {3, 4}};
		try {
			System.out.println(Matrix.toMatrix(row));
			System.out.println(Matrix.toMatrix(dummy));
			System.out.println(Matrix.toMatrix(row).get(1, 2));
			System.out.println(Matrix.toMatrix(dummy).get(2, 1));
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(Matrix.identity(5));
		
	}
	
	public static void main(String[] args) {
		// constructor_tests();
		// manipulation_tests();
		function_tests();
	}
}
