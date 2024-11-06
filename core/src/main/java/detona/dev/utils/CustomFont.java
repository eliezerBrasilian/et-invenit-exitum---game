package detona.dev.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class CustomFont extends FreeTypeFontGenerator{
    public BitmapFont font;
    public FreeTypeFontGenerator.FreeTypeFontParameter props;
    public CustomFont(){
        super(Gdx.files.internal("fonts/Memory Vintage.otf"));
        props = new FreeTypeFontGenerator.FreeTypeFontParameter();
        props.size = 45;
        props.borderWidth = 1;
        props.color = Color.YELLOW;
        props.shadowOffsetX = 3;
        props.shadowOffsetY = 3;
        props.shadowColor = new Color(0, 0.5f, 0, 0.75f);

        font = generateFont(props);
    }

}
