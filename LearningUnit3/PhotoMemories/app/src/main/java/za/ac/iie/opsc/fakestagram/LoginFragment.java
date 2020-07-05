package za.ac.iie.opsc.fakestagram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnCompleteListener<AuthResult> onCompleteListener;

    private FirebaseAuth authentication;
    private EditText etEmailAddress;
    private EditText etPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        authentication = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etEmailAddress = view.findViewById(R.id.txt_uName);
        etPassword = view.findViewById(R.id.txt_pword);

        Button registerButton = view.findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = etEmailAddress.getText().toString().trim();
                String passWord = etPassword.getText().toString().trim();

                authentication.createUserWithEmailAndPassword(userName, passWord)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(),
                                            userName, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(),
                                            "Boo Boo Happened when registering ",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        Button loginButton = view.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = etEmailAddress.getText().toString().trim();
                String passWord = etPassword.getText().toString().trim();
                authentication.signInWithEmailAndPassword(userName,passWord)
                        .addOnCompleteListener(onCompleteListener);
            }
        });

        return view;
    }

    /**
     * Sets the listener to call when login completes.
     * @param onCompleteListener The listener to call.
     */
    public void setOnCompleteListener(
            OnCompleteListener<AuthResult> onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }
}