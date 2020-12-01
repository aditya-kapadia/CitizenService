package com.example.lucky_rathod.csp3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucky_Rathod on 09-02-2018.
 */
public class ResendFragmentActivity extends Fragment {

    ListView listView;

    List<Images> list;

    ProgressDialog progressDialog;

    PotholeAdapter potholeAdapter;

    View v;

    TextView completedGreenIfYes;


    private DatabaseReference potholeParentImageReference;
    DatabaseReference completedItem;
    //Now we store userid when continue button is clicked


    //Now we store userid when continue button is clicked
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//
//    TextView completedGreen = (listView).findViewById(R.id.completedGreenIfYes);

    String item;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ((MainActivity) getActivity())
                .setActionBarTitle("Resend Issues..!");
        return  inflater.inflate(R.layout.resend_fragment,null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Now here we will show our Pothole Data

        listView = view.findViewById(R.id.resendList);

        list = new ArrayList<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Fetching data...");
        progressDialog.show();

        completedItem = FirebaseDatabase.getInstance().getReference().child("users");

        potholeParentImageReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(currentFirebaseUser.getUid()).child("images").child("resendImages");


        potholeParentImageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                //Now we will clear the list which was previuosly retreived so it doesnt get override
                list.clear();

                for (DataSnapshot snapPotholes : dataSnapshot.getChildren()){

                    //Pojo class of images

                    Images images = snapPotholes.getValue(Images.class);
                    list.add(images);

                }

                //Now we will set our list in adapter
                if(getActivity() != null) {
                    //Now if u dont write in if bloack then ur app will get crashed when there will be a change in database

                    potholeAdapter = new PotholeAdapter(getActivity(), R.layout.potholes_data_items, list);

                    listView.setAdapter(potholeAdapter);
                    listView.setEmptyView(view.findViewById(R.id.emptyElement));

                    int count = listView.getCount();
                    Log.d("Count in ListItems", String.valueOf(count));

                    for (int i = 1; i <= count; i++) {


                        final int finalI = --i;
                        completedItem.child(currentFirebaseUser.getUid()).child("images").child("resendImages").child(String.valueOf(++i)).child("completed").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    if (dataSnapshot.getValue() != null) {
                                        try {

                                            String completed = (String) dataSnapshot.getValue();
                                            if (completed.equalsIgnoreCase("no")) {

                                                completedGreenIfYes = (TextView) listView.getChildAt(finalI).findViewById(R.id.completedGreenIfYes);

                                                if(completedGreenIfYes != null) {
                                                    completedGreenIfYes.setTextColor(Color.RED);
                                                }

                                            } else {
                                                completedGreenIfYes = (TextView) listView.getChildAt(finalI).findViewById(R.id.completedGreenIfYes);
                                                if(completedGreenIfYes != null) {
                                                    completedGreenIfYes.setTextColor(Color.parseColor("#00796B"));
                                                }

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

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long offset) {

                Images imageItem = (Images)potholeAdapter.getItem(position);

/*                Intent intent = new Intent(getActivity(),ImageInfoPerItemFragment.class);
                intent.putExtra("imageUrl",imageItem.getImageUrl());
                intent.putExtra("imageDate",imageItem.getImageDate());
                intent.putExtra("imageTime",imageItem.getImageTime());
                intent.putExtra("imageLocationAddress",imageItem.getImageLocationAddress());
                intent.putExtra("imageLocationValues",imageItem.getImageLocationValues());
                intent.putExtra("width",imageItem.getWidth());
                intent.putExtra("depth",imageItem.getHeight());
                startActivity(intent);*/


//Now we will not choose an intent to send data we will be using Fragment
                Fragment fragment = new ImageInfoPerItemResend();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                //using Bundle to send data
                Bundle bundle=new Bundle();
                bundle.putString("imageUrl",imageItem.getImageUrl());
                bundle.putString("imageDate",imageItem.getImageDate());
                bundle.putString("imageTime",imageItem.getImageTime());
                bundle.putString("imageLocationAddress",imageItem.getImageLocationAddress());
                bundle.putString("imageLocationValues",imageItem.getImageLocationValues());
                bundle.putString("widthAndHeight",imageItem.getWidthAndHeight());
                bundle.putString("output",imageItem.getOutput());
                bundle.putString("completed",imageItem.getCompleted());
                bundle.putInt("position", position);
                fragment.setArguments(bundle); //data being send to SecondFragment
                ft.replace(R.id.screen_area, fragment);
                ft.commit();

            }
        });

        view.findViewById(R.id.got_a_new_issue_resend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "CLICK A PHOTO FROM CAMERA", Toast.LENGTH_SHORT).show();
                Fragment fragment = new CaptureImageFragmentActivity();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                //Now in below method 1st argument is container ID
                //And we know that our container is FrameLayout
                //2nd Argumnet is Our Fragment object which is to replaced with
                ft.replace(R.id.screen_area,fragment);
                ft.commit();
            }
        });

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();

        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new HomeFragmentActivity();
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