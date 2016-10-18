package com.scu.drawabletoptextview;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.scu.drawabletoptextview.view.TopRefreshTextView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tesxt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopRefreshTextView button = (TopRefreshTextView) view;
                Drawable drawable = button.getDrawableTop();
                if (!(drawable instanceof Animatable))return;
                try {
                    Method m1 = drawable.getClass().getDeclaredMethod("setFramesDuration",int.class);
                    m1.invoke(drawable,50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!((Animatable) drawable).isRunning()) {
                    ((Animatable) drawable).start();
                } else {
                    ((Animatable) drawable).stop();
                }
            }
        });
    }
}
