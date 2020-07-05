package za.ac.iie.opsc.fakestagram;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import za.ac.iie.opsc.fakestagram.model.ImageModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CloudImagesStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CloudImagesStoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PICK_CODE = 102;
    private Bitmap imageToStore;
    private Uri imageData;
    private ImageView imageView;
    private EditText etImageName;
    private StorageReference storageRef;
    private FirebaseUser currentUser;
    private FirebaseAuth authentication;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("PhotoMemories");

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CloudImagesStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CloudImagesStoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CloudImagesStoreFragment newInstance(String param1, String param2) {
        CloudImagesStoreFragment fragment = new CloudImagesStoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cloud_images_store,
                container, false);
        imageView = view.findViewById(R.id.img_imagepane_cloud);
        etImageName = view.findViewById(R.id.txt_image_description_cloud);

        Button chooseButton = view.findViewById(R.id.btn_choose_image_cloud);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        storageRef = FirebaseStorage.getInstance().getReference("PhotoMemories");
        authentication = FirebaseAuth.getInstance();

        Button saveButton = view.findViewById(R.id.btn_save_cloud);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeImage();
            }
        });

        return view;
    }

    public void chooseImage() {
        Intent chooseImage = new Intent(Intent.ACTION_GET_CONTENT);
        chooseImage.setType("image/*");
        startActivityForResult(Intent.createChooser(chooseImage,
                "Pick and Image "), PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_CODE && data != null) {
            imageData = data.getData();
            try {
                imageToStore = MediaStore.Images.Media.getBitmap(
                        getContext().getContentResolver(), imageData);
            }
            catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            imageView.setImageBitmap(imageToStore);
        }
    }

    private String getFileExtensions(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void storeImage() {
        try {
            if (imageData != null) {
                // upload the image to storage
                final StorageReference fileRef = storageRef.child(
                        System.currentTimeMillis() + "." +
                        getFileExtensions(imageData));

                UploadTask uploadTask = fileRef.putFile(imageData);

                Task<Uri> urlTask = uploadTask.continueWithTask(
                        new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task)
                            throws Exception {
                        return fileRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()) {
                            // write an entry to realtime database
                            currentUser = authentication.getCurrentUser();
                            final String storedImageUri = task.getResult().toString();

                            ImageModel imageModel = new ImageModel(
                                    etImageName.getText().toString().trim(),
                                    storedImageUri);
                            String uploadID = myRef.push().getKey();
                            myRef.child(currentUser.getUid()).
                                    child(uploadID).setValue(imageModel);

                            Toast.makeText(getContext(),
                                    "Photo loaded to the cloud :-)",
                                    Toast.LENGTH_SHORT).show();

                            // reset the user interface
                            imageView.setImageResource(R.drawable.photo);
                            etImageName.setText("");
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please select an Image ",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}