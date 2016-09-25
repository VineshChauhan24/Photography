package com.infiniteloops.photographer.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infiniteloops.photographer.Activity.MainActivity;
import com.infiniteloops.photographer.R;
import com.infiniteloops.photographer.ReUseablity;
import com.infiniteloops.photographer.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Signup_Fragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String TAG = Signup_Fragment.class.getSimpleName();

        //Signin button
        private ProgressDialog pDialog;
        private static final String ARG_SECTION_NUMBER = "section_number";
        @Bind(R.id.profile)
        ImageView profile;
        @Bind(R.id.name)
        EditText name;
        @Bind(R.id.email)
        EditText email;
        @Bind(R.id.password)
        EditText password;
        @Bind(R.id.sighnup)
        AppCompatButton sighnup;

        public Signup_Fragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Signup_Fragment newInstance(int sectionNumber) {
            Signup_Fragment fragment = new Signup_Fragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.signup_fragment, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            pDialog = new ProgressDialog(getActivity());
            pDialog.setCancelable(false);

            ButterKnife.bind(this, rootView);
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.unbind(this);
        }

        @OnClick(R.id.sighnup)
        public void onClick() {
            registerUser(name.getText().toString(),email.getText().toString(),password.getText().toString(),"");

        }

        public void successLogin(){
            startActivity(new Intent(getContext(), MainActivity.class));
            SessionManager sm = new SessionManager(getContext());
            sm.setLogin(true);

        }
        private void registerUser(final String name, final String email, final String password,final String user_avatar) {
            // Tag used to cancel the request
            String tag_string_req = "req_register";

            pDialog.setMessage("Registering ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    ReUseablity.REGISTER_USER, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Register Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        int error = jObj.getInt("err-code");
                        if (error == 0) {
                            // User successfully stored in MySQL
                            // Now store the user in sqlite
                            ReUseablity.showToast(getContext(), "Registration Successfull :");
                            SharedPreferences.Editor ed = ReUseablity.editSharedPrefValue(getContext());
                            //ed.putString(ReUseablity.USER_ID, jObj.getString("user"));
                            ed.commit();
                            // Launch login activity
                            successLogin();
                            getActivity().finish();
                        } else {

                            // Error occurred in registration. Get the error
                            // message
                            String errorMsg = jObj.getString("message");
                            ReUseablity.showToast(getContext(),
                                    errorMsg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", "Registration Error: " + error.getMessage());
                    ReUseablity.showToast(getContext(),
                            error.getMessage());
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    SharedPreferences spf = ReUseablity.getSharedPrefValue(getContext());
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("profile", user_avatar);
                    params.put("method", "register_app_user");
                    params.put("token",spf.getString(ReUseablity.LOGIN_TOKEN,"asna"));
                    return params;
                }
            };

            // Adding request to request queue
            //Idea_Application.getInstance().addToRequestQueue(strReq, tag_string_req);
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(strReq);
        }
        //
        private void showDialog() {
            if(!((Activity) getContext()).isFinishing())
            {
                //show dialog
                if (!pDialog.isShowing())
                    pDialog.show();
            }
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }
