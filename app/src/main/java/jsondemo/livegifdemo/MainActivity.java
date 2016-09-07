package jsondemo.livegifdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import jsondemo.livegifdemo.wdiget.MessageLayout;
import jsondemo.livegifdemo.wdiget.MessageQueue;

public class MainActivity extends AppCompatActivity {

    private MessageQueue mMessageQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageQueue = new MessageQueue(getApplicationContext(), (RelativeLayout) findViewById(R.id.ll_root_viwe));
    }

    public void onClickSend(View view) {

        MessageLayout messageLayout = mMessageQueue.buiidMessagLayout();
        mMessageQueue.addView(messageLayout);

    }
}
