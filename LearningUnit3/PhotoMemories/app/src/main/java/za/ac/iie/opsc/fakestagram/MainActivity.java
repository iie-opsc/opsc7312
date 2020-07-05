package za.ac.iie.opsc.fakestagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class MainActivity extends AppCompatActivity
        implements OnCompleteListener<AuthResult> {

    private MainChoiceFragment mainChoiceFragment = new MainChoiceFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Set this activity as the login listener for the LoginFragment
     * @param fragment The fragment that is getting attached
     */
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        // check if we are looking at the right fragment
        if (fragment instanceof LoginFragment) {
            // set the listener on the LoginFragment
            LoginFragment loginFragment = (LoginFragment)fragment;
            loginFragment.setOnCompleteListener(this);
        }
    }

    /**
     * Handle the login event.
     * @param task The task that returns the login information.
     */
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Toast.makeText(this, "You have signed in " +
                            task.getResult().getUser().getEmail(),
                    Toast.LENGTH_SHORT).show();
            loadFragment();
        } else {
            Toast.makeText(this,
                    "Boo Boo Happened when logging in",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Switch the fragment on successful login.
     */
    public void loadFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.layout_fragment, mainChoiceFragment);
        transaction.commit();
    }
}