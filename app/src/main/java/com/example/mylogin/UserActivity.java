package com.example.mylogin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    DBHelper myDb;
    EditText editId;
    Spinner spinner;
    Button btnAdd;
    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    ArrayList arrayList;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    RadioButton radioButton1, radioButton2;
    RadioGroup radioGroup;
    String type;
    TextView content;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String spinnerValue;
    ImageView imageView;


    Uri imageUri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_view);
        myDb = new DBHelper(this);
        ProfileImage image =new ProfileImage();

        actionButton = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recycler_view);
        content = findViewById(R.id.contents);

        sharedPreferences=this.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = hView.findViewById(R.id.name);
        imageView=hView.findViewById(R.id.imageView2);
        nav_user.setText(sharedPreferences.getString("name",null));
        imageView.setImageURI(image.imageUri);



        Log.i("Log", " imageuri " + imageUri2);




        arrayList = new ArrayList<>(myDb.getAllData());
        Log.i("Number of Records", " :: " + arrayList.size());
        if (arrayList.size() == 0) {
            content.setVisibility(View.VISIBLE);
        } else {
            displayNotes();
            content.setVisibility(View.GONE);
        }
    }


    public void displayNotes() {
        arrayList = new ArrayList<>(myDb.getAllData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        NotesAdapter adapter = new NotesAdapter(getApplicationContext(), this, arrayList);
        recyclerView.setAdapter(adapter);
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.activity_user);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btnAdd = dialog.findViewById(R.id.btn_add);
        editId = dialog.findViewById(R.id.product);
        spinner = dialog.findViewById(R.id.spinner);
        radioGroup = dialog.findViewById(R.id.radioGroup);
        radioButton1 = dialog.findViewById(R.id.radioButton);
        radioButton2 = dialog.findViewById(R.id.radioButton2);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Bucket");
        categories.add("vessel");
        categories.add("spoon");
        categories.add("Mug");
        categories.add("plate");
        categories.add("glass");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editId.getText().toString(), spinnerValue, type);
                Log.i("Log", "insert" + isInserted);

                if (isInserted == true) {
                    dialog.cancel();
                   displayNotes();
                    content.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_settings:
                Log.d("Log", "opened");
                Intent profile = new Intent(this, ProfileImage.class);
                startActivity(profile);

                break;
            case R.id.nav_logout:
                Intent logoutintent = new Intent(this, MainActivity.class);
                startActivity(logoutintent);
                Log.i("Log", "logout:" + logoutintent);
                SharedPreferences loginSharedPreferences;
                loginSharedPreferences = getSharedPreferences(
                        "login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginSharedPreferences.edit();
                editor.putBoolean("userlogin", false);
                editor.clear();
                editor.apply();
                finish();
                break;
            case R.id.nav_movielist:
                Intent movie = new Intent(this, SearchActivity.class);
                startActivity(movie);
                drawer.closeDrawer(GravityCompat.START);
                break;


        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerValue = parent.getItemAtPosition(position).toString();
        Log.d("Log", "spinner"+spinnerValue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void RadioButtonClicked(View view) {
        type = "";
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    type = "Plastic";
                break;
            case R.id.radioButton2:
                if (checked)
                    type = "Glass";
                break;
        }
    }


}
