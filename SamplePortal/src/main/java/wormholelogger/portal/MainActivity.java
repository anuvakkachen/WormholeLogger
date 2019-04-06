package wormholelogger.portal;

import android.app.Activity;
import android.os.Bundle;

import wormholelogger.service.WormholeLogger;


/**
 * A sample portal app which when installed opens the wormhole for the logs
 * from the client app to be transported to the developers firebase
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //todo show user dialog what this portal does

        //todo user auth if needed for firebase

        //initialize portal
        initWormholeLogger();

        setContentView(R.layout.activity_main);
    }


    private void initWormholeLogger() {
        String clientPackage = "wormholelogger.app";
        WormholeLogger.setGoogleServicesResourceId(this, R.raw.google_services);
        WormholeLogger.init(this, clientPackage);
    }
}
