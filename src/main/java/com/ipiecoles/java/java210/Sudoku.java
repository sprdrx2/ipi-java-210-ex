package com.ipiecoles.java.java210;
import java.util.Scanner;

public class Sudoku {

	public static final String FIN_SAISIE = "FIN";
	public boolean resolu = false;
	public short[][] sudokuAResoudre;


	public Sudoku() {
		sudokuAResoudre = new short[9][9];
	}

	public short[][] getSudokuAResoudre() {
		return sudokuAResoudre;
	}

	public void setSudokuAResoudre(short[][] sudoku) {
		sudokuAResoudre = sudoku;
	}

	public static boolean ligneSaisieEstCoherente(String ligneSaisie) {
		if (ligneSaisie == null || ligneSaisie.trim().isEmpty()) {
			System.out.println("Les coordonnées du chiffre et/ou sa valeur ne peuvent pas être nulles, vides ou remplies avec des espaces");
			return false;
		} else if (ligneSaisie.length() != 3) {
			System.out.println("Les coordonnées du chiffre et/ou sa valeur doit faire 3 caractères");
			return false;
		}

		for(int i = 0; i < 2; i++)  {
			if(!ligneSaisie.substring(i, i + 1).matches("[0-8]")) {
				System.out.println("L'abscisse et l'ordonnée doivent être compris entre 0 et 8, la valeur entre 1 et 9");
				return false;
			}
		}
		if(!ligneSaisie.substring(2,3).matches("[1-9]")) {
			System.out.println("L'abscisse et l'ordonnée doivent être compris entre 0 et 8, la valeur entre 1 et 9");
			return false;
		}

		return true;
	}

	/**
	 * Cette méthode invite l'utilisateur à saisir un ensemble de coordonnées pour initialiser un sudoku à résoudre.
	 * Les coordonnées prennent la forme XYZ avec X correspondant à l'abscisse, Y l'ordonnée et Z la valeur. Seules les
	 * chiffres présents sont à saisir et l'utilisateur doit appuyer sur entrée après chaque saisie.
	 * Lorsqu'il a terminé sa saisie, il entre la chaîne FIN. La fonction remplit au fur et à mesure un tableau de String
	 * comportant les coordonnées des chiffres saisis.
	 *
	 * A noter que pour chaque ligne saisie, sa cohérence est vérifiée en appelant la méthode ligneSaisieEstCoherente
	 * En cas de mauvaise saisie, la saisie ne doit pas être prise en compte et l'utilisateur doit pouvoir saisie une nouvelle ligne
	 * La fonction doit également gérer le cas où l'utilisateur ne rentre rien mais appuye sur Entrée
	 *
	 * @return Un tableau comportant les coordonnées des chiffres présents dans le sudoku à résoudre
	 */
	public static String[] demandeCoordonneesSudoku() {
		String[] saisie = new String[81];
		String ligne;
		Scanner sc = new Scanner(System.in);
		int i = 0;
        ligne = sc.nextLine();
        while(true) {
			if (ligne.equals(FIN_SAISIE)) { break; }
                if (ligneSaisieEstCoherente(ligne)) {
                    saisie[i] = ligne;
                    i++;
                }
                ligne = sc.nextLine();
        }
        sc.close();
		return saisie;
	}

	/**
	 * La méthode prend un tableau de coordonnées de chiffre soud la forme XYZ avec X correspondant
	 * à l'abscisse, Y l'ordonnée et Z la valeur et remplit le tableau sudokuAResoudre avec les bonnes valeurs
	 * au bon endroit. Ex 012, première ligne deuxième colonne, on met la valeur 2. Lorsqu'une valeur nulle est
	 * rencontrée dans le tableau, on arrête le traitement
	 *
	 * Pour passer d'une String à un short, on pourra utiliser la méthode stringToInt(string)
	 *
	 * @param tableauCoordonnees
	 */
	public void remplitSudokuATrous(String[] tableauCoordonnees) {
	    int i; int abscisse; int ordonnee; int valeur;
	    for(i = 0; i < tableauCoordonnees.length; i++) {
	        if(tableauCoordonnees[i] == null || tableauCoordonnees[i].isEmpty()) { break; }
	        abscisse = stringToInt(tableauCoordonnees[i].substring(0,1));
            ordonnee = stringToInt(tableauCoordonnees[i].substring(1,2));
            valeur = stringToInt(tableauCoordonnees[i].substring(2,3));
            sudokuAResoudre[abscisse][ordonnee] = (short) valeur;
        }
    }

	private int stringToInt(String s) {
		return Integer.parseInt(s);
	}

	/**
	 * Cette méthode affiche un sudoku de manière formatée sur la console.
	 * Cela doit ressembler exactement à :
	 * -----------------------
	 * |   8   | 4   2 |   6   |
	 * |   3 4 |       | 9 1   |
	 * | 9 6   |       |   8 4 |
	 *  -----------------------
	 * |       | 2 1 6 |       |
	 * |       |       |       |
	 * |       | 3 5 7 |       |
	 *  -----------------------
	 * | 8 4   |       |   7 5 |
	 * |   2 6 |       | 1 3   |
	 * |   9   | 7   1 |   4   |
	 *  -----------------------
	 *
	 * @param sudoku tableau de short représentant les valeurs d'un sudoku (résolu ou non).
	 * Ce tableau fait 9 par 9 et contient des chiffres de 0 à 9, 0 correspondant à une valeur
	 * non trouvée (dans ce cas, le programme affiche un blanc à la place de 0
	 */
	public void ecrireSudoku(short[][] sudoku) {
		System.out.println("-----------------------");
		int i; int j;
		for(i = 0; i < 9; i++) {
			System.out.print("|");
			for (j = 0; j < 9; j++) {
				System.out.print(" ");
				if(sudoku[i][j] > 0) { System.out.print(sudoku[i][j]); } else { System.out.print(" "); }
				if (j == 2 || j == 5 || j == 8) {
					System.out.print(" |");
				}
			}
			System.out.println();
			if (i == 2 || i == 5 || i == 8) {
				System.out.println(" -----------------------");
			}
		}
    }

	/**
	 * Cette méthode vérifie si un chiffre est autorisé à la position d'abscisse et
	 * d'ordonnée donnés dans le sudoku en appliquant les règles suivantes :
	 *
	 * 1 : Si la valeur est déjà dans la ligne, le chiffre n'est pas autorisé
	 * 2 : Si le valeur est déjà dans la colone, le chiffre n'est pas autorisé
	 * 3 : Si la valeur est est déjà dans la boite, le chiffre n'est pas autorisé
	 *
	 * @param abscisse
	 * @param ordonnee
	 * @param chiffre
	 * @param sudoku
	 * @return
	 */
	public static boolean estAutorise(int abscisse, int ordonnee, short chiffre, short[][] sudoku) {
		// verif abs
		int i; int j;
		for(i = 0; i < 9; i++) {
			if(sudoku[abscisse][i] == chiffre) { return false; }
		}
		// verif ord
		for(i = 0; i < 9; i++) {
			if(sudoku[i][ordonnee] == chiffre) { return false; }
		}

		// verif boite
		int[] boiteAbscisses = new int[3];
		int[] boiteOrdonnees = new int[3];

		if(abscisse == 0 || abscisse % 3 == 0) {
			boiteAbscisses[0] = abscisse;
			boiteAbscisses[1] = abscisse + 1;
			boiteAbscisses[2] = abscisse + 2;
		} else if (abscisse == 1 || abscisse == 4 || abscisse == 7) {
			boiteAbscisses[0] = abscisse - 1;
			boiteAbscisses[1] = abscisse;
			boiteAbscisses[2] = abscisse + 1;
		} else {
			boiteAbscisses[0] = abscisse - 2;
			boiteAbscisses[1] = abscisse - 1;
			boiteAbscisses[2] = abscisse;
		}

		if(ordonnee == 0 || ordonnee % 3 == 0) {
			boiteOrdonnees[0] = ordonnee;
			boiteOrdonnees[1] = ordonnee + 1;
			boiteOrdonnees[2] = ordonnee + 2;
		} else if (ordonnee == 1 || ordonnee == 4 || ordonnee == 7) {
			boiteOrdonnees[0] = ordonnee - 1;
			boiteOrdonnees[1] = ordonnee;
			boiteOrdonnees[2] = ordonnee + 1;
		} else {
			boiteOrdonnees[0] = ordonnee - 2;
			boiteOrdonnees[1] = ordonnee - 1;
			boiteOrdonnees[2] = ordonnee;
		}
		int x; int y;
		for (i = 0; i < 3; i++) {
			for(j = 0; j < 3; j++) {
				x = boiteAbscisses[i];
				y = boiteOrdonnees[j];
				if(sudoku[x][y] == chiffre) { return false; }
			}
		}

		return true;
    }

	public boolean resoudre(int ligne, int colonne, short[][] sudoku) {

		if(colonne == 9) {
			colonne = 0;
			ligne++;
			if(ligne == 9) {
				return true;
			}
		}

		if(sudoku[ligne][colonne] != 0) {
			return resoudre(ligne, colonne + 1, sudoku);
		}

		for(short i = 0; i <= 9; i++) {
			if(estAutorise(ligne, colonne, i, sudoku)) {
				sudoku[ligne][colonne] = i;
				if(resoudre(ligne, colonne + 1, sudoku)) {
					return true;
				}
			}
		}
		sudoku[ligne][colonne] = 0;
		return false;
    }
}