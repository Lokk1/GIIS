package by.bsuir.giis.util;

public class Matrix {

	public static float[][] mult(float[][] f, float[][] s) {
		if (f[0].length != s.length) {
			return null;
		}

		int a = f.length;
		int b = f[0].length;
		int c = s[0].length;
		float[][] d = new float[a][c];

		for (int i = 0; i < a; i++) {
			for (int j = 0; j < c; j++) {
				float summand = 0f;
				for (int k = 0; k < b; k++) {
					summand += f[i][k] * s[k][j];
				}
				d[i][j] = summand;
			}
		}
		return d;
	}

}
