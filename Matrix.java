// R. Jayaraman
// Full-fledged Matrix functionality in Java
// Shorthand method calls + Descriptive method calls

public class Matrix {
	private double[][] values;
	protected static final double TOLERANCE = 1e-10;
	
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
	
	public boolean equals(Object o) {
		// Checks equivalence of matrices
		if (o instanceof Matrix) {
			Matrix m = (Matrix) o;
			if (this.rows() != m.rows()) {
				return false;
			}
			if (this.columns() != m.columns()) {
				return false;
			}
			for (int i = 1; i <= this.rows(); i++) {
				for (int j = 1; j <= this.columns(); j++) {
					if (this.get(i, j) != m.get(i, j)) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
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
	
	public static Matrix unit(int dimension) {
		// Returns unit matrix of dimension m
		Matrix m = new Matrix(1, dimension);
		for (int i = 1; i <= m.columns(); i++) {
			m.put(1, 1, i);
		}
		return m;
	}
	
	public static Matrix to_matrix(Object target) throws Exception {
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
	
	public static double[] vector_to_array(Matrix vector) {
		// Turns row/column vector to array
		try {
			if (vector.rows() == 1) {
				double[] a = new double[vector.columns()];
				for (int i = 1; i <= vector.columns(); i++) {
					a[i - 1] = vector.get(1, i);
				}
				return a;
			} else {
				double[] a = new double[vector.rows()];
				for (int i = 1; i <= vector.rows(); i++) {
					a[i - 1] = vector.get(i, 1);
				}
				return a;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return new double[1];
	}
	
	public static double[][] to_array(Matrix target) {
		// Turns matrix to 2D array
		try {
			double[][] a = new double[target.rows()][target.columns()];
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a[0].length; j++) {
					a[i][j] = target.get(i + 1, j + 1);
				}
			}
			return a;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
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
		try {
			for (int i = 1; i <= this.columns(); i++) {
				this.put(new_row.get(1, i), row, i);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return this;
	}
	
	public Matrix set_column(Matrix new_column, int column) {
		// Supplants a column
		try {
			for (int i = 1; i <= this.columns(); i++) {
				this.put(new_column.get(i, 1), i, column);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return this;
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
	
	public Matrix append_row(Matrix new_row) {
		// Adds a new row
		// Accounts for dimension mismatches
		Matrix m = new Matrix();
		try {
			if (new_row.columns() <= this.columns()) {
				m = new Matrix(this.rows() + 1, this.columns());
			} else {
				m = new Matrix(this.rows() + 1, new_row.columns());
			}
			for (int i = 1; i <= this.rows(); i++) {
				for (int j = 1; j <= this.columns(); j++) {
					m.put(this.get(i, j), i, j);
				}
			}
			for (int i = 1; i <= new_row.columns(); i++) {
				m.put(new_row.get(1, i), m.rows(), i);
			}
			this.set(Matrix.to_array(m));
		} catch (Exception e) {
			System.out.println(e);
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
	
	public boolean delete_row(int row) {
		// Deletes row
		return false;
	}
	
	public boolean delete_column(int column) {
		// Deletes column
		return false;
	}
	
	public double get(int row, int column) {
		// Retrieves value at row/column
		return values[row - 1][column - 1];
	}
	
	public Matrix get_row(int row) {
		// Returns a particular row
		Matrix m = new Matrix(1, this.columns());
		for (int i = 1; i <= this.columns(); i++) {
			m.put(this.get(1, i), 1, i);
		}
		return m;
	}
	
	public Matrix get_column(int column) {
		// Returns a particular column
		Matrix m = new Matrix(this.rows(), 1);
		for (int i = 1; i <= this.rows(); i++) {
			m.put(this.get(i, 1), i, 1);
		}
		return m;
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
	
	public Matrix times(Matrix target) throws Exception {
		// Multiplies two matrices
		Matrix m = new Matrix();
		double[][] entries = new double[this.rows()][target.columns()];
		if (this.columns() != target.rows()) {
			throw new Exception("Dimension mismatch!");
		}
		try {
			for (int i = 0; i < this.rows(); i++) {
				for (int j = 0; j < target.columns(); j++) {
					for (int k = 0; k < this.columns(); k++) {
						entries[i][j] = entries[i][j] + values[i][k] * target.get(k + 1, j + 1);
					}
				}
			}
			m.set(entries);
		} catch (Exception e) {
			System.out.println("Iteration error!");
		}
		return m;
	}
	
	public Matrix times(double target) {
		// Multiplies matrix by constant
		Matrix m = new Matrix(this.rows(), this.columns());
		try {
			for (int i = 1; i <= this.rows(); i++) {
				for (int j = 1; j <= this.columns(); j++) {
					m.put(this.get(i, j) * target, i, j);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return m;
	}
	
	public Matrix to_the(int power) {
		// Returns matrix to the power of some integer constant
		// Assumes power is a whole number
		try {
			if (power == 0) {
				return identity(this.rows());
			} else {
				Matrix m = this.clone();
				for (int i = 1; i < power; i++) {
					m = this.times(m);
				}
				return m;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return this;
	}
	
	public Matrix invert() throws Exception {
		// Inverts the matrix
		return null;
	}
	
	public Matrix[] horizontal_slice(int[] rows) {
		// Splits a matrix along some horizontal(s)
		return new Matrix[1];
	}
	
	public Matrix[] vertical_slice(int[] columns) {
		// Splits a matrix along some vertical(s)
		return new Matrix[1];
	}
		
	public static Matrix horizontal_stitch(Matrix target) {
		// Joins two matrices over the horizontal
		return new Matrix();
	}
	
	public static Matrix vertical_stitch(Matrix target) {
		// Joins two matrices over the vertical
		return new Matrix();
	}
	
	public static Matrix cholesky_factorization(Matrix target) {
		return null;
	}
	
	public static Matrix[] LU_Decomposition(Matrix target) {
		return null;
	}
	
	public double determinant() {
		return 0;
	}
	
	public Matrix transpose() {
		// Transposes the matrix
		Matrix m = new Matrix(this.columns(), this.rows());
		for (int i = 1; i <= this.rows(); i++) {
			for (int j = 1; j <= this.columns(); j++) {
				m.put(this.get(i, j), j, i);
			}
		}
		return m;
	}
	
	public double[] gaussian_elimination(double[] vector) throws Exception {
		// Solves a system of linear equations given an array
		// Returns an array
		double[][] copy = values.clone();
		int n = vector.length;
        for (int p = 0; p < n; p++) {
            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(copy[i][p]) > Math.abs(copy[max][p])) {
                    max = i;
                }
            }
            double[] temp = copy[p]; 
            copy[p] = copy[max]; 
            copy[max] = temp;
            double t = vector[p];
            vector[p] = vector[max];
            vector[max] = t;
            // singular or nearly singular
            if (Math.abs(copy[p][p]) <= Matrix.TOLERANCE) {
                throw new Exception("Matrix is singular!");
            }
            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = copy[i][p] / copy[p][p];
                vector[i] -= alpha * vector[p];
                for (int j = p; j < n; j++) {
                    copy[i][j] -= alpha * copy[p][j];
                }
            }
        }
        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += copy[i][j] * x[j];
            }
            x[i] = (vector[i] - sum) / copy[i][i];
        }
        return x;
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
		System.out.println(b.clone());
		System.out.println(b.transpose());
		System.out.println(b.transpose().transpose().equals(b));
		try {
			double[][] entries = new double[2][2];
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					entries[i][j] = i*j-j+1*i;
				}
			}
			b.set(entries);		
			System.out.println(b.times(b).equals(b.to_the(2)));
		} catch (Exception e) {
			System.out.println(e);
		}
		double[] row1 = {1, 2, 3, 4, 5, 6};
		double[] row2 = {19, 20};
		try {
			System.out.println(b.append_row(Matrix.to_matrix(row1)));
			System.out.println(b.append_row(Matrix.to_matrix(row2)));
			System.out.println(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		double[] array = {1, 6, 2, 7, 3, 7};
		try {
			Matrix m = Matrix.to_matrix(array);
			System.out.println(m);
			System.out.println(m.transpose());
			System.out.println(m);
			double[] array2 = Matrix.vector_to_array(m);
			for (int i = 0; i < array2.length; i++) {
				System.out.println(array2[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		try {
			System.out.println(a.times(b));
		} catch (Exception e) {}
		double[] row = {1, 1, 2, 3, 5};
		double[][] dummy = {{1, 2}, {3, 4}};
		try {
			System.out.println(Matrix.to_matrix(row));
			System.out.println(Matrix.to_matrix(dummy));
			System.out.println(Matrix.to_matrix(row).get(1, 2));
			System.out.println(Matrix.to_matrix(dummy).get(2, 1));
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(Matrix.identity(5));
		Matrix m = Matrix.unit(5);
		Matrix mT = m.transpose();
		System.out.println(m);
		double[] ar = Matrix.vector_to_array(m);
		for (int i = 0; i < ar.length; i++) {
			System.out.println(ar[i]);
		}
		System.out.println(Matrix.vector_to_array(mT)[1]);
		try {
			System.out.println(m.times(mT).times(3));
		} catch (Exception e) {}
	}
	
	public static void main(String[] args) {
		constructor_tests();
		manipulation_tests();
		function_tests();
	}
}
