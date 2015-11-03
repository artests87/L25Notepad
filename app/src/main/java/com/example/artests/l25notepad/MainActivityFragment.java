package com.example.artests.l25notepad;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ResourceBundle;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private final String FILENAME = "sample.txt"; // имя файла
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (((MainActivity)getActivity()).getSharedPreferencesOwn().getBoolean(getString(R.string.pref_openmode), false)){
            openFile(FILENAME);
        }
        ((MainActivity)getActivity()).setTextSize();
        ((MainActivity)getActivity()).setTextStyle();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.action_open:
                openFile(FILENAME);
                return true;
            case R.id.action_save:
                saveFile(FILENAME);
                return true;
            default:
                return true;
        }
    }
    private void openFile(String fileName) {
        try {
            InputStream inputStream= getActivity().openFileInput(fileName);
            if (inputStream!=null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder builder=new StringBuilder();
                while ((line=reader.readLine())!=null){
                    builder.append(line+"\n");
                }
                //Toast.makeText(getContext(),builder.toString(), Toast.LENGTH_LONG).show();
                inputStream.close();
                ((MainActivity)getActivity()).getmEditText().setText(builder.toString());
            }
        } catch (Throwable t) {
            Toast.makeText(getContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
    private void saveFile(String fileName) {
        try {
            OutputStream outputStream= getActivity().openFileOutput(fileName, 0);
            if (outputStream!=null){
                OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                //Toast.makeText(getContext(),mEditText.getText().toString(), Toast.LENGTH_LONG).show();
                writer.write(((MainActivity)getActivity()).getmEditText().getText().toString());
                writer.close();
            }
        } catch (Throwable t) {
            Toast.makeText(getContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }


}
