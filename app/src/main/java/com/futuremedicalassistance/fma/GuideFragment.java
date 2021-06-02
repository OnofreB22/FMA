package com.futuremedicalassistance.fma;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuideFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    GuideAdapter guideAdapter;
    ArrayList<Guide> list;

    public GuideFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guide, container, false);

        recyclerView = view.findViewById(R.id.guideRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference("Guides");

        list = new ArrayList<>();
        ReadGuides();

        return view;
    }

    private void ReadGuides() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Guide guide = dataSnapshot.getValue(Guide.class);
                    list.add(guide);

                }
                guideAdapter = new GuideAdapter(getContext(),list);
                guideAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(guideAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}