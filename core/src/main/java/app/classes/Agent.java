package app.classes;

import java.util.*;

public class Agent {
    public State currentPositionState;
    private int steps = 0;
    public boolean hasWon = false;
    private final SearchAlgorithm searchAlgorithm;

    public Agent(State initialState,SearchAlgorithm searchAlgorithm){
        currentPositionState = initialState;
        this.searchAlgorithm = searchAlgorithm;

        searchAlgorithm.setInitialState(initialState);
        steps++;
    }

    public void showFrontiers() {
        searchAlgorithm.showFrontiers();
    }

    public void showExplored(){searchAlgorithm.showExplored();}

    public void move(Object[][] matriz){
        //cima,  esquerda,  direita e  baixo
        int up = currentPositionState.line - 1;
        int esq = currentPositionState.column - 1;
        int dir = currentPositionState.column + 1;
        int baixo = currentPositionState.line + 1;

        if(Colider.canGoUp(up,currentPositionState,matriz)) searchAlgorithm.add(up, currentPositionState.column);

        if(Colider.canGoLeft(esq,currentPositionState,matriz))searchAlgorithm.add(currentPositionState.line,esq);

        if(Colider.canGoRight(dir,currentPositionState,matriz)) searchAlgorithm.add(currentPositionState.line,dir);

        if(Colider.canGoDown(baixo,currentPositionState,matriz)) searchAlgorithm.add(baixo,currentPositionState.column);

        searchAlgorithm.run(currentPositionState);
        steps++;

        showFrontiers();

        if(reachedExit()){
            hasWon = true;
        }
    }

    public boolean reachedExit(){
        System.out.println("\n\t");

        if(Objects.equals(currentPositionState.line,10 ) &&
                Objects.equals(currentPositionState.column,0 ) ){
            System.out.println("venceu");
           return true;
        }
        return false;
    }

    public void showSteps(){
        System.out.println("Passos no total: " + steps);
    }

    public int steps(){
        return steps;
    }
}
