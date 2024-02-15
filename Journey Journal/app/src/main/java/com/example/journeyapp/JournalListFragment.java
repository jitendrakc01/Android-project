package com.example.journeyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class JournalListFragment extends Fragment {

    RecyclerView recyclerView;
    JournalAdapter journalAdapter;
    FloatingActionButton faBtn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JournalListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static JournalListFragment newInstance(String param1, String param2) {
        JournalListFragment fragment = new JournalListFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal_list, container, false);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      //open data entry form from floating action button
        faBtn = view.findViewById(R.id.faBTn);
        faBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), JournalEntry.class));
            }
        });
//fetch data from firebase
        FirebaseRecyclerOptions<JournalModel> options =
                new FirebaseRecyclerOptions.Builder<JournalModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("journals"), JournalModel.class)
                        .build();
        journalAdapter = new JournalAdapter(options);
        recyclerView.setAdapter(journalAdapter);
        return view;

    }



    @Override
    public void onStart() {
        super.onStart();
        journalAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        journalAdapter.stopListening();
    }
}