package  com.xmalok.weatherv;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Fragment1 extends Fragment {

    final String LOG_TAG = "myLogs";
    private final int CHARTERS_IN_QUIZ =8;
   List<String> filenameslist;
    List<String>quizgroupslist;
    Set<String>groupsset;
    String correctanswer;
    int totalguesses;
    int correctanswers;
    int guessrows;
    SecureRandom random;
    Handler handler;
    Animation shakeanimation;
    LinearLayout quizlinearlayout;
    TextView questionnumbertextView;
    ImageView characterimageView;
    LinearLayout[] guesseslinearLayouts;
    TextView answertextView;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "Fragment1 onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Fragment1 onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Fragment1 onCreateView");
View view= inflater.inflate(R.layout.fragment_blank2, container, false);
       filenameslist = new ArrayList<>();
       quizgroupslist=new ArrayList<>();
       random=new SecureRandom();
       handler=new Handler();
       shakeanimation= AnimationUtils.loadAnimation(getActivity(),R.anim.incorrect_shake);
       shakeanimation.setRepeatCount(3);
       quizlinearlayout=(LinearLayout)view.findViewById(R.id.quizLinearLayout);
       questionnumbertextView=(TextView)view.findViewById(R.id.questionNtv);

       guesseslinearLayouts=new LinearLayout[4];
       guesseslinearLayouts[0]=view.findViewById(R.id.row1LinearLayout);
       guesseslinearLayouts[1]=view.findViewById(R.id.row2LinearLayout);
       guesseslinearLayouts[2]=view.findViewById(R.id.row3LinearLayout);
       guesseslinearLayouts[3]=view.findViewById(R.id.row4LinearLayout);


       answertextView=(TextView) view.findViewById(R.id.answerTextView);

       for (LinearLayout row : guesseslinearLayouts ){
           for (int column=0; column<row.getChildCount(); column++){
               Button button=(Button) row.getChildAt(column);
               button.setOnClickListener(buttonclicklistener);

           }
       }
       questionnumbertextView.setText(getString(R.string.question,1,CHARTERS_IN_QUIZ));





         return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "Fragment1 onActivityCreated");
        Log.d(LOG_TAG, "Fragment1 onActivityCreated");
        Log.d(LOG_TAG, "Fragment1 onActivityCreated");
    }

    public void onStart() { Log.d(LOG_TAG, "Fragment1 onStart");
        super.onStart();

    }

    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Fragment1 onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Fragment1 onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "Fragment1 onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "Fragment1 onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Fragment1 onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG, "Fragment1 onDetach");
    }

    public void updateGuessRows(SharedPreferences sharedPreferences) {
        String choices=sharedPreferences.getString(MainActivity.CHOICES,null);Log.d(LOG_TAG, "Fragment1 choices "+choices);
        guessrows=Integer.parseInt(choices)/2;Log.d(LOG_TAG, "Fragment1 guessrows  "+guessrows);


        for (LinearLayout linearLayout:guesseslinearLayouts){
            linearLayout.setVisibility(View.GONE);
        }

        for (int row=0; row<guessrows; row++){
            guesseslinearLayouts[row].setVisibility(View.VISIBLE);
        }



    }

    public void updateGroup(SharedPreferences sharedPreferences) {
        groupsset=sharedPreferences.getStringSet(MainActivity.GROUPS,null);

        Log.d(LOG_TAG, "Fragment1 updateGroup "+groupsset);

    }

    public void resetQuiz() {  Log.d(LOG_TAG, "Fragment1 resetQuiz begin");
        AssetManager assetManager=getActivity().getAssets();
        {
            Log.d(LOG_TAG, "Fragment1 resetQuiz AssetManager");
            filenameslist.clear();
            Log.d(LOG_TAG, "Fragment1 resetQuiz filenameslist");
          try {Log.d(LOG_TAG, "Fragment1 resetQuiz try");
                for (String group : groupsset) { Log.d(LOG_TAG, "Fragment1 resetQuiz for1");
                    String[] paths = assetManager.list(group);Log.d(LOG_TAG, "Fragment1 resetQuiz path"+ paths.toString());
                    for (String path : paths) {Log.d(LOG_TAG, "Fragment1 resetQuiz for2");
                        filenameslist.add(path.replace(".png", ""));
                    }

                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "eror loading images");
                Log.d(LOG_TAG, "Fragment1 catch");
            }
            correctanswers = 0; Log.d(LOG_TAG, "Fragment1 resetQuiz 1");
            totalguesses = 0;Log.d(LOG_TAG, "Fragment1 resetQuiz 2");
            Log.d(LOG_TAG,"Fragment1 resetQuiz filenamelist "+filenameslist);

            quizgroupslist.clear();Log.d(LOG_TAG, "Fragment1 resetQuiz 3");
            int charactercounter = 1;Log.d(LOG_TAG, "Fragment1 resetQuiz 4");
            int numberofcharacters = filenameslist.size();Log.d(LOG_TAG, "Fragment1 resetQuiz 5 "+filenameslist.size());
            while (charactercounter <= CHARTERS_IN_QUIZ) {Log.d(LOG_TAG, "Fragment1 resetQuiz 6");
                int randomindex = random.nextInt(numberofcharacters);Log.d(LOG_TAG, "Fragment1 resetQuiz 7  "+randomindex);
                String filename = filenameslist.get(randomindex);

                if (!quizgroupslist.contains(filename)) {
                    quizgroupslist.add(filename);
                    ++charactercounter;

                }
            }
            Log.d(LOG_TAG,"quizgrouplist "+quizgroupslist);
            loadnextcharacter();
        }}


   private void loadnextcharacter(){
        characterimageView=(ImageView) getView().findViewById(R.id.imageView2);
       String nextimage=quizgroupslist.remove(0);Log.d(LOG_TAG,"loadnextcharacter "+nextimage);
        correctanswer=nextimage;
        answertextView.setText("");

      questionnumbertextView.setText(getString(R.string.question,(correctanswers+1),CHARTERS_IN_QUIZ));
       String group=nextimage.substring(0,nextimage.indexOf('-'));
       Log.d(LOG_TAG,"loadnextcharacter group "+group);
       AssetManager assetManager=getActivity().getAssets();
       try {
    InputStream stream=assetManager.open(group+"/"+nextimage+".png");
Log.d(LOG_TAG,"loadnextcharacter nextimage "+nextimage);
Log.d(LOG_TAG,group+"/"+nextimage+".png");
           Drawable drawable=Drawable.createFromStream(stream,nextimage);

           characterimageView.setImageDrawable(drawable);

      } catch (IOException e) {Log.d(LOG_TAG,"loadnextcharacter  error image load");

      }
       Collections.shuffle(filenameslist);
       int correct=filenameslist.indexOf(correctanswer);
       filenameslist.add(filenameslist.remove(correct));

       for (int row=0; row<guessrows; row++){
           for(int column=0;column<guesseslinearLayouts[row].getChildCount();column++){
               Button newguessbutton=(Button) guesseslinearLayouts[row].getChildAt(column);
               newguessbutton.setEnabled(true);
               String filename=filenameslist.get((row*2)+column);
               String fnl=filename.substring(filename.indexOf("-")+1);
               Log.d(LOG_TAG,"loadnextcharacter fnl "+fnl);
               newguessbutton.setText(fnl);
           }
       }
       int row=random.nextInt(guessrows);
       int column=random.nextInt(2);
       LinearLayout randomrow=guesseslinearLayouts[row];
       String charactername=correctanswer;
       charactername=charactername.substring(charactername.indexOf("-")+1);
       Log.d(LOG_TAG,"loadnextcharacter charactername "+charactername);
       Button randombutton=(Button) randomrow.getChildAt(column);
       randombutton.setText(charactername);


   }



private View.OnClickListener buttonclicklistener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Button guessbutton=(Button) v;
        String guess=guessbutton.getText().toString();
        String answer=correctanswer.substring(correctanswer.indexOf("-")+1);
        ++totalguesses;


        if(guess.equals(answer)){
            correctanswers++;
            answertextView.setText(answer+" !");
            answertextView.setTextColor(getResources().getColor(R.color.correct_answer));
            disablebuttons();


            if(correctanswers==CHARTERS_IN_QUIZ){



                getString(R.string.results,totalguesses,(1000/(double)totalguesses));

AlertDialog.Builder builder=new AlertDialog.Builder(Fragment1.super.getActivity());
String resstring= getString(R.string.results,totalguesses, (800/(double)totalguesses));
builder.setTitle(resstring);
builder.setNegativeButton("ЗАНОВО", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
       dialog.cancel();
       resetQuiz();
    }
});
AlertDialog alertDialog=builder.create();
alertDialog.show();
         // resetQuiz();
                //
                }
else {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },2000);
loadnextcharacter();


            }

        }
else{ characterimageView.startAnimation(shakeanimation);
        answertextView.setText(R.string.incorrect_answer);
        guessbutton.setEnabled(false);
        }

} };
   private void disablebuttons(){
       for (int row=0;row<guessrows;row++){
           LinearLayout linearLayout=guesseslinearLayouts[row];
           for (int i=0;i<linearLayout.getChildCount();i++)
               linearLayout.getChildAt(i).setEnabled(false);

       }

   }
   public void ald(){
       AlertDialog.Builder builder=new AlertDialog.Builder(Fragment1.super.getActivity());
   }

}

