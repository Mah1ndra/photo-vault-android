package com.secure.calculatorp.ui.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.PrecomputedText;

import com.secure.calculatorp.di.ActivityContext;

import javax.xml.transform.Result;

/**
 * Created by zakir on 11/01/2019.
 */

public abstract class BaseAsyncTask<Params, Progress, Result>
        extends AsyncTask<Params, Progress, Result> {

    private AsyncCallback asyncCallback;

    public interface AsyncCallback {
        void onStart();
        void onStop();
    }

    public BaseAsyncTask(AsyncCallback asyncCallback) {
        this.asyncCallback = asyncCallback;
    }

    @Override
    protected void onPreExecute() {
        asyncCallback.onStart();
    }

    @Override
    protected void onPostExecute(Result result) {
        asyncCallback.onStop();
    }
}
