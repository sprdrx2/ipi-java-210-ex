package com.ipiecoles.java.java210;

import java.util.Scanner;

public class Main {

    public static void main(String[] Args) {
        System.out.println(Sudoku.FIN_SAISIE);
        Sudoku s = new Sudoku();
        s.remplitSudokuATrous(Sudoku.demandeCoordonneesSudoku());
        s.ecrireSudoku(s.sudokuAResoudre);
    }

}