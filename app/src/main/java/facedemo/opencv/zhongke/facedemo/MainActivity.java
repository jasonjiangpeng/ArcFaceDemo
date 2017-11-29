package facedemo.opencv.zhongke.facedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.timg);
        new Thread(){
            @Override
            public void run() {
                System.out.println("=====================");
                FaceUtils.startFace(bitmap,bitmap.getWidth(),bitmap.getHeight());

            }
        }.start();
    }
    public void onclicka(View view){

            new Thread(){
                @Override
                public void run() {
                    System.out.println("=====================");
                    FaceUtils.startFace(bitmap,bitmap.getWidth(),bitmap.getHeight());

                }
            }.start();



    }
}
