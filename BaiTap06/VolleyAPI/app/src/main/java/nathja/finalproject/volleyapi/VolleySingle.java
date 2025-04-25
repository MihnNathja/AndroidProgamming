package nathja.finalproject.volleyapi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingle {

    private static VolleySingle mInstance;

    private RequestQueue mRequestQueue;

    private static Context mCtx;

//Don't create constructor

    private VolleySingle (Context context) {

        mCtx = context;

        mRequestQueue = getRequestQueue();

    }

//function to get context

    public static synchronized VolleySingle getInstance(Context context) {

        if (mInstance == null) {

            mInstance = new VolleySingle (context);

        }

        return mInstance;

    }

//RequestQueue function

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {

// getApplicationContext() is key, it keeps you from leaking the

// Activity or BroadcastReceiver if someone passes one in.

            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());

        }

        return mRequestQueue;

    }

//addtoRequest function

    public <T> void addToRequestQueue (Request<T> req) {

        getRequestQueue().add(req);

    }
}
