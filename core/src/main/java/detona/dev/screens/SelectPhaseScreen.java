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
import detona.dev.classes.CustomButton;
import detona.dev.classes.SearchType;
import detona.dev.utils.MusicPlayer;

public class SelectPhaseScreen implements Screen {
    private SpriteBatch spriteBatch;
    private Texture bgTexture;
    private MusicPlayer menuMusic;
    private Stage stage;

    public SelectPhaseScreen(Main game){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        menuMusic = new MusicPlayer();
        spriteBatch = new SpriteBatch();
        bgTexture = new Texture("background.png");

        BitmapFont titleFont = getTitleFont();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Skin mySkin = new Skin(Gdx.files.internal("uiskin.json"));
        Label title = getStylishedLabel(mySkin, titleFont);

        //meus quadrados
        Button op1 = new TextButton("Busca em largura",mySkin);
        op1.setColor(Color.GRAY);
        Button op2 = new TextButton("Busca em profundidade", mySkin);
        op2.setColor(Color.GRAY);

        CustomButton startButton = new CustomButton("Começar", mySkin);

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

        startButton.onClick (()->{
            menuMusic.stop();

            if(op1.isChecked()){
                game.setScreen(new Play(game, SearchType.QUEUE));
            }
           else game.setScreen(new Play(game,SearchType.STACK));
        });

        buildingUi(table, title, op1, op2, startButton);

        stage.addActor(table);
    }

    private static void buildingUi(Table table, Label title, Button op1, Button op2, CustomButton startButton) {
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
        Container<Button> buttonContainer = new Container<>(startButton.get());
        buttonContainer.center();
        table.add(buttonContainer).colspan(3).padTop(20).center(); // Centraliza o botão com colspan de 3
    }

    private static Label getStylishedLabel(Skin mySkin, BitmapFont titleFont) {
        Label title = new Label("Escolha um algoritmo", mySkin);
        title.setColor(Color.WHITE);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = titleFont;

        title.setStyle(labelStyle);
        return title;
    }

    private static BitmapFont getTitleFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/impact_stone.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        parameter.borderWidth = 1;
        parameter.color = Color.YELLOW;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);

        BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixel
        return font24;
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
