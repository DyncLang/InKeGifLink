package jsondemo.livegifdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import jsondemo.livegifdemo.wdiget.OnTouchChangeViewLayout;

public class OnTouchActivity extends AppCompatActivity {

    private ImageView imgTop;
    private ImageView imgContent;
    private ImageView imgBottom;
    private OnTouchChangeViewLayout rootViwe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_touch);
        imgTop = (ImageView) findViewById(R.id.img_top);
        imgContent = (ImageView) findViewById(R.id.img_content);
       // imgBottom = (ImageView) findViewById(R.id.img_bottom);
        rootViwe = (OnTouchChangeViewLayout) findViewById(R.id.activity_on_touch);

        rootViwe.setOnChangeViewListner(new OnTouchChangeViewLayout.OnChangeViewListener() {
            @Override
            public void onNextView() {
                Toast.makeText(OnTouchActivity.this,"下一个视图",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBackView() {
                Toast.makeText(OnTouchActivity.this,"上一个视图",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickShow(View view) {
        Toast.makeText(this,"nihoa",Toast.LENGTH_SHORT).show();
    }

}
