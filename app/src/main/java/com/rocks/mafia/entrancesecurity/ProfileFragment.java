package com.rocks.mafia.entrancesecurity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PICK_IMAGE = 1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView image;
    private Button selectImageButton;
    private TextView editName;
    private TextView editContact;
    private TextView editEmail;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment()
    {
        Log.e("Test", "WORKING OR NOT ???");
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2)
    {
        Log.d("Test", "pf");
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Test", "onCreate  ");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d("Test", "onCreateview");
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button editdetails = (Button) view.findViewById(R.id.editDetails);
        final Button savedetails = (Button) view.findViewById(R.id.saveDetails);
            editName=(TextView)view.findViewById(R.id.name);
        editEmail=(TextView)view.findViewById(R.id.email);
        editContact=(TextView)view.findViewById(R.id.contact);

        //MAKE EDITABLE TEXTVIEW ON CLICK edit details

        editdetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                editName.setCursorVisible(true);
                editName.setFocusableInTouchMode(true);
                editName.setInputType(InputType.TYPE_CLASS_TEXT);
                editName.requestFocus();
                savedetails.setVisibility(View.VISIBLE);
            }// end onClick
        });

// savedetails make button disable

        savedetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                editName.setCursorVisible(false);
                editName.setFocusableInTouchMode(false);
                savedetails.setVisibility(View.INVISIBLE);
            }// end onClick
        });



        image = (ImageView)view.findViewById(R.id.uploadImage);
        selectImageButton = (Button)view.findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("Test", "onClickListener ist gestartet");
                Toast.makeText(getActivity().getApplicationContext(), "Test",        Toast.LENGTH_LONG).show();
                selectImageFromGallery();

            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        Log.d("Test", "onAttach");

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

    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}


