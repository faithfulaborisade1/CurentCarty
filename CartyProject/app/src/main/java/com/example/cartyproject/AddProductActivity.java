package com.example.cartyproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;

public class AddProductActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 2;

    private Spinner categorySpinner;
    private EditText productNameEditText;
    private EditText productDescriptionEditText;
    private EditText productPriceEditText;
    private ImageView productImageView;
    private byte[] productImageByteArray;
    private ProductDatabaseHelper databaseHelper;
    Button saveButton;
    ImageButton cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        databaseHelper = new ProductDatabaseHelper(this);

        productNameEditText = findViewById(R.id.edit_product_name);
        productDescriptionEditText = findViewById(R.id.edit_product_description);
        productPriceEditText = findViewById(R.id.edit_product_price);
        productImageView = findViewById(R.id.product_image);
        categorySpinner = findViewById(R.id.spinner_product_category);
        cameraButton = findViewById(R.id.camera_button);
        saveButton = findViewById(R.id.button_save_product);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            productImageView.setImageBitmap(imageBitmap);

            // Convert the Bitmap to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            productImageByteArray = stream.toByteArray();
        }
    }

    private void saveProduct() {
        String name = productNameEditText.getText().toString();
        String description = productDescriptionEditText.getText().toString();
        String priceString = productPriceEditText.getText().toString();
        double price = Double.parseDouble(priceString);
        String category = categorySpinner.getSelectedItem().toString();

        if (name.isEmpty() || description.isEmpty() || priceString.isEmpty() || productImageByteArray == null) {
            Toast.makeText(this, "Please fill all fields and take a picture", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add product to the database
        boolean success = databaseHelper.addProduct(name, description, price, category, productImageByteArray);
        if (success) {
            Toast.makeText(this, "Product saved successfully", Toast.LENGTH_SHORT).show();
            // Clear input fields
            productNameEditText.setText("");
            productDescriptionEditText.setText("");
            productPriceEditText.setText("");
            productImageView.setImageResource(R.drawable.product_placeholder);
            productImageByteArray = null; // Reset product image byte array
        } else {
            Toast.makeText(this, "Failed to save product", Toast.LENGTH_SHORT).show();
        }
    }
}
