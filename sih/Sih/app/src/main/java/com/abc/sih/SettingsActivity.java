package com.abc.sih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import io.paperdb.Paper;

public class SettingsActivity extends AppCompatActivity {

    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        aSwitch = (Switch) findViewById(R.id.switch1);

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
