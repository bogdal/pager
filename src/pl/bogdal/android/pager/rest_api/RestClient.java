package pl.bogdal.android.pager.rest_api;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class RestClient {
    private static final String LOG_TAG = "RestClient";

    private ArrayList <NameValuePair> headers;
    private ArrayList<NameValuePair> postParams;
    private String url;

    public RestClient(String url) {
        this.url = url;
        headers = new ArrayList<NameValuePair>();
        postParams = new ArrayList<NameValuePair>();
    }

    private void setHeadersParameters(HttpUriRequest request)
    {
        for(NameValuePair h : headers) {
            request.addHeader(h.getName(), h.getValue());
        }
    }

    public void addPostParam(NameValuePair param) {
        postParams.add(param);
    }

    public JSONObject getJsonObject() {
        JSONObject jsonObj = new JSONObject();

        for(NameValuePair p : postParams) {
            try {
                jsonObj.put(p.getName(), p.getValue());
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONException: " + e);
            }
        }
        return jsonObj;
    }

    public boolean execute() {
        HttpPost request = new HttpPost(url);
        setHeadersParameters(request);

        try {
            StringEntity entity = new StringEntity(getJsonObject().toString(), HTTP.UTF_8);
            entity.setContentType("application/json");
            request.setEntity(entity);
        } catch( UnsupportedEncodingException e ) {
            Log.e( LOG_TAG, "UnsupportedEncodingException: " + e );
            return false;
        }

        HttpClient client = new DefaultHttpClient();
        try {

            HttpResponse httpResponse = client.execute(request);
            Integer responseCode = httpResponse.getStatusLine().getStatusCode();
            String responseMessage = httpResponse.getStatusLine().getReasonPhrase();

            Log.d(LOG_TAG, "Response code: " + responseCode);
            Log.d(LOG_TAG, "Response message: " + responseMessage);

            return responseCode == HttpStatus.SC_OK;

        } catch (ClientProtocolException e)  {
            client.getConnectionManager().shutdown();
            Log.e(LOG_TAG, "ClientProtocolException: " + e);
        } catch (IOException e) {
            client.getConnectionManager().shutdown();
            Log.e(LOG_TAG, "IOException: " + e);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception: " + e);
        }

        return false;
    }
}
