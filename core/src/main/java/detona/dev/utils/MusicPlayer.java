package detona.dev.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {
    private Music menuMusic;

    public MusicPlayer() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("celest_prologue.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    public void stop(){
        menuMusic.stop();
    }

    public void dispose(){menuMusic.dispose();}
}
