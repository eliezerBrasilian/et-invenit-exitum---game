package detona.dev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import detona.dev.Main;

public class SelectPhaseScreen implements Screen {
    private SpriteBatch spriteBatch;
    private Texture bgTexture;
    private Music menuMusic;
    private Stage stage;

    public SelectPhaseScreen(Main game){

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        spriteBatch = new SpriteBatch();

        bgTexture = new Texture("background.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/impact_stone.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        parameter.borderWidth = 1;
        parameter.color = Color.YELLOW;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);

        BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixel

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("celest_prologue.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();

        Table table = new Table();
        table.setFillParent(true);
        table.center(); // Centraliza o conteúdo do Table

        // Carrega o Skin para os elementos de UI
        Skin mySkin = new Skin(Gdx.files.internal("uiskin.json"));

        // Cria o título
        Label title = new Label("Escolha um algoritmo", mySkin);
        title.setColor(Color.WHITE);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font24;

        title.setStyle(labelStyle);

        //meus quadrados

        Button op1 = new TextButton("Busca em largura",mySkin);
        op1.setColor(Color.GRAY);
        Button op2 = new TextButton("Busca em profundidade", mySkin);
        op2.setColor(Color.GRAY);

        //title.setFontScale(3); // Aumenta o tamanho da fonte do título

        // Cria o botão
        Button startButton = new TextButton("Começar", mySkin);
        float r = 183 / 255f;
        float g = 163 / 255f;
        float b = 228 / 255f;
        float a = 1f;
        startButton.setColor(r,g,b,a);
        startButton.setSize(150, 40);

        op1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                op1.setChecked(true);
                op2.setChecked(false);
                op1.setColor(Color.FOREST);
                op2.setColor(Color.GRAY);
            }
        });

        op2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                op2.setChecked(true);
                op1.setChecked(false);
                op2.setColor(Color.FOREST);
                op1.setColor(Color.GRAY);
            }
        });

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuMusic.stop();
                dispose();
               game.setScreen(new Play(game));
            }
        });

        // Adiciona o título e o botão ao Table
        table.padTop(50);
        table.add(title)
            .colspan(3)
            .padBottom(20); // Adiciona o título com um padding inferior para espaçamento
        table.row(); // Move para a próxima linha no Table

        //adicionando meus quadrados
        table.add(op1).width(190).height(100).padRight(15);
        table.add(op2).width(190).height(100);
        table.row();


        // Adiciona o botão Start centralizado
        Container<Button> buttonContainer = new Container<>(startButton);
        buttonContainer.center();
        table.add(buttonContainer).colspan(3).padTop(20).center(); // Centraliza o botão com colspan de 3


        //table.add(startButton).size(150, 40).padTop(40); // Adiciona o botão com tamanho específico

        stage.addActor(table);

        //table.setDebug(true);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
       // Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      spriteBatch.begin();
      spriteBatch.draw(bgTexture,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

      spriteBatch.end();

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int w, int h) {
        stage.getViewport().update(w,h,true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        bgTexture.dispose();
        //font.dispose();
        menuMusic.dispose();
    }
}
