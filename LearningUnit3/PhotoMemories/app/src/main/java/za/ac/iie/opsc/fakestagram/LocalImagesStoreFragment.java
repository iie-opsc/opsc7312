package za.ac.iie.opsc.fakestagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import za.ac.iie.opsc.fakestagram.database.DatabaseHandler;
import za.ac.iie.opsc.fakestagram.model.ImageModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalImagesStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalImagesStoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int PICK_CODE = 101;
    private Bitmap imageToStore;
    private ImageView imageView;
    private EditText etImageName;
    private DatabaseHandler imagedb;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LocalImagesStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocalImagesStoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalImagesStoreFragment newInstance(String param1, String param2) {
        LocalImagesStoreFragment fragment = new LocalImagesStoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_local_images_store,
                container, false);
        imageView = view.findViewById(R.id.img_imagepane);
        etImageName = view.findViewById(R.id.txt_image_description);
        imagedb = new DatabaseHandler(getActivity());

        Button chooseButton = view.findViewById(R.id.btn_choose_image);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        Button saveButton = view.findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
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
            Uri imageData = data.getData();
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

    private void saveImage() {
        if (etImageName.getText().toString() != null &&
                imageView != null && imageToStore != null) {
            imagedb.storeImageLocal(new ImageModel(
                    etImageName.getText().toString(), imageToStore));

            // reset the image view and name edit text
            imageView.setImageResource(R.drawable.photo);
            etImageName.setText("");
        }
        else {
            Toast.makeText(getContext(),
                    "Please select and Image and Enter an Image name",
                    Toast.LENGTH_SHORT).show();
        }
    }
}