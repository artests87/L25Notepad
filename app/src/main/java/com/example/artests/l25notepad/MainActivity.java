package com.example.artests.l25notepad;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        findViewById(R.id.layoutSettingsFragment).setVisibility(View.INVISIBLE);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        mEditText=(EditText)findViewById(R.id.editText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            findViewById(R.id.fragmentMain).setVisibility(View.INVISIBLE);
            findViewById(R.id.layoutSettingsFragment).setVisibility(View.VISIBLE);
            this.setTitle(getString(R.string.action_settings_own));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTextStyle(){
        String regular=sharedPreferences.getString(getString(R.string.pref_style),null);
        if (regular!=null){
            try {
                int typeface= Typeface.NORMAL;
                if (regular.contains(getString(R.string.pref_style_bold))){
                    typeface+=Typeface.BOLD;
                }
                if (regular.contains(getString(R.string.pref_style_italic))){
                    typeface+=Typeface.ITALIC;
                }
                mEditText.setTypeface(null,typeface);
            } catch (NumberFormatException e) {
                Toast.makeText(this,e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void setTextSize(){
        if (sharedPreferences.getString(getString(R.string.pref_size),null)!=null){
            try {
                float textSize = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_size), "20"));
                mEditText.setTextSize(textSize);
            } catch (NumberFormatException e) {
                Toast.makeText(this,e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(findViewById(R.id.layoutSettingsFragment).getVisibility()==View.VISIBLE){
            findViewById(R.id.layoutSettingsFragment).setVisibility(View.INVISIBLE);
            setTextSize();
            setTextStyle();
            this.setTitle(getString(R.string.app_name));
            findViewById(R.id.fragmentMain).setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }
    }

    public SharedPreferences getSharedPreferencesOwn() {
        return sharedPreferences;
    }

    public EditText getmEditText() {
        return mEditText;
    }


}
