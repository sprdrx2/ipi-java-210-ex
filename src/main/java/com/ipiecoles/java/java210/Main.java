package com.ipiecoles.java.java210;

import java.util.Scanner;

public class Main {

    public static void main(String[] Args) {
        Sudoku s = new Sudoku();
        String[] tab = Sudoku.demandeCoordonneesSudoku();
        s.remplitSudokuATrous(tab);
        s.ecrireSudoku(s.getSudokuAResoudre());
        if(s.resoudre(0,0,s.getSudokuAResoudre())) {
            s.ecrireSudoku(s.getSudokuAResoudre());
        } else {
            System.out.println("Pas de solution.");
        }

    }

}