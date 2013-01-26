package pl.bogdal.android.pager;

import org.apache.http.message.BasicNameValuePair;
import pl.bogdal.android.pager.config.Settings;
import pl.bogdal.android.pager.rest_api.RestClient;


public class Device {

    public static void register(String deviceID, String registrationId) {
        String url = Settings.API_URL + "register/";

        RestClient client = new RestClient(url);

        client.addPostParam(new BasicNameValuePair( "dev_id", deviceID ));
        client.addPostParam(new BasicNameValuePair("reg_id", registrationId));
        client.execute();
    }

    public static void unregister(String deviceID) {
        String url = Settings.API_URL + "unregister/";

        RestClient client = new RestClient(url);

        client.addPostParam(new BasicNameValuePair( "dev_id", deviceID ));
        client.execute();
    }
}
