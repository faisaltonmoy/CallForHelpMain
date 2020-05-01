package com.example.callforhelpdemu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.callforhelpdemu.ui.gallery.GalleryFragment;
import com.example.callforhelpdemu.ui.home.HomeFragment;
import com.example.callforhelpdemu.ui.share.ShareFragment;
import com.example.callforhelpdemu.ui.slideshow.SlideshowFragment;
import com.example.callforhelpdemu.ui.slideshow.SlideshowViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
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

public class Home2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    //CREATE THE OBJECT//
    private AppBarConfiguration mAppBarConfiguration;
    private CardView HospitalCardView,PoliceStationCardView,FireServiceCardView,AmbulanceCardView;
    private TextView menu_user_id;
    private View hview;

    private AlertDialog.Builder alertdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        menu_user_id=findViewById(R.id.menu_user_id);



    //THIS PART IS GET THE STRING FROM MAIN ACTIVITY JAVA//
        Intent intent = getIntent();
        String text1 = intent.getStringExtra(MainActivity.Extra);
        String text2 = intent.getStringExtra(start.Extra);





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


        //FIND THE TEXT FROM MAIN ACTIVITY//
        hview=navigationView.getHeaderView(0);
        menu_user_id=hview.findViewById(R.id.menu_user_id);


        navigationView.setNavigationItemSelectedListener(this);


        //DATA RETRIVE FROM THE FILE AND SET THE MENU NAVIGATION BER//
        menu_user_id.setText(check("user.txt"));


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
        getMenuInflater().inflate(R.menu.home2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //THIS PART IS CHECK THE CONDITION FOR SHOW THE ALERT NOTIFICATION WHEN WE CLICK MENU OPTION//

        if(item.getItemId()==R.id.action_logout)
        {
            alertdialog=new AlertDialog.Builder(Home2.this);
            alertdialog.setTitle("Alert");
            alertdialog.setMessage("Do you want to exit?");
            alertdialog.setIcon(R.drawable.warn_icon);



            alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Home2.this, "You have clicked no button", Toast.LENGTH_SHORT).show();
                }
            });

            alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Home2.this,MainActivity.class));
                    Toast.makeText(Home2.this, "Signed Out", Toast.LENGTH_SHORT).show();

                    //THIS PART WRITE THE Sta FILE FOR SIGN OUT//

                    FileOutputStream fos0 = null;
                    try {
                        // edits Stat File and sets the text to logged_out
                        fos0 = openFileOutput("sta.txt", MODE_PRIVATE);
                        fos0.write("Signed Out".getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new GalleryFragment()).commit();
                break;

            /*case R.id.nav_tools:

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ToolsFragment()).commit();
                break;*/

            case R.id.nav_slideshow:    //THIS IS ABOUT US PART IN NAVIGATION MENU//

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new SlideshowFragment() ).commit();


                break;


            case R.id.nav_share:        //THIS IS SHARE IN NAVIGATION MENU//

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ShareFragment()).commit();
                break;







        }

        return true;
    }

    //THIS FUNCTION READ THE FILE//

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
