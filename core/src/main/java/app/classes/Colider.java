package app.classes;

public class Colider {

    public static boolean canGoUp(int up,State currentPositionState, Object[][] tabuleiro){
        return up >= 0 &&  (tabuleiro[up][currentPositionState.column].equals(1) ||
            tabuleiro[up][currentPositionState.column].equals(3));
    }

    public static boolean canGoLeft(int esq,State currentPositionState, Object[][] tabuleiro){
        return esq >= 0 && (tabuleiro[currentPositionState.line][esq].equals(1)||
            tabuleiro[currentPositionState.line][esq].equals(3));
    }

    public static boolean canGoRight(int dir,State currentPositionState, Object[][] tabuleiro){
        return dir <= 11 && (tabuleiro[currentPositionState.line][dir].equals(1)||
            tabuleiro[currentPositionState.line][dir].equals(3));
    }

    public static boolean canGoDown(int baixo,State currentPositionState, Object[][] tabuleiro){
        return baixo <= 11 && ( tabuleiro[baixo][currentPositionState.column].equals(1)||
            tabuleiro[baixo][currentPositionState.column].equals(3));
    }
}
