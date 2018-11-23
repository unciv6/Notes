package net.micode.notes.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import net.micode.notes.notes.R;
import net.micode.notes.ui.NotesListActivity;

/**
 * Description:
 * Created by peak on 2018/11/23.
 */
public class launcher extends Activity {

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent = new Intent(launcher.this, NotesListActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mHandler.sendMessageDelayed(mHandler.obtainMessage(), 2500);

    }


}
