package com.example.lucky_rathod.csp3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Lucky_Rathod on 02-02-2018.
 */

public class ImageInfoPerItemFragment extends Fragment {

    ImageView final_captured_image;
    TextView output,output_widthAndDepth,final_imageDate,final_imageTime,final_location_address,reported,completed,comleted_or_not_item_text;
    TextView completionDate,completionTime,completedBy,feedback,verified_text;
    EditText feedback_text;
    Button okButton,resendButton,onBack;

    String imageUrl;


    String imageDate,imageTime,imageLocationAddress,imageLocationValues,
            widthAndHeight,outputResend,completedResend="no",verifiedResend="no",feedbackResend="No Feedback";
    int positon_of_item;

    DatabaseReference completedItem;
    //Now we store userid when continue button is clicked
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    String feedbackEnteredByUser="No Feedback";

    String completedByResend="Mastek Ngo",completedDateResend ="Feb 17, 2018",completedTimeResend="no";

    String user_id;

    Button getDirections;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity())
                .setActionBarTitle("Pothole Issue Status");
        return  inflater.inflate(R.layout.image_info_per_item,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final_captured_image = view.findViewById(R.id.final_captured_image_item);
        output_widthAndDepth = view.findViewById(R.id.widthAndDept_image_info_perItem);

        final_imageDate = view.findViewById(R.id.date_image_info_perItem);
        final_imageTime = view.findViewById(R.id.time_image_info_perItem);
        final_location_address = view.findViewById(R.id.location_image_info_perItem);
        comleted_or_not_item_text = view.findViewById(R.id.completed_or_not_text);
        completed = view.findViewById(R.id.completed_image_info_perItem);

        completionDate = view.findViewById(R.id.completionDate);
        completionTime = view.findViewById(R.id.completionTime);
        completedBy = view.findViewById(R.id.comletedBy);

        user_id = currentFirebaseUser.getEmail();

        feedback = view.findViewById(R.id.feedback);
        feedback_text = view.findViewById(R.id.feedback_text);

        okButton = view.findViewById(R.id.ok_button);
        resendButton = view.findViewById(R.id.resend_button);
//        onBack = view.findViewById(R.id.onBack);

        verified_text = view.findViewById(R.id.verfied_text);

        getDirections = view.findViewById(R.id.get_directions);

       // send_button = view.findViewById(R.id.send_button_item);


        Bundle bundle=getArguments();

        //Extract all the values from bundle which has been received

        imageDate = bundle.getString("imageDate");
        imageTime = bundle.getString("imageTime");
        imageLocationAddress = bundle.getString("imageLocationAddress");
        imageLocationValues = bundle.getString("imageLocationValues");
        widthAndHeight = bundle.getString("widthAndHeight");
        outputResend = bundle.getString("output");

        final_imageDate.setText(String.valueOf(bundle.getString("imageDate")));
        final_imageTime.setText(String.valueOf(bundle.getString("imageTime")));
        final_location_address.setText(String.valueOf(bundle.getString("imageLocationAddress")));
        output_widthAndDepth.setText(String.valueOf(bundle.getString("widthAndHeight")));
        imageUrl = bundle.getString("imageUrl");

        positon_of_item = bundle.getInt("position");
        positon_of_item++;
        Log.d("Exact Position",String.valueOf(positon_of_item));

        Log.d("Positon of Item " , String.valueOf(positon_of_item));

        //Here we will fetch the download url from firebase database and load it into ImageView
//        Glide.with(getActivity()).load(new File(String.valueOf(imageDownloadUri))).into(final_captured_image);
        Picasso.with(getActivity()).load(imageUrl).into(final_captured_image);


        //Now we will check the value of key completed in firebase if it is true
        //then we will display the info as completed date completed time etc


        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f", imageLocationValues);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);*/
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://maps.google.co.in/maps?q=" + imageLocationAddress));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        completedItem = FirebaseDatabase.getInstance().getReference().child("users");


        completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).child("completed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        try {
                         String output_of_comleted = (String) dataSnapshot.getValue();
                            Log.e("Output value", "" + output_of_comleted); // your name values you will get here
                            if(output_of_comleted.equalsIgnoreCase("no")){
                                comleted_or_not_item_text.setText("Reported Pothole has not been Completed yet..!");
                                comleted_or_not_item_text.setTextColor(Color.RED);

                                feedback.setVisibility(View.INVISIBLE);
                                feedback_text.setVisibility(View.INVISIBLE);
                                okButton.setVisibility(View.INVISIBLE);
                                resendButton.setVisibility(View.INVISIBLE);
                                verified_text.setVisibility(View.INVISIBLE);



                            }
                            else{
                                comleted_or_not_item_text.setText("Reported Pothole has been Completed ..! ");
                                comleted_or_not_item_text.setTextColor(Color.parseColor("#00796B"));
                                completed.setTextColor(Color.parseColor("#00796B"));


                                //Now we will retrieve completion Date , time and completedBy from database
                                completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).child("completedDate").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            if (dataSnapshot.getValue() != null) {
                                                try {
                                                    String completionDateOutput = (String) dataSnapshot.getValue();
                                                    completionDate.setText("Completion Date    :- "+completionDateOutput);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                completionDate.setText("Completion Date    :- Feb 14 , 2018");
                                                Log.e("TAG", " it's null.");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).child("completedTime").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            if (dataSnapshot.getValue() != null) {
                                                try {
                                                    String completionTimeOutput = (String) dataSnapshot.getValue();
                                                    completionTime.setText("Completion Time   :- "+completionTimeOutput);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                completionTime.setText("Completion Time   :- 2 PM");
                                                Log.e("TAG", " it's null.");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).child("completedBy").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            if (dataSnapshot.getValue() != null) {
                                                try {
                                                    String completedByOutput = (String) dataSnapshot.getValue();
                                                    completedBy.setText("Completion By       :- "+completedByOutput);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                completedBy.setText("Completed By       :- Mastek NGO");
                                                Log.e("TAG", " it's null.");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).child("verified").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            if (dataSnapshot.getValue() != null) {
                                                try {
                                                    String verifiedOutput = (String) dataSnapshot.getValue();
                                                    if(verifiedOutput.equalsIgnoreCase("no")){
                                                        okButton.setEnabled(true);
                                                        okButton.setAlpha(1.0f);
                                                        //Now we will extract the string from feedback and store it in firebase database
                                                        okButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {


                                                                feedbackEnteredByUser = feedback_text.getText().toString();
                                                                HashMap<String, Object> result = new HashMap<>();
                                                                result.put("verified", "yes");
                                                                result.put("feedBack",feedbackEnteredByUser);
                                                                completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).updateChildren(result);
                                                                Toast.makeText(getActivity(), "Thank you for your Feedback", Toast.LENGTH_SHORT).show();
                                                                feedbackEnteredByUser ="";
                                                                verified_text.setText("Pothole has Been Verified .. !");
                                                                verified_text.setTextColor(Color.parseColor("#00796B"));
                                                                resendButton.setEnabled(false);
                                                                resendButton.setAlpha(0.7f);



                                                            }
                                                        });

                                                    }
                                                    else {


                                   /*                     //Now here completed is yes and verfied is yes
                                                        //so we will print that Your pothole is successfully verified
                                                        verified_text.setText("Pothole has been Verified and Completed succesfully ..!");
                                                        verified_text.setTextColor(Color.GREEN);
                                                        okButton.setEnabled(false);
                                                        resendButton.setEnabled(false);*/


                                                        //Now we will retrieve the values of verfied.completed and resend
                                                        //if completed is yes and resend is yes and verified is no then we will
                                                        //print Pothole resend successfully
                                                        completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {


                                                                Images image = dataSnapshot.getValue(Images.class);

                                                                String retreivedCompleted = image.getCompleted();
                                                                String retreivedResend =  image.getResend();
                                                                String retreivedVerified =  image.getVerified();

                                                                if(retreivedCompleted.equalsIgnoreCase("yes")&&retreivedResend.equalsIgnoreCase("yes")&&retreivedVerified.equalsIgnoreCase("no")){
                                                                    verified_text.setText("Pothole has been Resend  succesfully ..!");
                                                                    verified_text.setTextColor(Color.parseColor("#00796B"));
                                                                    okButton.setAlpha(0.7f);
                                                                    okButton.setEnabled(false);
                                                                    resendButton.setAlpha(0.7f);
                                                                    resendButton.setEnabled(false);
                                                                }
                                                                else if(retreivedCompleted.equalsIgnoreCase("yes") && retreivedVerified.equalsIgnoreCase("yes")&&retreivedResend.equalsIgnoreCase("no")){
                                                                    //Now here completed is yes and verfied is yes
                                                                    //so we will print that Your pothole is successfully verified
                                                                    verified_text.setText("Pothole has been Verified and Completed succesfully ..!");
                                                                    verified_text.setTextColor(Color.parseColor("#00796B"));
                                                                    okButton.setAlpha(0.7f);
                                                                    okButton.setEnabled(false);
                                                                    resendButton.setAlpha(0.7f);
                                                                    resendButton.setEnabled(false);
                                                                }
                                                                else {
                                                                    Toast.makeText(getActivity(), "Error while retreiving image data !", Toast.LENGTH_SHORT).show();
                                                                }

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });





                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {

                                                Log.e("TAG", " it's null.");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                                completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).child("resend").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            if (dataSnapshot.getValue() != null) {
                                                try {
                                                    String resendOutput = (String) dataSnapshot.getValue();
                                                    if(resendOutput.equalsIgnoreCase("no")){

                                                        resendButton.setEnabled(true);
                                                        resendButton.setAlpha(1.0f);

                                                        resendButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {

                                                                completedItem.child(currentFirebaseUser.getUid()).child("images").child("resendImages").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        long imageCounterPotholes =  dataSnapshot.getChildrenCount();

                                                                        //Now we will store these counter in sharedPreferences beacause if user close and opens the app then from these value
                                                                        //image should be uploaded
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.putLong("ImageCounter",imageCounter);
//                                    editor.commit();


                                                                        HashMap<String, Object> result = new HashMap<>();
                                                                        result.put("resend", "yes");
                                                                        completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).updateChildren(result);


                                                                        //Now before or after adding image in database we will not insert a user emailid because there comes the big error of overriding

                                                                        String resend = "no";


                                                                        completedItem.child(currentFirebaseUser.getUid()).child("images").child("resendImages").child(String.valueOf(++imageCounterPotholes))
                                                                                .setValue(new Images(imageUrl,imageDate,imageTime,imageLocationAddress,imageLocationValues,outputResend,completedResend,completedDateResend,completedTimeResend,completedByResend,user_id,feedbackResend,widthAndHeight,verifiedResend,resend));
                                                                        resendButton.setAlpha(0.7f);
                                                                        resendButton.setEnabled(false);
                                                                        verified_text.setText("Pothole has Been RESEND .. !");
                                                                        verified_text.setTextColor(Color.parseColor("#00796B"));
                                                                        okButton.setAlpha(0.7f);
                                                                        okButton.setEnabled(false);

                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {

                                                                    }
                                                                });

                                                            }
                                                        });

                                                    }

                                                    else {


                                   /*                     //Now here completed is yes and verfied is yes
                                                        //so we will print that Your pothole is successfully verified
                                                        verified_text.setText("Pothole has been Verified and Completed succesfully ..!");
                                                        verified_text.setTextColor(Color.GREEN);
                                                        okButton.setEnabled(false);
                                                        resendButton.setEnabled(false);*/


                                                        //Now we will retrieve the values of verfied.completed and resend
                                                        //if completed is yes and resend is yes and verified is no then we will
                                                        //print Pothole resend successfully
                                                        completedItem.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(positon_of_item)).addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {


                                                                Images image = dataSnapshot.getValue(Images.class);

                                                                String retreivedCompleted = image.getCompleted();
                                                                String retreivedResend =  image.getResend();
                                                                String retreivedVerified =  image.getVerified();

                                                                if(retreivedCompleted.equalsIgnoreCase("yes")&&retreivedResend.equalsIgnoreCase("yes")&&retreivedVerified.equalsIgnoreCase("no")){
                                                                    verified_text.setText("Pothole has been Resend  succesfully ..!");
                                                                    verified_text.setTextColor(Color.parseColor("#00796B"));
                                                                    okButton.setAlpha(0.7f);
                                                                    okButton.setEnabled(false);
                                                                    resendButton.setEnabled(false);
                                                                    resendButton.setAlpha(0.7f);
                                                                }
                                                                else if(retreivedCompleted.equalsIgnoreCase("yes") && retreivedVerified.equalsIgnoreCase("yes")&&retreivedResend.equalsIgnoreCase("no")){
                                                                    //Now here completed is yes and verfied is yes
                                                                    //so we will print that Your pothole is successfully verified
                                                                    verified_text.setText("Pothole has been Verified and Completed succesfully ..!");
                                                                    verified_text.setTextColor(Color.parseColor("#00796B"));
                                                                    okButton.setAlpha(0.7f);
                                                                    okButton.setEnabled(false);
                                                                    resendButton.setEnabled(false);
                                                                    resendButton.setAlpha(0.7f);
                                                                }
                                                                else {
                                                                    Toast.makeText(getActivity(), "Error while retreiving image data !", Toast.LENGTH_SHORT).show();
                                                                }

                                                            }

                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {

                                                            }
                                                        });


                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            } else {

                                                Log.e("TAG", " it's null.");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });












                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//
//        //When back button is pressed
//        onBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new PotholeFragmentActivity();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction ft = fragmentManager.beginTransaction();
//
//                //Now in below method 1st argument is container ID
//                //And we know that our container is FrameLayout
//                //2nd Argumnet is Our Fragment object which is to replaced with
//                ft.replace(R.id.screen_area,fragment);
//                ft.commit();
//            }
//        });


        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new PotholeFragmentActivity();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();

                        //Now in below method 1st argument is container ID
                        //And we know that our container is FrameLayout
                        //2nd Argumnet is Our Fragment object which is to replaced with
                        ft.replace(R.id.screen_area,fragment);
                        ft.commit();
                        return true;
                    }
                }
                return false;
            }
        });





    }


}

