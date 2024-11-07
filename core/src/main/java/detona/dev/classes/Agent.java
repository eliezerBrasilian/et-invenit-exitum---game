package detona.dev.classes;

import java.util.*;

public class Agent {
    public State currentPositionState;
    private Queue<State> frontiersQueue = new LinkedList<>();
    private List<State> visitedList = new ArrayList<>();
    private Stack<State> frontiersStack = new Stack<>();
    private int steps = 0;
    public boolean hasWon = false;
    private SearchType searchType;


    public Agent(State newState,SearchType searchType){
        currentPositionState = newState;
        this.searchType = searchType;

        if (searchType.equals(SearchType.QUEUE))
            frontiersQueue.add(newState);
        else frontiersStack.add(newState);
    }

    public void showFrontiers() {
        System.out.print("\n\t>Na Fronteira: ");

        if (searchType.equals(SearchType.QUEUE)) {
            for (State frontier : frontiersQueue) {
                System.out.print("( " + frontier.line + ", " + frontier.column + " ), ");
            }
        } else {
            for (State frontier : frontiersStack) {
                System.out.print("( " + frontier.line + ", " + frontier.column + " ), ");
            }
        }
    }

    public void move(Object[][] matriz){
        //cima,  esquerda,  direita e  baixo
        int up = currentPositionState.line - 1;
        int esq = currentPositionState.column - 1;
        int dir = currentPositionState.column + 1;
        int baixo = currentPositionState.line + 1;

        //fila
        if(up >= 0 &&  (matriz[up][currentPositionState.column].equals(1) || matriz[up][currentPositionState.column].equals(3))){
            System.out.println("pode ir para cima");
            if (!frontierContainState(up, currentPositionState.column)) {
                if (searchType.equals(SearchType.QUEUE)) {
                    frontiersQueue.add(new State(up, currentPositionState.column));
                } else {
                    frontiersStack.add(new State(up, currentPositionState.column));
                }
            }
        }
        if(esq >= 0 && (matriz[currentPositionState.line][esq].equals(1)|| matriz[currentPositionState.line][esq].equals(3))){
            System.out.println("pode ir para esquerda");
            if (!frontierContainState(currentPositionState.line, esq))
                if (searchType.equals(SearchType.QUEUE)) {
                    frontiersQueue.add(new State(currentPositionState.line, esq));
                } else {
                    frontiersStack.add(new State(currentPositionState.line, esq));
                }
        }
        if(dir <= 11 && (matriz[currentPositionState.line][dir].equals(1)|| matriz[currentPositionState.line][dir].equals(3))){
            System.out.println("pode ir para a direita");
            if (!frontierContainState(currentPositionState.line, dir))
                if (searchType.equals(SearchType.QUEUE)) {
                    frontiersQueue.add(new State(currentPositionState.line, dir));
                } else {
                    frontiersStack.add(new State(currentPositionState.line, dir));
                }
        }
        if(baixo <= 11 && ( matriz[baixo][currentPositionState.column].equals(1)|| matriz[baixo][currentPositionState.column].equals(3))){
            System.out.println("pode ir para baixo");
            if (!frontierContainState(baixo, currentPositionState.column))
                if (searchType.equals(SearchType.QUEUE)) {
                    frontiersQueue.add(new State(baixo, currentPositionState.column));
                } else {
                    frontiersStack.add(new State(baixo, currentPositionState.column));
                }
        }

        showFrontiers();

        if (searchType.equals(SearchType.QUEUE)) {

            State firsElement = frontiersQueue.remove();
            System.out.println("\nvai ir para: ( " + firsElement.line + ", " + firsElement.column + " )");

            //e visita-o
            visitedList.add(firsElement);
            //atualiza o agente na matriz
            currentPositionState.line = firsElement.line;
            currentPositionState.column = firsElement.column;
            steps++;
            showSteps();
        } else {
            State lastElement = frontiersStack.pop();
            System.out.println("\nvai ir para: ( " + lastElement.line + ", " + lastElement.column + " )");

            visitedList.add(lastElement);

            currentPositionState.line = lastElement.line;
            currentPositionState.column = lastElement.column;
            steps++;
            showSteps();
        }

            matriz[currentPositionState.line][currentPositionState.column] = 'A';

            if(reachedExit(matriz)){
                hasWon = true;
            }

    }

    private boolean frontierContainState(Object line, Object column) {
        if (searchType.equals(SearchType.QUEUE)) {
            for (State frontier : frontiersQueue) {
                System.out.print("( " + frontier.line + ", " + frontier.column + " ), ");
                //(4,11)
                if (Objects.equals(frontier.line, line) && Objects.equals(frontier.column, column)) {
                    System.out.println("coordenada recebida: " + "( " + frontier.line + ", " + frontier.column + " )");
                    System.out.println("ja existe na fronteira");
                    return true;
                }
            }
        } else {
            for (State frontier : frontiersStack) {
                System.out.print("( " + frontier.line + ", " + frontier.column + " ), ");
                //(4,11)
                if (Objects.equals(frontier.line, line) && Objects.equals(frontier.column, column)) {
                    System.out.println("coordenada recebida: " + "( " + frontier.line + ", " + frontier.column + " )");
                    System.out.println("ja existe na fronteira");
                    return true;
                }
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

    public int steps(){
        return steps;
    }
}
