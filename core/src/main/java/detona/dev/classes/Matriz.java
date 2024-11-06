package detona.dev.classes;

public class Matriz {
    private static final int B = 3;
    private Agent agent;
    public static int VERTICES = 12;

    public void show(){
        for(int i = 0; i < VERTICES; i ++) {
            for (int j = 0; j < VERTICES; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println();
        }
    }

    private final Object[][] matriz = {
            //0,1,2,3,4,5,6,7,8,8,10,11
            {0,0,0,0,0,0,0,0,0,0,0,0},//0
            {0,1,1,1,0,1,1,1,1,1,1,0},//1
            {0,1,0,1,0,1,0,0,0,0,1,0},//2
            {0,0,0,1,0,1,1,1,1,0,1,0},//3
            {0,1,1,1,1,0,0,0,1,0,1,0},//4
            {0,0,0,0,1,0,1,0,1,0,1,0},//5
            {0,1,1,0,1,0,1,0,1,0,1,0},//6
            {0,0,1,0,1,0,1,0,1,0,1,0},//7
            {0,1,1,1,1,1,1,1,1,0,1,0},//8
            {0,0,0,0,0,0,1,0,0,0,1,0},//9
            {B,1,1,1,1,1,1,0,1,1,1,0},//10
            {0,0,0,0,0,0,0,0,0,0,0,0}//11
    };

    public Matriz(Agent agent){
        insertAgent(agent);
    }

    private void insertAgent(Agent agent){
        this.agent = agent;

        State initialState = agent.currentPositionState;
        int column = initialState.column;
        int line = initialState.line;

        matriz[line][column] = 'A';
        show();
    }

    public void updateAgentPosition(){
        if(!agent.hasWon){
            //limpando o caminho anterior onde o agente passou
            State currentPosition = agent.currentPositionState;
            matriz[currentPosition.line][currentPosition.column] = 4;

            agent.move(matriz);
            matriz[agent.currentPositionState.line][agent.currentPositionState.column] = 'A';

            show();
        }
    }
}
