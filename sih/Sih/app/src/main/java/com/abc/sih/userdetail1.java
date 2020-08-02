package com.abc.sih;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class userdetail1 extends Fragment {
    private EditText aboutme,type,station,status,res1,res2,res3;
    private String about_me,type_usr,stat,status_usr,resource1,resource2,resource3;
    private Button next;
    public userdetail1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_userdetail1, container, false);
        aboutme = view.findViewById(R.id.aboutme);
        type = view.findViewById(R.id.type);
        station = view.findViewById(R.id.station);
        status = view.findViewById(R.id.status);
        res1 = view.findViewById(R.id.res1);
        res2 = view.findViewById(R.id.res2);
        res3 = view.findViewById(R.id.res3);
        next = view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about_me = aboutme.getText().toString();
                type_usr = type.getText().toString();
                stat = station.getText().toString();
                status_usr = status.getText().toString();
                resource1 = res1.getText().toString();
                resource2 = res2.getText().toString();
                resource3 = res3.getText().toString();
                if (TextUtils.isEmpty(about_me)||TextUtils.isEmpty(type_usr)||TextUtils.isEmpty(stat)
                        ||TextUtils.isEmpty(status_usr)||TextUtils.isEmpty(resource1)||TextUtils.isEmpty(resource2)
                        ||TextUtils.isEmpty(resource3)){
                    Toast.makeText(getActivity(),"Please Enter all details",Toast.LENGTH_SHORT).show();

                }else {
                    if(type_usr.equals("type1")){
                        submitrfo();

                    }else{
                        submit();
                    }
                }
            }
        });

        return view;
    }

    private void submitrfo() {
        String mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap<String, Object> userdataMap = new HashMap<>();
        userdataMap.put("about_me",about_me);
        userdataMap.put("type",type_usr);
        userdataMap.put("status",status_usr);
        userdataMap.put("station",stat);
        userdataMap.put("resource1",resource1);
        userdataMap.put("resource2",resource2);
        userdataMap.put("resource3",resource3);
        FirebaseDatabase.getInstance().getReference().child("Users").child(mUid).updateChildren(userdataMap);
        FirebaseDatabase.getInstance().getReference().child("rfo").child(stat).child(mUid).setValue(mUid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                ((UserDetails)getActivity()).nextpage();

            }
        });
    }

    private void submit() {
        HashMap<String, Object> userdataMap = new HashMap<>();
        userdataMap.put("about_me",about_me);
        userdataMap.put("type",type_usr);
        userdataMap.put("status",status_usr);
        userdataMap.put("station",stat);
        userdataMap.put("resource1",resource1);
        userdataMap.put("resource2",resource2);
        userdataMap.put("resource3",resource3);
        String mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(mUid).updateChildren(userdataMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        ((UserDetails)getActivity()).selectrfo();
                    }
                });
    }

}
