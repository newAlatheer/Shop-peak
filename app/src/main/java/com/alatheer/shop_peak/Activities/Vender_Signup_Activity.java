package com.alatheer.shop_peak.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alatheer.shop_peak.Model.Address;
import com.alatheer.shop_peak.R;
import com.alatheer.shop_peak.common.Common;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vender_Signup_Activity extends AppCompatActivity {
    EditText shop_name, shop_email, address, category;
    Spinner governate, city;
    Button add_logo, signup, latlon;
    ImageView seller_image;
    List<Address> addressList;
    List<String> cities;
    private String Name, Email, Governate, City, Address, Category;
    int PICK_IMAGE_REQUEST = 1 ;
    android.support.v7.widget.Toolbar toolbar;
    Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender__signup_);
        initview();
    }

    private void initview() {
        toolbar = findViewById(R.id.toolbar);
        shop_name = findViewById(R.id.shop_name);
        shop_email = findViewById(R.id.shop_email);

        address = findViewById(R.id.address);
        latlon = findViewById(R.id.addlat_lon);
        add_logo = findViewById(R.id.add_logo);
        signup = findViewById(R.id.btn_sign);
        setSupportActionBar(toolbar);
        seller_image = findViewById(R.id.seller_image);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_anim);
        Common.CloseKeyBoard(this, shop_name);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.clearAnimation();
                signup.setAnimation(animation);
                validation();
            }
        });
        add_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        seller_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        latlon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Vender_Signup_Activity.this, MapsActivity.class));
            }
        });
        List<Address> addressList = getaddresslist();
        String governate_name1 = addressList.get(0).getGovernate();
        String governate_name2 = addressList.get(1).getGovernate();
        String governate_name3 = addressList.get(2).getGovernate();
        List<String> governate_names = new ArrayList<>();
        governate_names.add(governate_name1);
        governate_names.add(governate_name2);
        governate_names.add(governate_name3);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Vender_Signup_Activity.this, android.R.layout.simple_spinner_item, governate_names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        governate.setAdapter(adapter);
//        governate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(Vender_Signup_Activity.this, "hello", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }



    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                add_logo.setVisibility(View.GONE);
                seller_image.setImageBitmap(bitmap);
                seller_image.setVisibility(View.VISIBLE);
                Toast.makeText(Vender_Signup_Activity.this, "image added successfully", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void validation() {
        Name = shop_name.getText().toString();
        Email = shop_email.getText().toString();
        Address = address.getText().toString();
        Category = category.getText().toString();
        if (!TextUtils.isEmpty(Name) &&
                !TextUtils.isEmpty(Email) &&
                !TextUtils.isEmpty(Address) &&
                !TextUtils.isEmpty(Category)) {


            Common.CloseKeyBoard(this, shop_email);
            shop_name.setError(null);
            shop_email.setError(null);
            address.setError(null);
            //city.setError(null);
            //governate.setError(null);
            Signup(Name, Email, Address, Category, filePath);

        } else {
            if (TextUtils.isEmpty(Name)) {
                shop_name.setError(getString(R.string.shopname_req));
            } else {
                shop_name.setError(null);
            }

            if (TextUtils.isEmpty(Email)) {
                shop_email.setError(getString(R.string.email_req));
            } else {
                shop_email.setError(null);
            }

            /*if (TextUtils.isEmpty(Governate)) {
                //governate.setError(getString(R.string.governate_req));
            } else {
                //governate.setError(null);
            }
            if (TextUtils.isEmpty(City)) {
                //city.setError(getString(R.string.city_req));
            } else {
                //city.setError(null);
            }*/
            if (TextUtils.isEmpty(Address)) {
                address.setError(getString(R.string.address_req));
            } else {
                address.setError(null);
            }
            if (TextUtils.isEmpty(Category)) {
                category.setError(getString(R.string.category_req));
            } else {
                category.setError(null);
            }

        }

    }

    private void Signup(String name, String email, String address, String category, Uri filePath) {
        Intent intent = new Intent(Vender_Signup_Activity.this, MainActivity.class);
        intent.putExtra("flag", 1);
        startActivity(intent);
        Animatoo.animateInAndOut(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        Animatoo.animateInAndOut(this);
    }

    private List<Address> getaddresslist() {
        addressList = new ArrayList<>();
        addressList.add(new Address("Menofia", new String[]{"menouf", "Shebin"}));
        addressList.add(new Address("Elgharbiah", new String[]{"Tanta"}));
        addressList.add(new Address("Elshaqia", new String[]{"Elzagazig"}));
        return addressList;
    }
}