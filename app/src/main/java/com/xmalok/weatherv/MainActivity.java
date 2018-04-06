package com.xmalok.weatherv;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
public static final String CHOICES="pref_number_of_choice";
public static final String GROUPS="pref_group_to_include";
private boolean phoneDivice=true;
private boolean preferenceChanged=true;



  public   final String LOG_TAG= "mylog";

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    Log.d(LOG_TAG,"activity create");


       PreferenceManager.setDefaultValues(this,R.xml.preference,false);
       PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferenceChangeListener);
       int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

    //   if (screenSize==Configuration.SCREENLAYOUT_SIZE_LARGE||screenSize==Configuration.SCREENLAYOUT_SIZE_XLARGE) {
    //       phoneDivice=false;setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);}
   //        if (phoneDivice){setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}
//
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    }
    @Override
    protected void  onStart(){
        super.onStart();
        FragmentManager fragmentManager=getFragmentManager();

        if (preferenceChanged){
          Fragment1 fragment1=(Fragment1) fragmentManager.findFragmentById(R.id.fragment2);
         fragment1.updateGroup(PreferenceManager.getDefaultSharedPreferences(this));
         fragment1.updateGuessRows(PreferenceManager.getDefaultSharedPreferences(this));
           fragment1.resetQuiz();
            preferenceChanged=false;

        }
    }
      @Override
    public boolean onCreateOptionsMenu(Menu menu){

          getMenuInflater().inflate(R.menu.menu_main, menu);

          return true;
      }
      @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
          Intent settingintent=new Intent(this,SettingsActivity.class);

        startActivity(settingintent);

        return super.onOptionsItemSelected(menuItem);
      }

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener=new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            preferenceChanged=true;

            FragmentManager fragmentManager=getFragmentManager();
           Fragment1 fragment1=(Fragment1) fragmentManager.findFragmentById(R.id.fragment2);
            if (key.equals(CHOICES)){
                fragment1.updateGuessRows(sharedPreferences);
                fragment1.resetQuiz();
            }
            else if (key.equals(GROUPS)){

                Set<String> groups=sharedPreferences.getStringSet(GROUPS,null);
                if (groups!=null&&groups.size()>0 ){
                   fragment1.updateGroup(sharedPreferences);
                    fragment1.resetQuiz();

                }
                else {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    groups.add(getString(R.string.default_group));
                    editor.putStringSet(GROUPS, groups);
                    editor.apply();

                    Toast.makeText(MainActivity.this,R.string.default_group_message,Toast.LENGTH_SHORT).show();

                }

            }
           Toast.makeText(MainActivity.this,R.string.restarting_quiz, Toast.LENGTH_SHORT).show();

        }
   /* private void setSupportActionBar(Toolbar toolbar) {
    }*/
};
public void aldia(){
    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
}

}
