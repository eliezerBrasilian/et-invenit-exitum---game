package detona.dev.classes;

import java.util.*;

public class Agent {
    public State currentPositionState;
    private Queue<State> frontiersQueue = new LinkedList<>();
    private List<State> visitedList = new ArrayList<>();
    private int steps = 0;
    public boolean hasWon = false;

    public Agent(State newState){
        currentPositionState = newState;
        frontiersQueue.add(newState);
    }

    public void showFrontiers(){
        System.out.print("\n\t>Na Fronteira: ");
        for (State frontier:frontiersQueue){
            System.out.print( "( " + frontier.line + ", " + frontier.column + " ), ");
        }
    }

    public void move(Object[][] matriz){
        //fornecer as fronteiras para o agente
        //cima,  esquerda,  direita e  baixo
        int up = currentPositionState.line - 1;
        int esq = currentPositionState.column - 1;
        int dir = currentPositionState.column + 1;
        int baixo = currentPositionState.line + 1;

        //fila
        if(up >= 0 &&  (matriz[up][currentPositionState.column].equals(1) || matriz[up][currentPositionState.column].equals(3))){
            System.out.println("pode ir para cima");
           if(!frontierContainState(up,currentPositionState.column))
             frontiersQueue.add(new State(up, currentPositionState.column));
        }
        if(esq >= 0 && (matriz[currentPositionState.line][esq].equals(1)|| matriz[currentPositionState.line][esq].equals(3))){
            System.out.println("pode ir para esquerda");
            if(!frontierContainState(currentPositionState.line,esq))
                frontiersQueue.add(new State(currentPositionState.line,esq));
        }
        if(dir <= 11 && (matriz[currentPositionState.line][dir].equals(1)|| matriz[currentPositionState.line][dir].equals(3))){
            System.out.println("pode ir para a direita");
            if(!frontierContainState(currentPositionState.line,dir))
                frontiersQueue.add(new State(currentPositionState.line,dir));
        }
        if(baixo <= 11 && ( matriz[baixo][currentPositionState.column].equals(1)|| matriz[baixo][currentPositionState.column].equals(3))){
            System.out.println("pode ir para baixo");
            if(!frontierContainState(baixo,currentPositionState.column))
                frontiersQueue.add(new State( baixo,currentPositionState.column));
        }

        showFrontiers();

            //remove o primeiro da fronteira
            State firsElement = frontiersQueue.remove();
            System.out.println("\nvai ir para: ( " + firsElement.line + ", " + firsElement.column + " )");

            //e visita-o
            visitedList.add(firsElement);
            //atualiza o agente na matriz
            currentPositionState.line = firsElement.line;
            currentPositionState.column = firsElement.column;
            steps++;
            showSteps();

            matriz[currentPositionState.line][currentPositionState.column] = 'A';

            if(reachedExit(matriz)){
                hasWon = true;
            }

    }

    private boolean frontierContainState(Object line, Object column) {
        for (State frontier:frontiersQueue){
            System.out.print( "( " + frontier.line + ", " + frontier.column + " ), ");
            //(4,11)
            if(Objects.equals(frontier.line,line) && Objects.equals(frontier.column,column)){
                System.out.println("coordenada recebida: " + "( " + frontier.line + ", " + frontier.column + " )");
                System.out.println("ja existe na fronteira");
                return true;
            }
        }
        return false;
    }

    public boolean reachedExit(Object[][] matriz){
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

}
