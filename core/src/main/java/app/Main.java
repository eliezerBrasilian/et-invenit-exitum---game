package app;

import com.badlogic.gdx.Game;
import app.screens.MenuScreen;

public class Main extends Game {

    @Override
    public void create() {
        this.setScreen(new MenuScreen(this));
    }
}
