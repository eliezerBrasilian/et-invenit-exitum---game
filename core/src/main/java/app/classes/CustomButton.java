package app.classes;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CustomButton extends Button {
    private Button button;
    public CustomButton(String text, Skin mySkin){
        button = new TextButton(text,mySkin);
        float r = 183 / 255f;
        float g = 163 / 255f;
        float b = 228 / 255f;
        float a = 1f;
        button.setColor(r,g,b,a);
        button.setSize(150, 40);
    }

    public void onClick(CallbackFunction cf){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cf.execute();
            }
        });
    }

    public Button get(){
        return button;
    }
}
