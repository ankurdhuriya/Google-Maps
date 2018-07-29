package io.github.haanjiankur.googlemaps.app;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import io.github.haanjiankur.googlemaps.receivers.ConnectivityReceiver;
import io.github.haanjiankur.googlemaps.receivers.GPSConnectivityReceiver;
import io.github.haanjiankur.googlemaps.utils.VolleySingleton;


public class AppController extends MultiDexApplication {
    private static final String TAG = AppController.class
            .getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    private ImageLoader mImageLoader;
    public static VolleySingleton volleyQueueInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        instantiateVolleyQueue();

    }


    public void instantiateVolleyQueue() {
        volleyQueueInstance = VolleySingleton.getInstance(getApplicationContext());
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public void setGpsConnectivityListener(GPSConnectivityReceiver.GPSConnectivityReceiverListener listener){
        GPSConnectivityReceiver.gpsConnectivityReceiverListener=listener;
    }
}