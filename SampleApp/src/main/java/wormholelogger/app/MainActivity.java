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

        initLogger();

        setContentView(R.layout.activity_main);
    }

    /**
     * Initialize wormhole logger
     */
    private void initLogger() {
        Log.init(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Touch " + ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");

        //destroy logger
        Log.destroy();

        Log.d(TAG, "Destroyed");
        super.onDestroy();
    }
}
