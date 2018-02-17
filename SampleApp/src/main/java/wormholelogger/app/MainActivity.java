package wormholelogger.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import wormholelogger.log.Log;


/**
 * A sample app that uses WormholeLogger's {@link Log} to log
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "Touch Down " + ev);
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.w(TAG, "Touch UP ", new Exception("Testing"));
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");

        super.onDestroy();
    }
}
