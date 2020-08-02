package com.abc.sih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class SettingsActivity extends AppCompatActivity {

    Switch aSwitch;
    private Button logt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        aSwitch = (Switch) findViewById(R.id.switch1);
        logt = (Button) findViewById(R.id.logout);

        logt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent start = new Intent(SettingsActivity.this,LoginActivity.class);
                start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(start);
            }
        });

        Paper.init(SettingsActivity.this);

        String language = Paper.book().read("language");
        if(language == null)
            Paper.book().write("language", "en");

        Toast.makeText(SettingsActivity.this,language,Toast.LENGTH_SHORT).show();

if(language.equals("hi"))
{
    aSwitch.setChecked(true);
}


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    Paper.book().write("language","hi");

                }else{
                    Paper.book().write("language","en");

                }
            }
        });
    }

}
