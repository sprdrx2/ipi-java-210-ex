package com.ipiecoles.java.java210;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Sudoku {
	public static final String FIN_SAISIE = "FIN";
	
	private short[][] sudokuAResoudre;
	private boolean resolu = false;
	
	public Sudoku() {
		setSudokuAResoudre(new short[9][9]);
	}

	public short[][] getSudokuAResoudre() {
		return sudokuAResoudre;
	}

	public void setSudokuAResoudre(short[][] sudokuAResoudre) {
		this.sudokuAResoudre = sudokuAResoudre;
	}
	
	/**
	 * Cette méthode vérifie la cohérence de la ligne saisie par l'utilisateur.
	 * 
	 * La ligne ne doit pas être nulle ou vide, ou remplie d'espaces
	 * La ligne doit faire exactement 3 caractères
	 * La ligne doit avoir le format XYZ ou X et Y sont compris entre 0 et 8, et Y entre 1 et 9
	 * 
	 * @param ligneSaisie la ligne saisie par l'utilisateur
	 * @return true si la ligne saisie est cohérente, false sinon
	 */
	public boolean ligneSaisieEstCoherente(String ligneSaisie) {
		//Ligne nulle ou vide
		if(ligneSaisie == null || ligneSaisie.trim().equals("")) {
			System.out.println("Les coordonnées du chiffre et/ou sa valeur ne peuvent pas être nulles, vides ou remplies avec des espaces");
			return false;
		}
		//Ligne ne faisant pas 3 caractères
		else if(ligneSaisie.length() != 3) {
			System.out.println("Les coordonnées du chiffre et/ou sa valeur doit faire 3 caractères");
			return false;
		}
		//Vérification de la validité des coordonnées
		else if(!ligneSaisie.substring(0, 1).matches("[0-8]") || 
				!ligneSaisie.substring(1, 2).matches("[0-8]") || 
				!ligneSaisie.substring(2, 3).matches("[1-9]")) 
		{
			System.out.println("L'abscisse et l'ordonnée doivent être compris entre 0 et 8, la valeur entre 1 et 9");
			return false;
		}
		//Si aucun des tests n'a échoué, c'est valide
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
	public String[] demandeCoordonneesSudoku() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Bonjour et bienvenue dans le résolveur de Sudoku !");
		System.out.println("Veuillez renseigner les coordonnées de chaque chiffre présent en appuyant sur ENTREE " 
				+ "entre chaque coordonnée (abscisse suivie de l'ordonnée suivie de la valeur) : " 
				+ "Exemple : 065 (première ligne, sixième colonne, valeur 5). Entrez FIN lorsque vous avez terminés");
		
		//Variable qui contient la ligne saisie par l'utilisateur
		String ligneSaisie = null;
		
		//Un sudoku fait 9 par 9, soit potentiellement 81 valeurs à remplir
		//même si remplir toutes les valeurs n'est pas très intéressant...
		String[] tableauCoordonnees = new String[81];
		
		//Initialisation de l'indice permettant de remplir le tableau de coordonnées
		int indiceTableauCoordonnees = 0;
		do {
			//Lecture de la saisie utilisateur
			try {
				ligneSaisie = scanner.nextLine();
			}
			catch (NoSuchElementException e) {
				//L'utilisateur a juste fait entrée, on sort de la boucle
				System.out.println("L'utilisateur n'a rien saisi !");
				break;
			}
			if(ligneSaisie.equals(FIN_SAISIE)) {
				//On sort de la boucle si l'utilisateur saisit FIN
				break;
			}
			else if(ligneSaisieEstCoherente(ligneSaisie)) {
				//Si la ligne est cohérente, on l'insère dans le tableau des coordonnées
				tableauCoordonnees[indiceTableauCoordonnees++] = ligneSaisie;
			}
			else {
				//Ligne incohérente, on demande à l'utilisateur de saisie une nouvelle valeur
				System.out.println("Les coordonnées du chiffre et/ou sa valeur ne sont pas cohérents. Merci de réessayer");
			}
		} while (!ligneSaisie.equals(FIN_SAISIE) && indiceTableauCoordonnees < 81);
		
		//Fermeture de la ressource
		scanner.close();
		
		return tableauCoordonnees;
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
		//On parcourt le tableau de coordonnées
		for (int n = 0; n < tableauCoordonnees.length; ++n) 
		{
			//Si une valeur nulle est rencontrée, on sort de la boucle
			if(tableauCoordonnees[n] == null) {
				break;
			}
		    short abscisse = (short)stringToInt(tableauCoordonnees[n].substring(0,1));   
		    short ordonnee = (short)stringToInt(tableauCoordonnees[n].substring(1,2));   
		    short valeur = (short)stringToInt(tableauCoordonnees[n].substring(2,3)); 
		    sudokuAResoudre[abscisse][ordonnee] = valeur;
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
		String chaineSudoku = "";
		for (int i = 0; i < 9; ++i) {
		    if (i % 3 == 0){
		    	chaineSudoku += " -----------------------\n";
			}
		    for (int j = 0; j < 9; ++j) {
				if (j % 3 == 0) {
					chaineSudoku += "| ";
				}
				chaineSudoku += sudoku[i][j] == 0
						 ? " "
						 : Integer.toString(sudoku[i][j]);
				
				chaineSudoku += " ";
		    }
		    chaineSudoku += "|\n";
		}
		chaineSudoku += " -----------------------";
		System.out.println(chaineSudoku);
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
	public boolean estAutorise(int abscisse, int ordonnee, short chiffre, short[][] sudoku) {
		for (int k = 0; k < 9; ++k) { // 
			//Si la valeur est déjà dans la ligne, le chiffre n'est pas autorisé
			if (chiffre == sudoku[k][ordonnee]){
				return false;
			}
		}
		for (int k = 0; k < 9; ++k) { // col
			//Si le valeur est déjà dans la colone, le chiffre n'est pas autorisé
			if (chiffre == sudoku[abscisse][k]){
				return false;
			}
		}
			
		//Si la valeur est est déjà dans la boite, le chiffre n'est pas autorisé
		int boxRowOffset = (abscisse / 3)*3;
		int boxColOffset = (ordonnee / 3)*3;
		for (int k = 0; k < 3; ++k) { // box
			for (int m = 0; m < 3; ++m){
				//Si la valeur est déjà dans le carré, le chiffre n'est pas autorisé
				if (chiffre == sudoku[boxRowOffset+k][boxColOffset+m]){
					return false;
			    }
			}
	    }
	
		return true; // Pas de violations, donc c'est autorisé
    }
	
	/**
	 * 
	 * @param abscisse
	 * @param ordonnee
	 * @param sudoku
	 * @return
	 */
	public boolean resoudre(int abscisse, int ordonnee, short[][] sudoku) {
		//Pour commencer, on teste si on est arrivé au bout de la ligne, si c'est
		//le cas, on remet l'abscisse à 0 et on augmente l'ordonnée mais si on est 
		//arrivé à 9, on retourne true (on a traité tout le sudoku)
		if (abscisse == 9) {
			//Si on est arrivé au bout de la ligne, on remet l'abscisse à 0
		    abscisse = 0;
		    //Et on augmente l'ordonnée
		    if (++ordonnee == 9) {
		    	//On sort de la méthode si on dépasse la dernière colonne
		    	return true; 
			}
		}
		
		//On passe les éléments déjà remplis en incrémentant l'abscisse
		if (sudoku[abscisse][ordonnee] != 0) {
			//Pour les autres on appelle la méthode de résolution
		    return resoudre(abscisse+1,ordonnee,sudoku);
		}
		
		//Sinon, on essaye chaque valeur dans la case en appelant la méthode estAutorise
		//Si c'est le cas, on met cette valeur dans le sudoku et on appelle de 
		//nouveau la méthode de résolution en incrémentant l'abscisse et si
		//cette dernière retourne true, on retourne true.
		for (short val = 1; val <= 9; ++val) {
		    if (estAutorise(abscisse,ordonnee,val,sudoku)) {  
				sudoku[abscisse][ordonnee] = val;       
				if (resoudre(abscisse+1,ordonnee,sudoku)) {
					return true;
			    }
			}
		}
		//Si aucune valeur n'est autorisée, on remet la valeur 0
		//dans le sudoku
		//et on fait machine arrière en retournant false
		sudoku[abscisse][ordonnee] = 0;
		return false;
    }
}
