package com.example.da08.threadasync;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int SET_DONE = 1;

    TextView textView;
    ProgressDialog progress;  // 화면의 진행상태 보여주기

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){   // what을 써줘야 함(정해진 규칙같은거임)
                case SET_DONE :
                    setDone();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run();
            }
        });

        // 정의
        progress = new ProgressDialog(this);  // getBaseContext를 쓰면 MainActivity에 대한 자원만 가져오므로 에러가 발생 
        progress.setTitle("진행중");
        progress.setMessage("룰루랄라");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void setDone(){
        textView.setText("Done");
        progress.dismiss(); // 해제
    }


    // 10초 후에 Done 메세지 호출
    private void run(){
        progress.show(); // 호출
       CustomThread thread = new CustomThread(handler);
        thread.start();
    }
}

class CustomThread extends Thread{  // subThread
    Handler handler;

    public CustomThread(Handler handler){
        this.handler = handler;
    }
    @Override
     public void run(){
            try {
                Thread.sleep(10000);
                // main UI에 현재 Thread가 접근할 수 없으므로 Handler를 통해 접근
               /*
                Message msg = new Message();
                msg.what = MainActivity.SET_DONE;
                handler.sendMessage(msg);

                 =  handler.sendEmptyMessage(MainActivity.SET_DONE);
                */
                handler.sendEmptyMessage(MainActivity.SET_DONE);
            } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }
}