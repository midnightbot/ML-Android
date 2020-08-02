package com.abc.sih;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Script;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.ChecksumException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CheckpointActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4;

    TextView notes1,notes2,notes3,notes4;

    DatabaseReference user_data;
    String childval;
    DatabaseReference qrdatabase;

    ImageView img1,img2,img3,img4;
    private String link1 = null, link2 = null, link3 = null;
    private ImageView attachment1, attachment2, attachment3;
    private static final int GALLERY_PICK = 1;
    private int choice = 1;
    private ProgressDialog mProgress;
    private ProgressDialog mProgressDialog;
    private StorageReference mImageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkpoint);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        notes1 = (TextView) findViewById(R.id.notes1);
        notes2 = (TextView) findViewById(R.id.notes2);
        notes3 = (TextView) findViewById(R.id.notes3);
        notes4 = (TextView) findViewById(R.id.notes4);

        img1 = (ImageView) findViewById(R.id.tick1);
        img2 = (ImageView) findViewById(R.id.tick2);
        img3 = (ImageView) findViewById(R.id.tick3);
        img4 = (ImageView) findViewById(R.id.tick4);
        mImageStorage = FirebaseStorage.getInstance().getReference();





        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user_data = FirebaseDatabase.getInstance().getReference().child("Checkpoint").child(user_id).child(date);
        user_data.keepSynced(true);



        user_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("C1").getValue()==null)
                {
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                }

                if(dataSnapshot.child("C2").getValue()==null)
                {
                    btn3.setVisibility(View.INVISIBLE);
                    btn4.setVisibility(View.INVISIBLE);
                }

                if(dataSnapshot.child("C3").getValue()==null)
                {

                    btn4.setVisibility(View.INVISIBLE);
                }

                if(dataSnapshot.child("C1").getValue()!=null && dataSnapshot.child("C1time").getValue()!=null)
                {
                    String abc = dataSnapshot.child("C1").getValue().toString();
                    notes1.setText(abc);
                    btn1.setVisibility(View.INVISIBLE);
                    img1.setVisibility(View.VISIBLE);
                }

                if(dataSnapshot.child("C2").getValue()!=null && dataSnapshot.child("C2time").getValue()!=null)
                {
                    String abc = dataSnapshot.child("C2").getValue().toString();
                    notes2.setText(abc);
                    btn2.setVisibility(View.INVISIBLE);
                    img1.setVisibility(View.VISIBLE);
                    img2.setVisibility(View.VISIBLE);
                }

                if(dataSnapshot.child("C3").getValue()!=null && dataSnapshot.child("C3time").getValue()!=null)
                {
                    String abc = dataSnapshot.child("C3").getValue().toString();
                    notes3.setText(abc);
                    btn3.setVisibility(View.INVISIBLE);
                    img1.setVisibility(View.VISIBLE);
                    img2.setVisibility(View.VISIBLE);
                    img3.setVisibility(View.VISIBLE);
                }

                if(dataSnapshot.child("C4").getValue()!=null && dataSnapshot.child("C4time").getValue()!=null)
                {
                    String abc = dataSnapshot.child("C4").getValue().toString();
                    notes4.setText(abc);
                    btn4.setVisibility(View.INVISIBLE);
                    img1.setVisibility(View.VISIBLE);
                    img2.setVisibility(View.VISIBLE);
                    img3.setVisibility(View.VISIBLE);
                    img4.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                childval = "C1";
                qrdatabase = FirebaseDatabase.getInstance().getReference().child("Beats-Record").child(childval).child(date);
                IntentIntegrator intentIntegrator = new IntentIntegrator(CheckpointActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childval = "C2";
                qrdatabase = FirebaseDatabase.getInstance().getReference().child("Beats-Record").child(childval).child(date);
                IntentIntegrator intentIntegrator = new IntentIntegrator(CheckpointActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childval = "C3";
                qrdatabase = FirebaseDatabase.getInstance().getReference().child("Beats-Record").child(childval).child(date);
                IntentIntegrator intentIntegrator = new IntentIntegrator(CheckpointActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childval = "C4";
                qrdatabase = FirebaseDatabase.getInstance().getReference().child("Beats-Record").child(childval).child(date);
                IntentIntegrator intentIntegrator = new IntentIntegrator(CheckpointActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){

            case 1:
                final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

                if(result.getContents().equals(childval))
                {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase.getInstance().getReference().child("Attendance").child(date).child(userid).setValue(userid);

                    final String key = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy ");
                    String formattedDate = dateFormat.format(new Date()).toString();

                    SimpleDateFormat dateFormatt = new SimpleDateFormat("hh.mm.ss aa");
                    String formattedDatet = dateFormatt.format(new Date()).toString();
                    ////updating notification

                    final HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("date",date);
                    userdataMap.put("desc","Your Checkpoint " + childval+ "is successfully marked for the day"+ date);
                    userdataMap.put("type","Checkpoint Admin");
                    userdataMap.put("time",formattedDatet);

                    FirebaseDatabase.getInstance().getReference().child("Notification")
                            .child(userid).child(key).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });



                    AlertDialog.Builder myDialog= new AlertDialog.Builder(CheckpointActivity.this);

                    LayoutInflater inflater= LayoutInflater.from(CheckpointActivity.this);

                    final View myview= inflater.inflate(R.layout.checkpoint_data_nakli,null);

                    myDialog.setView(myview);

                    final AlertDialog dialog=myDialog.create();
                    final EditText title= myview.findViewById(R.id.edt_about);

                    attachment1 = (ImageView) findViewById(R.id.input_attach1);
                    attachment2 = (ImageView) findViewById(R.id.input_attach2);
                    attachment3 = (ImageView) findViewById(R.id.input_attach3);



                    Button btnSave= myview.findViewById(R.id.btn_save);
                    attachment1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            choice = 1;
                            Intent galleryIntent = new Intent();
                            galleryIntent.setType("image/*");
                            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), 2);

                        }
                    });

                    attachment2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            choice = 2;
                            Intent galleryIntent = new Intent();
                            galleryIntent.setType("image/*");
                            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), 2);

                        }
                    });


                    attachment3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            choice = 3;
                            Intent galleryIntent = new Intent();
                            galleryIntent.setType("image/*");
                            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), 2);

                        }
                    });

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(View v) {

                            String mTitle=title.getText().toString().trim();


                            if(TextUtils.isEmpty(mTitle))
                            {
                                title.setError("Required Field..");
                                return;
                            }

                            user_data.child(childval).setValue(mTitle);
                            user_data.child(childval+"time").setValue(ServerValue.TIMESTAMP);

                            qrdatabase.child(userid).child("id").setValue(userid);
                            qrdatabase.child(userid).child("time").setValue(ServerValue.TIMESTAMP);
                            qrdatabase.child(userid).child("attach1").setValue(link1);
                            qrdatabase.child(userid).child("attach2").setValue(link2);
                            qrdatabase.child(userid).child("attach3").setValue(link3);

                            Toast.makeText(CheckpointActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();

                            ///
                            new GlideToast.makeToast(CheckpointActivity.this,"CP Recorded", GlideToast.LENGTHLONG,GlideToast.SUCCESSTOAST).show();

                        }
                    });

                    dialog.show();



                }
                else{
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseDatabase.getInstance().getReference().child("Attendance").child(date).child(userid).setValue("Present");
                    final String key = FirebaseDatabase.getInstance().getReference().child("Notification").push().getKey();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy ");
                    String formattedDate = dateFormat.format(new Date()).toString();

                    SimpleDateFormat dateFormatt = new SimpleDateFormat("hh.mm.ss aa");
                    String formattedDatet = dateFormatt.format(new Date()).toString();
                    ////updating notification

                    final HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("date",date);
                    userdataMap.put("desc","Some Misbehaviour is observed with you account. If it was not you report it to your head office " );
                    userdataMap.put("type","Checkpoint Admin");
                    userdataMap.put("time",formattedDatet);

                    FirebaseDatabase.getInstance().getReference().child("Notification")
                            .child(userid).child(key).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            new GlideToast.makeToast(CheckpointActivity.this,"Wrong Office Code", GlideToast.LENGTHLONG,GlideToast.FAILTOAST).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(CheckpointActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }, 2000); //5seconds
                        }
                    });


                }
                break;
            case 2:


                if (requestCode == 2 && resultCode == RESULT_OK) {

                    Uri imageUri = data.getData();

                    CropImage.activity(imageUri)
                            // .setAspectRatio(1, 1)
                            .start(this);

                    //Toast.makeText(SettingsActivity.this, imageUri, Toast.LENGTH_SHORT).show();
                }

                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult resultt = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {

                        mProgressDialog = new ProgressDialog(CheckpointActivity.this);
                        mProgressDialog.setTitle("Uploading Image..");
                        mProgressDialog.setMessage("Please wait while we upload and process the image");
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        mProgressDialog.show();

                        Uri resultUri = resultt.getUri();

                        //  final String current_user_id = mCurrentUser.getUid();
                        final Date currentTime = Calendar.getInstance().getTime();

                        StorageReference filepath = mImageStorage.child("attachments-qr").child("_"+ currentTime+ ".jpg");

                        filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if(task.isSuccessful()){

                                    mImageStorage.child("attachments-qr").child("_"+currentTime+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            if(choice == 1)
                                            {
                                                mProgressDialog.dismiss();
                                                link1 = uri.toString();
                                                Picasso.with(CheckpointActivity.this).load(link1).into(attachment1);
                                            }
                                            if(choice == 2)
                                            {
                                                mProgressDialog.dismiss();
                                                link2 = uri.toString();
                                                Picasso.with(CheckpointActivity.this).load(link2).into(attachment2);
                                            }
                                            if(choice == 3)
                                            {
                                                mProgressDialog.dismiss();
                                                link3 = uri.toString();
                                                Picasso.with(CheckpointActivity.this).load(link3).into(attachment3);
                                            }


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });
                                    //////////////////



                                }else{

                                    Toast.makeText(CheckpointActivity.this, "Error in uploading", Toast.LENGTH_SHORT).show();
                                    mProgressDialog.dismiss();
                                }
                            }
                        });

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = resultt.getError();
                    }
                }
                break;


        }



        super.onActivityResult(requestCode, resultCode, data);
    }



}