package com.example.journeyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class JournalEntry extends AppCompatActivity {
    EditText title,description, date;
    Button submit,back, uploadbtn;
    ImageView imageView,purl;
    Uri imagerUri;
    String url=null;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("journals").push();
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);
        title=(EditText)findViewById(R.id.add_title);
        description=(EditText)findViewById(R.id.add_descriptione);
        date=(EditText)findViewById(R.id.add_date);
        purl=(ImageView)findViewById(R.id.add_image);
        uploadbtn = findViewById(R.id.uploadBtn);
       // imageView = findViewById(R.id.add_image);
//on click of image view
        purl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
//uplaodbutton
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagerUri != null )
                    uploadToFirebase(imagerUri);

                else{

                    Toast.makeText(JournalEntry.this, "please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

//back button
        back=(Button)findViewById(R.id.add_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),JournalListFragment.class));
                finish();
            }
        });
//submit
        submit=(Button)findViewById(R.id.add_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertDate();
            }
        });
    }
    //image upload to firebasse

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis()+ "."+ getFileExtention(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                   url = uri.toString();
                   // JournalModel model = new JournalModel(uri.toString());
                  //  String modelid = root.getKey();
                 //   root.child(modelid).setValue(model);
                    Toast.makeText(JournalEntry.this, "Uploaded sucessful", Toast.LENGTH_SHORT).show();
                }
            });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(JournalEntry.this, "upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtention(Uri mUri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
    //onactivityresult`
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null)
        {
            imagerUri = data.getData();
            purl.setImageURI(imagerUri);
        }

    }

    //converting entries that comes in edittext and mapping them to add on firebase realtime db
    private void InsertDate()
    {
        Map<String,Object> map=new HashMap<>();
        map.put("title",title.getText().toString());
        map.put("description",description.getText().toString());
        map.put("date",date.getText().toString());
        if (url!= null)
        map.put("purl",url );

        FirebaseDatabase.getInstance().getReference().child("journals").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        title.setText("");
                        description.setText("");
                        date.setText("");
                        //purl.set;
                        Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show();
                    }
                });
    }
}