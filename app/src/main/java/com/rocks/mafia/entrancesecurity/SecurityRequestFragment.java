package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.extras.Base64;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;
public class SecurityRequestFragment extends Fragment {
    private View view;
    private static final String TAG = SecurityMainActivity.class.getSimpleName();
    private String title;//String for tab title
    public static final String ACCOUNT_SID = "AC5590c4ed74e1ba927995055348e6e3ca";
    public static final String AUTH_TOKEN = "ab215f3f60a90eb52c9d69c38c1f7697";

    private static RecyclerView recyclerView;

    public SecurityRequestFragment()
    {

    }

    public SecurityRequestFragment(String title) {
      this.title = title;//Setting tab title
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Log.e("LISTEN","EDIT");
        Log.e("Mode ","REQUEST");
        view = inflater.inflate(R.layout.security_request_layout, container, false);
       setRecyclerView();
        return view;

    }

             //Setting recycler view
    public void setRecyclerView() {

        recyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items
        SecurityRequestHandler handler=new SecurityRequestHandler(getActivity());
        Log.e("REQUEST ","  : STEP 1");
       ArrayList<SecurityRequestNode> arrayList=handler.getAllSecurityRequest();
        Log.e("REQUEST ","  : STEP 2");
        //  System.out.println("DATATATA : "+ arrayList.get(0).getOutsiderName());
        Collections.reverse(arrayList);
        SecurityRequestRecyclerViewAdapter adapter = new SecurityRequestRecyclerViewAdapter(getActivity(), arrayList);
        Log.e("REQUEST ","  : STEP 3");
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }

}