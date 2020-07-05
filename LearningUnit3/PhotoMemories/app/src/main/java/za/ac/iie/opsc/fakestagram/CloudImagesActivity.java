package za.ac.iie.opsc.fakestagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CloudImagesActivity extends AppCompatActivity {

    private CloudImagesStoreFragment storeFragment = new CloudImagesStoreFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_images);

        Button addButton = findViewById(R.id.btn_add_cloud);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.cloud_image_place_holder, storeFragment);
                transaction.commitAllowingStateLoss();
            }
        });
    }
}