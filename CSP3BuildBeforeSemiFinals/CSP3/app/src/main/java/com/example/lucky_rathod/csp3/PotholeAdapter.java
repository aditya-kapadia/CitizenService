package com.example.lucky_rathod.csp3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Lucky_Rathod on 30-01-2018.
 */

public class PotholeAdapter extends ArrayAdapter<Images> {



    Activity activity;
    int resource;
    List<Images> list;
//    TextView completedGreenIfYes;

/*    DatabaseReference completedItem;
    //Now we store userid when continue button is clicked
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;*/

    public PotholeAdapter(Activity activity, int resource, List<Images> list) {
        super(activity, resource , list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(resource,null);

        ImageView potholeImage = (ImageView) view.findViewById(R.id.getPotholeImagesItems);
        TextView widthDepth = (TextView) view.findViewById(R.id.getWidthDepth);
        TextView location = (TextView) view.findViewById(R.id.getLocation);
        TextView date = (TextView) view.findViewById(R.id.getDateItems);
        TextView time = (TextView) view.findViewById(R.id.getTimeItems);

//        completedGreenIfYes = (TextView) view.findViewById(R.id.completedGreenIfYes);



        widthDepth.setText(list.get(position).getWidthAndHeight());
        date.setText(list.get(position).getImageDate());
        time.setText(list.get(position).getImageTime());
        location.setText(list.get(position).getImageLocationAddress());

        //Now we will display the Image o=in imageView
        Glide.with(activity).load(list.get(position).getImageUrl()).into(potholeImage);


//        Log.e("Posiiton", String.valueOf(position));


        //Now we will make text green of completed if its value is YES FROM DATABASE
//
//        completedItem = FirebaseDatabase.getInstance().getReference().child("users");
//
////        position++;
//
//
//        Log.e("Positon", String.valueOf(position));
//
//
//
//
//            completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(position)).child("completed").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    try {
//                        if (dataSnapshot.getValue() != null) {
//                            try {
//
//                                String completed = (String) dataSnapshot.getValue();
//                                if (completed.equalsIgnoreCase("no")) {
//
//
//                                    completedGreenIfYes.setTextColor(Color.RED);
//
//                                } else {
//
//                                    completedGreenIfYes.setTextColor(Color.GREEN);
//
//                                }
//
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//
//                            Log.e("TAG", " it's null.");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });


        return view;
    }
}
