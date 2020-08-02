package com.abc.sih;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FullCaseViewActivity extends AppCompatActivity {

    String post_id;
    DatabaseReference post_database;

    TextView user_profile_name,upload_time,case_type_details,case_report_details,case_time_details,case_assigned_details;
    TextView case_urgency_details,uploader_name,uploader_mobile,uploader_email,uploader_circlecode;
    CircleImageView user_image;
    ImageView case_image,case_image2,case_image3,uploader_sign;
    LinearLayout other_images;

    String user_name,upd_time,user_dp,case_link,case_link2,case_link3,case_type,case_info,case_time_det,case_assgn_det;
    String case_urg_det,upd_name,upd_mobile,upd_email,upd_circlecode,upd_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_case_view);

        post_id = getIntent().getStringExtra("post_id");
        Toast.makeText(FullCaseViewActivity.this,post_id,Toast.LENGTH_SHORT).show();
        //////
        user_profile_name = (TextView) findViewById(R.id.user_profile_name);
        upload_time = (TextView) findViewById(R.id.upload_time);
        user_image = (CircleImageView) findViewById(R.id.user_profile_image);
        case_image = (ImageView) findViewById(R.id.case_image);
        case_image2 = (ImageView) findViewById(R.id.case_image2);
        case_image3 = (ImageView) findViewById(R.id.case_image3);
        other_images = (LinearLayout) findViewById(R.id.other_images);
        case_type_details = (TextView) findViewById(R.id.case_type_details);
        case_report_details = (TextView) findViewById(R.id.case_report_details);
        case_time_details = (TextView) findViewById(R.id.case_time_details);
        case_assigned_details = (TextView) findViewById(R.id.case_assigned_details);
        case_urgency_details = (TextView) findViewById(R.id.case_urgency_details);
        uploader_name = (TextView) findViewById(R.id.uploader_name);
        uploader_mobile = (TextView) findViewById(R.id.uploader_mobile);
        uploader_email = (TextView) findViewById(R.id.uploader_email);
        uploader_circlecode = (TextView) findViewById(R.id.uploader_circlecode);
        uploader_sign = (ImageView) findViewById(R.id.uploader_sign);
        //////

        post_database = FirebaseDatabase.getInstance().getReference().child("Posts").child(post_id);

        post_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_name = dataSnapshot.child("user_name").getValue().toString();
                Long h = dataSnapshot.child("time").getValue(Long.class);
                GetTimeAgo getTimeAgo = new GetTimeAgo();
                upd_time = getTimeAgo.getTimeAgo(h);

                user_dp = dataSnapshot.child("image").getValue().toString();
                case_link = dataSnapshot.child("link").getValue().toString();
                if(dataSnapshot.child("link1").exists())
                {
                    case_link2 = dataSnapshot.child("link1").getValue().toString();
                    Picasso.with(FullCaseViewActivity.this).load(case_link2).placeholder(R.drawable.default_avatar).into(case_image2);
                }
                else{
                    case_image2.setVisibility(View.INVISIBLE);
                }
                if(dataSnapshot.child("link2").exists())
                {
                    case_link3 = dataSnapshot.child("link2").getValue().toString();
                    Picasso.with(FullCaseViewActivity.this).load(case_link3).placeholder(R.drawable.default_avatar).into(case_image3);
                }
                else{
                    case_image3.setVisibility(View.INVISIBLE);
                }
                if(!dataSnapshot.child("link1").exists() && !dataSnapshot.child("link2").exists())
                {
                    other_images.setVisibility(View.GONE);
                }
                case_type = dataSnapshot.child("type").getValue().toString();
                case_info = dataSnapshot.child("case_details").getValue().toString();
                case_time_det = dataSnapshot.child("time_date_case").getValue().toString();
                case_assgn_det = dataSnapshot.child("case_assigned").getValue().toString();
                if(case_assgn_det.toLowerCase().equals("yes"))
                {
                    case_assigned_details.setText("YES");
                    case_assigned_details.setTextColor(Color.GREEN);
                }
                if(case_assgn_det.toLowerCase().equals("no"))
                {
                    case_assigned_details.setText("NO");
                    case_assigned_details.setTextColor(Color.RED);
                }
                case_urg_det = dataSnapshot.child("likes").getValue().toString();
                upd_name = dataSnapshot.child("uploader_name").getValue().toString();
                upd_mobile = dataSnapshot.child("mobile").getValue().toString();
                upd_email = dataSnapshot.child("email").getValue().toString();
                upd_circlecode = dataSnapshot.child("circle_code").getValue().toString();
                upd_sign = dataSnapshot.child("signature_link").getValue().toString();


                user_profile_name.setText(user_name);
                upload_time.setText(upd_time);
                Picasso.with(FullCaseViewActivity.this).load(user_dp).placeholder(R.drawable.default_avatar).into(user_image);
                Picasso.with(FullCaseViewActivity.this).load(case_link).placeholder(R.drawable.default_avatar).into(case_image);
                case_type_details.setText(case_type);
                case_report_details.setText(case_info);
                case_time_details.setText(case_time_det);
                case_urgency_details.setText(case_urg_det);
                uploader_name.setText(upd_name);
                uploader_mobile.setText(upd_mobile);
                uploader_email.setText(upd_email);
                uploader_circlecode.setText(upd_circlecode);
                Picasso.with(FullCaseViewActivity.this).load(upd_sign).placeholder(R.drawable.default_avatar).into(uploader_sign);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}