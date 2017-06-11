package com.example.darwin.vgbuff.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.darwin.vgbuff.R;
import com.example.darwin.vgbuff.Telemetry;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TelemetryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TelemetryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TelemetryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;
    String url;
    Telemetry telemetry;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TelemetryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TelemetryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TelemetryFragment newInstance(String param1, String param2) {
        TelemetryFragment fragment = new TelemetryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_telemetry, container, false);
        telemetry = new Telemetry();

        final EditText raw = (EditText) view.findViewById(R.id.RawData);

        // Get data from the main page
        final Bundle dataToDetail = this.getArguments();

        // Check if there is data, and get the position of the hero inside the JSON file
        if (dataToDetail != null) {

            // Create loading animation while data is being processed
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.setMessage("Loading...");
                    dialog.setCanceledOnTouchOutside(false);
                    if(!dialog.isShowing()){
                        dialog.show();
                    }
                }
            });

            // New Thread
            new Thread(){
                public void run() {

                    String url = dataToDetail.getString("URL");
                    Log.i("url",url);
                    telemetry.getRawTelemetryData(url);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // remove loading animation
                            dialog.dismiss();
                            Log.i("raw",telemetry.rawTelemetryData);
                            raw.setText(telemetry.rawTelemetryData);


                        }
                    });

                }
            }.start();

        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
