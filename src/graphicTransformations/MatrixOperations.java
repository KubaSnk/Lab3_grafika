package graphicTransformations;

public class MatrixOperations {
	private static double wyznaczWyznacznikMacierzy(double[][] tablica) {
		double wyznacznik = 0;

		if (tablica.length == 1 && tablica[0].length == 1) {
			wyznacznik = tablica[0][0];
		} else if (tablica.length != tablica[0].length) {
			throw new RuntimeException(
					"Nie mo¿na wyznaczyæ wyznacznika dla macierzy która nie jest kwadratowa");
		} else if (tablica.length == 2 && tablica[0].length == 2) {
			wyznacznik = (tablica[0][0] * tablica[1][1] - tablica[0][1]
					* tablica[1][0]);
		} else {
			double[][] nTab = new double[tablica.length + (tablica.length - 1)][tablica[0].length];
			for (int i = 0, _i = 0; i < nTab.length; i++, _i++) {
				for (int j = 0; j < tablica[0].length; j++) {
					if (_i < tablica.length && j < tablica[0].length) {
						nTab[i][j] = tablica[_i][j];
					} else {
						_i = 0;
						nTab[i][j] = tablica[_i][j];
					}
				}
			}

			double iloczyn = 1;
			int _i;

			for (int i = 0; i < tablica.length; i++) {
				_i = i;
				for (int j = 0; j < tablica[0].length; j++) {
					iloczyn *= nTab[_i][j];
					_i++;
				}
				wyznacznik += iloczyn;
				iloczyn = 1;
			}

			iloczyn = 1;
			for (int i = 0; i < tablica.length; i++) {
				_i = i;
				for (int j = tablica[0].length - 1; j >= 0; j--) {
					iloczyn *= nTab[_i][j];
					_i++;
				}
				wyznacznik -= iloczyn;
				iloczyn = 1;
			}
		}
		return wyznacznik;
	}

	public static double[][] wyznaczMacierzOdwrotna(double[][] matrix) {
		if (!isKwadratowa(matrix)) {
			throw new RuntimeException(
					"Nie mo¿na wyznaczyæ macierzy odwrotnej dla macierzy która nie jest kwadratowa");
		} else {
			double[][] macierzOdwrotna;

			if (matrix.length == 1) {
				macierzOdwrotna = new double[1][1];
				macierzOdwrotna[0][0] = 1 / wyznaczWyznacznikMacierzy(matrix);
				return macierzOdwrotna;
			} else {
				macierzOdwrotna = new double[matrix.length][matrix[0].length];
				double[][] macierzDolaczona = new double[matrix.length][matrix[0].length];
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[0].length; j++) {
						double[][] temp = new double[matrix.length - 1][matrix[0].length - 1];
						int a = 0, b = 0;
						for (int w = 0; w < matrix.length; w++) {
							for (int z = 0; z < matrix[0].length; z++) {
								if (w != i && z != j) {
									if (b >= temp.length) {
										b = 0;
										a++;
									}
									temp[a][b] = matrix[w][z];
									b++;
								}
							}
						}

						double wyznacznikTemp = wyznaczWyznacznikMacierzy(temp);

						if ((i + j) % 2 != 0) {// Niparzyste czyli zmiana znaku
												// wyznacznika
							if (wyznacznikTemp > 0) {
								wyznacznikTemp -= 2 * wyznacznikTemp;
							} else {
								wyznacznikTemp -= 2 * wyznacznikTemp;
							}
						} else {
						}
						macierzDolaczona[i][j] = wyznacznikTemp;
					}
				}
				macierzDolaczona = transponujTablice(macierzDolaczona);
				macierzOdwrotna = pomnozPrzezSkalarTablice(
						1 / wyznaczWyznacznikMacierzy(matrix), macierzDolaczona);
				return macierzOdwrotna;
			}
		}
	}

	public static boolean isKwadratowa(double[][] matrix) {
		if (matrix[0].length == matrix.length) {
			return true;
		} else {
			return false;
		}
	}

	private static double[][] transponujTablice(double[][] tablica) {
		double[][] macierzTransponowana = new double[tablica[0].length][tablica.length];
		for (int i = 0; i < tablica.length; i++) {
			for (int j = 0; j < tablica[0].length; j++) {
				macierzTransponowana[j][i] = tablica[i][j];
			}
		}
		return macierzTransponowana;
	}

	private static double[][] pomnozPrzezSkalarTablice(double skalar,
			double[][] tablica) {
		double[][] macierzPomnozona = new double[tablica.length][tablica[0].length];
		for (int i = 0; i < tablica.length; i++) {
			for (int j = 0; j < tablica[0].length; j++) {
				macierzPomnozona[i][j] = (tablica[i][j] * skalar);
			}
		}
		return macierzPomnozona;
	}
}
