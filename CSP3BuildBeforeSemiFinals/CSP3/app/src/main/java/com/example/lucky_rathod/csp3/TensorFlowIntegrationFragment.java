package com.example.lucky_rathod.csp3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lucky_Rathod on 21-01-2018.
 */

public class TensorFlowIntegrationFragment extends Fragment{

    ImageView final_captured_image;
    TextView output,output_widthAndHeight,final_imageDate,final_imageTime,final_location_address,final_location_value,uriTextView;
    Button send_button;
//    EditText output;
    Uri uri;
    String imageUri;

    //Now we will start storing our images as per the output of neural network code
    //Now we will add our storage and database reference
    StorageReference storageReference;
    DatabaseReference imageAndEmailReference,rootReference,latlongreference,potholeIssues,sanitationIssues;

    //Constants for Storage path and database path which will be used in getting reference till there
    public static final String STORAGE_PATH = "images/";

    //Now we store userid when continue button is clicked
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    //Now we will get the download url of the image which is uploaded
    String imageUrl;

    // variables for IMAGES POJO class
    String imageDate,imageTime,address,addressValues;

    String widthAnddepth="Width:-0 Height:-0";

    String completed="no";

    String feedback = "No feedback";

    String verified = "no";

    String resend = "no";

    String user_id;

    String completedBy="Mastek NGO",completedDate ="Feb 17, 2018",completedTime="10 AM";

    //Now we will capture lat long values from bundle
    String latitude,longitude;

    Double lati,loni;

    String imageOutputFromModelTensor;

    String realPathTensor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ((MainActivity) getActivity())
                .setActionBarTitle("Captured Issue Details");
        return  inflater.inflate(R.layout.tensorflow_integration_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final_captured_image = view.findViewById(R.id.final_captured_image);
        output = view.findViewById(R.id.output);
        output_widthAndHeight = view.findViewById(R.id.output_widthAndHeight);
//        final_imageDate = view.findViewById(R.id.final_imageDate);
//        final_imageTime = view.findViewById(R.id.final_imageTime);
//        final_location_address = view.findViewById(R.id.final_location_address);
//        final_location_value = view.findViewById(R.id.final_location_value);
        send_button = view.findViewById(R.id.send_button);
        send_button.setEnabled(false);
        //uriTextView = view.findViewById(R.id.tensorflowUri);
        //retrieving data using bundle
        Bundle bundle=getArguments();
//        final_imageDate.setText(String.valueOf(bundle.getString("imageDate")));
//        final_imageTime.setText(String.valueOf(bundle.getString("imageTime")));
//        final_location_address.setText(String.valueOf(bundle.getString("address")));
//        final_location_value.setText(String.valueOf(bundle.getString("addressValues")));

        //Now we will store the values from bundle to string to put them inPOJO class
        imageDate = bundle.getString("imageDate");
        imageTime = bundle.getString("imageTime");
        address = bundle.getString("address");
        addressValues = bundle.getString("addressValues");
        lati = bundle.getDouble("latitude");
        loni = bundle.getDouble("longitude");
        imageOutputFromModelTensor = bundle.getString("imageOutputFromModel");




        //Now we will get our reference of FireBase Storage
        storageReference = FirebaseStorage.getInstance().getReference(STORAGE_PATH);
        imageAndEmailReference = FirebaseDatabase.getInstance().getReference().child("users");
        rootReference = FirebaseDatabase.getInstance().getReference();
        latlongreference =FirebaseDatabase.getInstance().getReference().child("ReportedIssues");

        potholeIssues = FirebaseDatabase.getInstance().getReference().child("ReportedPotholeIssues");
        sanitationIssues = FirebaseDatabase.getInstance().getReference().child("ReportedSanitationIssues");







        //Now we will retreive the user uid and store it in data base
        user_id = currentFirebaseUser.getEmail();



        Bitmap bitmapImage = bundle.getParcelable("bitmap");
        final_captured_image.setImageBitmap(bitmapImage);

        uri = getImageUri(getActivity(),bitmapImage);
//        Toast.makeText(getActivity(), "Done bro"+uri, Toast.LENGTH_SHORT).show();

        //Converting string to Uri
        String stringUri = bundle.getString("stringUri");

        if(stringUri == null)
            realPathTensor = bundle.getString("realPath");
        else
            realPathTensor = Uri.parse(stringUri).getPath();


        final String imageFirebaseurl= "https://firebasestorage.googleapis.com/v0/b/deepbluecsp.appspot.com/o/images%2Fimages%2F1518763005130.%20jpg?alt=media&token=7644dc8b-95c0-43e5-9f1b-519bec39392d";
       final String cacheDir = getActivity().getCacheDir().getAbsolutePath();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    ImageClassifyApiCall apiCall = new ImageClassifyApiCall(realPathTensor, cacheDir);
                    List<Pair<String, Float>> result = apiCall.uploadAndClassify();
                    return result.get(0).first;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result){
                String outputFromModelFinal,widthHeightVolume;

                if(result.equals("Others")){
                    output.setText(result);
                    output_widthAndHeight.setText("Width and Depth cannot be fined");
                }
                else {
                    String inp[] = result.split(":");
                    outputFromModelFinal = inp[0];
                    widthHeightVolume = inp[1];
                    output.setText(outputFromModelFinal);
                    output_widthAndHeight.setText(widthHeightVolume);

//                    Toast.makeText(getActivity(), "Output" + outputFromModelFinal + "2nd part" + widthHeightVolume, Toast.LENGTH_SHORT).show();
                }
                send_button.setVisibility(View.VISIBLE);




                //Right now we are storing output value static untill model is trained
//     output.setText("sanitation");


//        output.setText(imageOutputFromModelTensor);


                if (output.getText().toString().contains("potholes")  || output.getText().toString().contains("sanitation")) {

//                    Toast.makeText(getActivity(), "Now in if", Toast.LENGTH_SHORT).show();
                    send_button.setAlpha(1.0f);
                    send_button.setEnabled(true);
                    send_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            HashMap<String, Object> result = new HashMap<>();
                            result.put("latitude", lati);
                            result.put("longitude",loni);
                            result.put("userName",user_id);

                            latlongreference.push().setValue(result);




                            if (uri != null) {


                                //Now we will also show the progress of our uploading task
                                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                progressDialog.setTitle("Uploading Your Image");
                                progressDialog.show();
                                //insert data


//
                                StorageReference reference = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + ". " + getActualImage(uri));
                                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        //Now we will create POJO class for Images to get uploaded in Database
                                        imageUrl = taskSnapshot.getDownloadUrl().toString();
                                        //Now we will retreive our value
                                        //  imageCounterPreference = sharedPreferences.getLong("ImageCounter",0);

                                        //Now here we will check that whether our database is empty or not

//                            rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    if(dataSnapshot.getValue()==null){
//                                        imageCounter = 0;
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//
//                                }
//                            });

                                        if (output.getText().toString().contains("potholes")) {
//                                            Toast.makeText(getActivity(), "Pothole", Toast.LENGTH_SHORT).show();

                                            //Now we will show no of pothole issues which are reported for home fragment Activity

                                            HashMap<String, Object> result = new HashMap<>();
                                            result.put("latitude", lati);
                                            result.put("longitude",loni);
                                            result.put("userName",user_id);

                                            potholeIssues.push().setValue(result);

                                            //Then we will store images as child of pothole ( parent ) in images parent in firebase
                                            imageAndEmailReference.child(currentFirebaseUser.getUid()).child("images").child("potholes").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    long imageCounterPotholes = dataSnapshot.getChildrenCount();

                                                    //Now we will store these counter in sharedPreferences beacause if user close and opens the app then from these value
                                                    //image should be uploaded
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.putLong("ImageCounter",imageCounter);
//                                    editor.commit();
                                                    //Now before or after adding image in database we will not insert a user emailid because there comes the big error of overriding
                                                    imageAndEmailReference.child(currentFirebaseUser.getUid()).child("images").child("potholes").child(String.valueOf(++imageCounterPotholes))
                                                            .setValue(new Images(imageUrl, imageDate, imageTime, address, addressValues, output.getText().toString(), completed, completedDate, completedTime, completedBy, user_id, feedback, output_widthAndHeight.getText().toString(), verified, resend));

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


//                           //Now before or after adding image in database we will not insert a user emailid because there comes the big error of overriding
//                           imageAndEmailReference.child(currentFirebaseUser.getUid()).child("images").child(String.valueOf(++imageCounter))
//                                   .setValue(new Images(imageUrl,formattedDate,formattedTime,imageAddress,longitudeAndLatitude,width,height));


                                            uri = null;
                                            final_captured_image.setImageResource(R.drawable.noimage);


                                            //Now when data gets stored on database
                                            //We will dismiss our progress dialog

                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                                            Fragment fragment = new HomeFragmentActivity();
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction ft = fragmentManager.beginTransaction();
                                            ft.replace(R.id.screen_area, fragment);
                                            ft.commit();


                                        } else if (output.getText().toString().contains("sanitation")) {

//                                            Toast.makeText(getActivity(), "Sanitation h", Toast.LENGTH_SHORT).show();


                                            //Now we will show no of pothole issues which are reported for home fragment Activity

                                            HashMap<String, Object> result = new HashMap<>();
                                            result.put("latitude", lati);
                                            result.put("longitude",loni);
                                            result.put("userName",user_id);

                                            sanitationIssues.push().setValue(result);


                                            //Then we will store images as child of sanitation ( parent ) in images parent in firebase

                                            //Then we will store images as child of pothole ( parent ) in images parent in firebase
                                            imageAndEmailReference.child(currentFirebaseUser.getUid()).child("images").child("sanitation").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    long imageCounterSanitation = dataSnapshot.getChildrenCount();

                                                    //Now we will store these counter in sharedPreferences beacause if user close and opens the app then from these value
                                                    //image should be uploaded
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.putLong("ImageCounter",imageCounter);
//                                    editor.commit();
                                                    //Now before or after adding image in database we will not insert a user emailid because there comes the big error of overriding
                                                    imageAndEmailReference.child(currentFirebaseUser.getUid()).child("images").child("sanitation").child(String.valueOf(++imageCounterSanitation))
                                                            .setValue(new Images(imageUrl, imageDate, imageTime, address, addressValues, output.getText().toString(), completed, completedDate, completedTime, completedBy, user_id, feedback, output_widthAndHeight.getText().toString(), verified, resend));

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


//                           //Now before or after adding image in database we will not insert a user emailid because there comes the big error of overriding
//                           imageAndEmailReference.child(currentFirebaseUser.getUid()).child("images").child(String.valueOf(++imageCounter))
//                                   .setValue(new Images(imageUrl,formattedDate,formattedTime,imageAddress,longitudeAndLatitude,width,height));

                                            uri = null;
                                            final_captured_image.setImageResource(R.drawable.noimage);


                                            //Now when data gets stored on database
                                            //We will dismiss our progress dialog

                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                                            Fragment fragment = new HomeFragmentActivity();
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction ft = fragmentManager.beginTransaction();
                                            ft.replace(R.id.screen_area, fragment);
                                            ft.commit();

                                        } else {

                                            send_button.setEnabled(false);
                                            send_button.setAlpha(0.7f);
                                            Toast.makeText(getActivity(), "Captured Image is Neither Pothole nor Sanitation", Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                //Now when it is interrupted we will show the message in Toast
                                                progressDialog.dismiss();
                                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                                double totalProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                progressDialog.setMessage("Uploaded % " + (int) totalProgress);
                                            }
                                        });

                            } else {
                                //show message
                                Toast.makeText(getActivity(), "Choose the image first", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
                else {
                    send_button.setEnabled(true);
                    send_button.setAlpha(0.7f);
//                    Toast.makeText(getActivity(), "Output"+output.getText(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Captured Image is Neither Pothole nor Sanitation", Toast.LENGTH_SHORT).show();
                }

                getView().setFocusableInTouchMode(true);
                getView().requestFocus();

                getView().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                                Fragment fragment = new CaptureImageFragmentActivity();
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

        }.execute();



//        new AsyncTask<String, Void, String>() {
//            @Override
//            protected String doInBackground(String... voids) {
//                try {
//                    ImageClassifyApiCall apiCall = new ImageClassifyApiCall(imageUri, cacheDir);
//                    List<Pair<String, Float>> result = apiCall.uploadAndClassify();
//                    return result.get(0).first;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return "potholes";
//            }
//            @Override
//            protected void onPostExecute(String result) {
//                output.setText(result);
//            }
//        }.execute();




    }
    public String getActualImage(Uri uri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
