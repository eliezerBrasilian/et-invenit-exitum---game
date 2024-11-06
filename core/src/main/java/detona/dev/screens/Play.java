package detona.dev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import detona.dev.Main;
import detona.dev.classes.Agent;
import detona.dev.classes.Matriz;
import detona.dev.classes.SearchType;
import detona.dev.classes.State;

public class Play implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private Sprite sprite;
    Texture agentTexture;
    ShapeRenderer shapeRenderer;

    // Configuração do tamanho do mapa (em tiles) e do tamanho da janela
    private static final float WORLD_WIDTH = 12f; // Largura em tiles
    private static final float WORLD_HEIGHT = 12f; // Altura em tiles
//    private static final int WINDOW_WIDTH = 780;
//    private static final int WINDOW_HEIGHT = 420;
    private static final int WINDOW_WIDTH = 1080;
    private static final int WINDOW_HEIGHT = 720;
    private static final int LINE = 4;
    private static final int COLUMN = 10;

    private Matriz matriz;
    private Agent agent;
    private float timer = 0f;
    private final float MOVE_INTERVAL = 0.1f;
    final float TITLE_SIZE = 1 / 16f;
    private SearchType searchType;
    private Main game;

    public Play(Main game, SearchType searchType){
        this.game = game;
        this.searchType = searchType;
        create();
    }

    public void create() {
        shapeRenderer = new ShapeRenderer();

        agent = new Agent(new State(LINE,COLUMN));
        matriz = new Matriz(agent);
        agent.showSteps();
        // Carrega o mapa
        map = new TmxMapLoader().load("tmap4.tmx");

        // Define o unitScale de acordo com o tamanho dos tiles (16x16 pixels)

        renderer = new OrthogonalTiledMapRenderer(map, TITLE_SIZE);

        // Configura a câmera e o viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Posiciona a câmera no centro do mapa
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();

        // Define o tamanho da janela
        Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);

        spriteBatch = new SpriteBatch();
        // Carrega a textura do sprite e cria o sprite na posição desejada (4, 11)

        agentTexture = new Texture("bucket.png");
        sprite = new Sprite(agentTexture);
        sprite.setSize(1, 1); // Define o tamanho do sprite para 1x1 (corresponde a um tile de 16x16 no mundo)
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
        // Atualiza o viewport e reposiciona a câmera para centralizar
        viewport.update(width, height);
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        // Limpa a tela com uma cor preta
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // renderiza o mapa
        renderer.setView(camera);
        renderer.render();

        timer += delta;
        if(timer >= MOVE_INTERVAL){
            timer = 0;
            matriz.updateAgentPosition();

            if(agent.hasWon){
                System.out.println("Agente venceu!");
            }
        }




        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        float agentX = agent.currentPositionState.column;
        float agentY = WORLD_HEIGHT - agent.currentPositionState.line - 1;

        spriteBatch.draw(agentTexture,agentX,agentY,1,1);
        spriteBatch.end();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // Libera os recursos
        map.dispose();
        renderer.dispose();
        spriteBatch.dispose();
    }
}
