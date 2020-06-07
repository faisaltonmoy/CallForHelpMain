package com.example.callforhelpdemu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.callforhelpdemu.Nav_UI.MyProfile.MyProfileFragment;
import com.example.callforhelpdemu.Nav_UI.Home.HomeFragment;
import com.example.callforhelpdemu.Nav_UI.Instruction.InstructionFragment;
import com.example.callforhelpdemu.Nav_UI.Feedback.FeedbackFragment;
import com.example.callforhelpdemu.Nav_UI.AboutUs.AboutUsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    //********************* CREATE THE OBJECT ***************************//
    private AppBarConfiguration mAppBarConfiguration;
    private TextView menu_user,menu_email;
    private View hview;

    private AlertDialog.Builder alertdialog;

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder exit = new AlertDialog.Builder(Home.this);
        exit.setTitle("Alert");
        exit.setMessage("Are you sure to exit this app?");
        exit.setIcon(R.drawable.warn_icon);
        exit.setCancelable(true);
        exit.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alert = exit.create();
        alert.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menu_user=findViewById(R.id.menu_user_id);
        menu_email=findViewById(R.id.menu_email_id);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        //********************** FIND THE TEXT FROM MAIN ACTIVITY ******************//
        hview=navigationView.getHeaderView(0);
        menu_user=hview.findViewById(R.id.menu_user_id);
        menu_email=hview.findViewById(R.id.menu_email_id);



        navigationView.setNavigationItemSelectedListener(this);


        //************************** DATA RETRIVE FROM THE FILE AND SET THE MENU NAVIGATION BER ***************************//
        menu_user.setText(check("user.txt"));
        menu_email.setText(check("email.txt"));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                 R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //****************** THIS PART IS CHECK THE CONDITION FOR SHOW THE ALERT NOTIFICATION WHEN WE CLICK MENU OPTION *************************//

        if(item.getItemId()==R.id.action_logout)
        {
            alertdialog=new AlertDialog.Builder(Home.this);
            alertdialog.setTitle("Alert");
            alertdialog.setMessage("Do you want to Sign Out?");
            alertdialog.setIcon(R.drawable.warn_icon);



            alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Home.this, "You have clicked 'NO' button", Toast.LENGTH_SHORT).show();
                }
            });

            alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Home.this,MainActivity.class));
                    Toast.makeText(Home.this, "Signed Out", Toast.LENGTH_SHORT).show();
                    finish();

                    //*************** THIS PART WRITE THE Sta FILE FOR SIGN OUT **************************//

                    FileOutputStream fos0 = null;
                    try {
                        // edits Stat File and sets the text to logged_out
                        fos0 = openFileOutput("sta.txt", MODE_PRIVATE);
                        fos0.write("Signed Out".getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });


            AlertDialog alertDialog=alertdialog.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {




        switch (menuItem.getItemId())
        {
            case R.id.nav_home:         //THIS IS ONLY HOME PART IN NAVIGATION MENU//

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
                break;

            case R.id.nav_gallery:      //THIS IS MY PROFILE PART IN NAVIGATION MENU//

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new MyProfileFragment()).commit();
                break;

            /*case R.id.nav_tools:

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ToolsFragment()).commit();
                break;*/

            case R.id.nav_slideshow:    //THIS IS ABOUT US PART IN NAVIGATION MENU//

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new AboutUsFragment() ).commit();

                break;

            case R.id.nav_send:        //THIS IS INSTRUCTIONS IN NAVIGATION MENU//

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new InstructionFragment()).commit();
                break;


            case R.id.nav_share:        //THIS IS FEEDBACK IN NAVIGATION MENU//

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new FeedbackFragment()).commit();
                break;


        }

        return true;
    }

    //*********************** THIS FUNCTION READ THE FILE AND RETURN STRING ********************//

    public String check(String File_Name){
        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 =openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while( (text = br.readLine()) != null ){
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis0 != null) {
                try {
                    fis0.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return st;
    }

}
