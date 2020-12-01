package com.example.lucky_rathod.csp3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.lucky_rathod.csp3.MainActivity.email_id;
import static com.example.lucky_rathod.csp3.MainActivity.name;

/**
 * Created by Lucky_Rathod on 02-01-2018.
 */

public class HomeFragmentActivity extends Fragment {


Button viewReportedIssues,gotAnewIssue;
TextView reported_issues,pothole_issues,sanitation_issues;

DatabaseReference reportedIssues,sanitationIssues,potholeIssues;
    private ProgressBar mProgress;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        ((MainActivity) getActivity())
                .setActionBarTitle("CSP3");
        return  inflater.inflate(R.layout.home_fragment,null);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.capture_pothole).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "CLICK A PHOTO FROM CAMERA", Toast.LENGTH_SHORT).show();
//            }
//        })
//
//name = view.findViewById(R.id.name);
//emailid = view.findViewById(R.id.email_id);

//        Log.d("Username and EmailId",name+""+email_id);

        final ProgressBar progressBar = view.findViewById(R.id.circle_progress_bar);
        /*progressBar.setProgress(65);*/

        final  ProgressBar progressBar1 = view.findViewById(R.id.circle_progress_bar1);




        viewReportedIssues = view.findViewById(R.id.view_reported_issues);
        gotAnewIssue = view.findViewById(R.id.got_a_new_issue_home);
//        reported_issues = view.findViewById(R.id.reported_issues);
        pothole_issues = view.findViewById(R.id.pothole_issues);
        sanitation_issues = view.findViewById(R.id.sanitation_issues);

        reportedIssues = FirebaseDatabase.getInstance().getReference().child("ReportedIssues");
        potholeIssues = FirebaseDatabase.getInstance().getReference().child("ReportedPotholeIssues");
        sanitationIssues = FirebaseDatabase.getInstance().getReference().child("ReportedSanitationIssues");

        reportedIssues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long reportedIssuesNo = dataSnapshot.getChildrenCount();
//                reported_issues.setText(String.valueOf(reportedIssuesNo));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        potholeIssues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long reportedPotholeIssuesNo = dataSnapshot.getChildrenCount();
                pothole_issues.setText(String.valueOf(reportedPotholeIssuesNo));
                progressBar.setProgress((int)reportedPotholeIssuesNo);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        sanitationIssues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long reportedSanitationIssuesNo = dataSnapshot.getChildrenCount();
            sanitation_issues.setText(String.valueOf(reportedSanitationIssuesNo));
            progressBar1.setProgress((int)reportedSanitationIssuesNo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        gotAnewIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        viewReportedIssues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new ViewAllReportedIssues();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();

                //Now in below method 1st argument is container ID
                //And we know that our container is FrameLayout
                //2nd Argumnet is Our Fragment object which is to replaced with
                ft.replace(R.id.screen_area,fragment);
                ft.commit();

            }
        });


    }
}
