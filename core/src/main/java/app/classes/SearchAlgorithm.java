package app.classes;

import java.util.*;


public class SearchAlgorithm {
    private SearchType searchType;
    private Queue<State> frontiersQueue = new LinkedList<>();
    private Stack<State> frontiersStack = new Stack<>();
    private List<State> visitedList = new ArrayList<>();

    public SearchAlgorithm(SearchType searchType){
        this.searchType = searchType;
    }

    public void setInitialState(State initialState){
        if (searchType.equals(SearchType.QUEUE))
            frontiersQueue.add(initialState);
        else frontiersStack.add(initialState);

        visitedList.add(initialState);
    }

    public void showExplored(){
        System.out.print("\n\t>Explorados: ");

        visitedList.forEach(i-> System.out.print("( " + i.line + ", " + i.column + " )"));
    }


    public void showFrontiers(){
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

    public void add(int line,int column){
        if (!frontierContainState(line, column)) {
            if (searchType.equals(SearchType.QUEUE)) {
                frontiersQueue.add(new State(line, column));
            } else {
                frontiersStack.add(new State(line, column));
            }
        }
    }

    public void run (State currentPositionState){
        if (searchType.equals(SearchType.QUEUE)) {
            State firsElement = frontiersQueue.remove();
            System.out.println("\nvai ir para: ( " + firsElement.line + ", " + firsElement.column + " )");

            //e visita-o
            visitedList.add(firsElement);
            //atualiza o agente na matriz
            currentPositionState.line = firsElement.line;
            currentPositionState.column = firsElement.column;

        } else {
            State lastElement = frontiersStack.pop();
            System.out.println("\nvai ir para: ( " + lastElement.line + ", " + lastElement.column + " )");

            visitedList.add(lastElement);

            currentPositionState.line = lastElement.line;
            currentPositionState.column = lastElement.column;
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

}
