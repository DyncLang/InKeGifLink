package jsondemo.livegifdemo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import jsondemo.livegifdemo.wdiget.MessageLayout;
import jsondemo.livegifdemo.wdiget.MessageQueue;

import static android.media.AudioManager.FLAG_SHOW_UI;

public class MainActivity extends AppCompatActivity {

    private MessageQueue mMessageQueue;

    AudioManager audiomanager;
    private int maxVolume;

    private int currentVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageQueue = new MessageQueue(getApplicationContext(), (RelativeLayout) findViewById(R.id.ll_root_viwe));

        audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    public void onClickSend(View view) {

        MessageLayout messageLayout = mMessageQueue.buiidMessagLayout();
        mMessageQueue.addView(messageLayout);


        startActivity(new Intent(this,OnTouchActivity.class));
    }


    private int getStreamVolume() {
        return audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前值
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_VOLUME_DOWN:

                int streamVolume = getStreamVolume();

                if (streamVolume == 0) {
                    streamVolume = 0;
                } else {
                    streamVolume -= streamVolume;
                }
                audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, FLAG_SHOW_UI );
                return false;

            case KeyEvent.KEYCODE_VOLUME_UP:
                streamVolume = getStreamVolume();

                if (streamVolume == maxVolume) {
                    streamVolume = maxVolume;
                } else {
                    streamVolume += streamVolume;
                }
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        audiomanager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, FLAG_SHOW_UI );
                    }
                });

                return false;
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                // Log.i(TAG, "onKeyDown: ------");

                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
