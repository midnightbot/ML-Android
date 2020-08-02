package com.abc.sih;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class AdminProfileFragment extends Fragment {


    TextView report_number,news_number,user_number;
    DatabaseReference mPostDatabase;
    PieChartView pieChartView;
    Long total_posts,user_count;
    DatabaseReference mNewsDatabase,mUserDatabase;

    public AdminProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        report_number = (TextView) view.findViewById(R.id.report_number);
        pieChartView = (PieChartView) view.findViewById(R.id.chart);
        news_number = (TextView) view.findViewById(R.id.news_number);
        user_number = (TextView) view.findViewById(R.id.user_number);

        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");
        mPostDatabase.keepSynced(true);
        mNewsDatabase = FirebaseDatabase.getInstance().getReference().child("news");
        mNewsDatabase.keepSynced(true);
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_count = dataSnapshot.getChildrenCount();
                user_number.setText(""+user_count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mNewsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long news_count = dataSnapshot.getChildrenCount();
                news_number.setText(""+news_count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mPostDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                total_posts = dataSnapshot.getChildrenCount();
                report_number.setText(""+total_posts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mPostDatabase.orderByChild("case_assigned").equalTo("no").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long negcount = dataSnapshot.getChildrenCount();
                List< SliceValue > pieData = new ArrayList<>();
                pieData.add(new SliceValue(negcount, Color.BLUE).setLabel("Not Assigned:"+negcount));
                if(total_posts!=null)
                {
                    pieData.add(new SliceValue(total_posts-negcount, Color.GRAY).setLabel("Assigned:"+(total_posts-negcount)));
                    PieChartData pieChartData = new PieChartData(pieData);
                    pieChartData.setHasLabels(true);
                    pieChartView.setPieChartData(pieChartData);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}