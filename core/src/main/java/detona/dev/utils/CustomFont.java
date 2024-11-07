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
        props.color = Color.BLACK;
        props.shadowOffsetX = 1;
        props.shadowOffsetY = 1;
        props.shadowColor = new Color(0, 0.5f, 0, 0.75f);

        font = generateFont(props);
    }

    public CustomFont(String fontPath){
        super(Gdx.files.internal("fonts/" + fontPath));
        setupProps();
    }

    private void setupProps(){
        props = new FreeTypeFontGenerator.FreeTypeFontParameter();
        props.size = 45;
        props.borderWidth = 1;
        props.color = Color.BLACK;
        props.shadowOffsetX = 1;
        props.shadowOffsetY = 1;
        props.shadowColor = new Color(0, 0.5f, 0, 0.75f);

        font = generateFont(props);
    }

    public void updateProps(FreeTypeFontGenerator.FreeTypeFontParameter props){
        this.props = props;
        font = generateFont(props);
    }


}
