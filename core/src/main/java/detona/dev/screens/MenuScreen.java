package detona.dev.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import detona.dev.Main;
import detona.dev.classes.CustomButton;
import detona.dev.utils.CustomFont;
import detona.dev.utils.MusicPlayer;

public class MenuScreen implements Screen {
    private SpriteBatch spriteBatch;
    private Texture bgTexture;
    private MusicPlayer menuMusic;
    private Stage stage;
    CustomFont customFont;

    public MenuScreen(Main game){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        customFont = new CustomFont();
        spriteBatch = new SpriteBatch();

        bgTexture = new Texture("background.png");
        menuMusic = new MusicPlayer();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Carrega o Skin para os elementos de UI
        Skin mySkin = new Skin(Gdx.files.internal("uiskin.json"));

        Label title = new Label("Et evenit exitum", mySkin);
        title.setColor(Color.WHITE);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = customFont.font;

        title.setStyle(labelStyle);

        CustomButton startButton = new CustomButton("Iniciar", mySkin);

        startButton.onClick(()->{
            menuMusic.stop();
            dispose();
            game.setScreen(new SelectPhaseScreen(game));
        });

        table.add(title).padBottom(20);
        table.row();
        table.add(startButton.get()).size(150, 40);

        stage.addActor(table);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
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
        menuMusic.dispose();
        customFont.dispose();
    }
}
