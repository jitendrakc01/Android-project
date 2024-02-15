package com.example.journeyapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class JournalDetailsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String title, description, date, purl;
    public JournalDetailsFragment() {
        // Required empty public constructor
    }

    public JournalDetailsFragment(String title, String description, String date, String purl) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.purl = purl;
    }

    // TODO: Rename and change types and number of parameters
    public static JournalDetailsFragment newInstance(String param1, String param2) {
        JournalDetailsFragment fragment = new JournalDetailsFragment();
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
//set the description in view--displayview of journal
    //here goes other details like longitude , latitide , time , etc.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_journal_details, container, false);
        ImageView imageholder=view.findViewById(R.id.imagegholder);
        TextView titleholder=view.findViewById(R.id.titleholder);
        TextView descholder=view.findViewById(R.id.descholder);
        TextView dateholder=view.findViewById(R.id.dateholder);

        titleholder.setText(title);
        descholder.setText(description);
        dateholder.setText(date);
        Glide.with(getContext()).load(purl).into(imageholder);


        return  view;
    }
    //back to the list fragement when back pressed
    public void onBackPressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new JournalListFragment()).addToBackStack(null).commit();

    }
}