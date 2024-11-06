package detona.dev;

import com.badlogic.gdx.Game;
import detona.dev.screens.MenuScreen;

public class Main extends Game {

    @Override
    public void create() {
        this.setScreen(new MenuScreen(this));
    }
}
