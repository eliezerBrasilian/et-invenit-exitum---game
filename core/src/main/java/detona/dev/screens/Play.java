package detona.dev.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import detona.dev.Main;
import detona.dev.classes.Agent;
import detona.dev.classes.Matriz;
import detona.dev.classes.SearchType;
import detona.dev.classes.State;
import detona.dev.utils.CustomFont;
import detona.dev.utils.MusicPlayer;

public class Play implements Screen {
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private Sprite sprite;
    Texture agentTexture;
    ShapeRenderer shapeRenderer;
    Label placar;
    Label elapsedTimeLabel;
    Stage stage;
    float elapsedTime = 0f;
    Skin mySkin;
    Main game;

    // Configuração do tamanho do mapa (em tiles) e do tamanho da janela
    private static final float WORLD_WIDTH = 12f; // Largura em tiles
    private static final float WORLD_HEIGHT = 12f; // Altura em tiles

    //private static final int WINDOW_WIDTH = 1080;
    //private static final int WINDOW_HEIGHT = 720;
    private static final int LINE = 4;
    private static final int COLUMN = 10;

    private Matriz matriz;
    private Agent agent;
    private float timer = 0f;
    private final float MOVE_INTERVAL = 0.15f;
    final float TITLE_SIZE = 1 / 16f;
    private SearchType searchType;
    private CustomFont customFont;
    MusicPlayer music;

    public Play(Main game, SearchType searchType){
        this.game = game;
        this.searchType = searchType;
        create();
    }

    public void create() {
        music =new MusicPlayer("celest_first_steps.mp3");

       // stage = new Stage(new FitViewport(WINDOW_WIDTH,WINDOW_HEIGHT));
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        agent = new Agent(new State(LINE,COLUMN),searchType);
        matriz = new Matriz(agent);
        agent.showSteps();

        map = new TmxMapLoader().load("tmap4.tmx");

        // Define o unitScale de acordo com o tamanho dos tiles (16x16 pixels)

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, TITLE_SIZE);

        // Configura a câmera e o viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Posiciona a câmera no centro do mapa
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();

        //Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);

        spriteBatch = new SpriteBatch();

        playerSetup();

        labelTopSetup();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(placar).expandX().padTop(10);
        table.add(elapsedTimeLabel).expandX().padTop(10);
        table.row();

        stage.addActor(table);
    }


    private void playerSetup() {
        agentTexture = new Texture("bucket.png");
        sprite = new Sprite(agentTexture);
        sprite.setSize(1, 1); // Define o tamanho do sprite para 1x1 (corresponde a um tile de 16x16 no mundo)
    }

    private void labelTopSetup() {
        mySkin = new Skin(Gdx.files.internal("uiskin.json"));

        //**
        var fontGenerated = new FreeTypeFontGenerator(Gdx.files.internal("fonts/dogicapixel.otf"));
        var props = new FreeTypeFontGenerator.FreeTypeFontParameter();
        props.size = 30;
        props.color = Color.BLACK;

        var font = fontGenerated.generateFont(props);

        //**

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        placar = new Label("Passos percorridos: " + agent.steps(), mySkin);
        elapsedTimeLabel = new Label("Tempo: " + elapsedTime, mySkin);

        placar.setStyle(labelStyle);
        elapsedTimeLabel.setStyle(labelStyle);
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
        // Atualiza o viewport e reposiciona a câmera para centralizar
        viewport.update(width, height,true);
        stage.getViewport().update(width,height,true);
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!agent.hasWon) {
            elapsedTime += delta;
            elapsedTimeLabel.setText("Tempo: " + String.format("%.1f", elapsedTime) + " s");
            placar.setText("Passos percorridos: " + agent.steps());
        }

        // renderiza o mapa
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        timer += delta;

        if (timer >= MOVE_INTERVAL && !agent.hasWon) {
            timer = 0;
            matriz.updateAgentPosition();

            if (agent.hasWon) {
                System.out.println("Agente venceu!");
                showGameOverDialog();
            }
        }

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        float agentX = agent.currentPositionState.column;
        float agentY = WORLD_HEIGHT - agent.currentPositionState.line - 1;
        spriteBatch.draw(agentTexture,agentX,agentY,1,1);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    private void showGameOverDialog() {
        Dialog dialog = new Dialog("Fim de Jogo", mySkin) {
            @Override
            protected void result(Object object) {
                boolean playAgain = (boolean) object;
                if (playAgain) {
                    dispose();
                    game.setScreen(new MenuScreen(game));
                } else {
                    Gdx.app.exit();
                }
            }
        };

        dialog.text("Voce venceu! Deseja jogar novamente?");
        dialog.setSize(WORLD_WIDTH - 50, 400);
        dialog.button("Sim", true);
        dialog.button("Não", false);

        dialog.show(stage);
    }

    @Override
    public void dispose() {
        map.dispose();
        tiledMapRenderer.dispose();
        spriteBatch.dispose();
        music.dispose();
    }

}
