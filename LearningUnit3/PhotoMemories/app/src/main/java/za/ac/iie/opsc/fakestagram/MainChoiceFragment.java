package za.ac.iie.opsc.fakestagram;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainChoiceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainChoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainChoiceFragment newInstance(String param1, String param2) {
        MainChoiceFragment fragment = new MainChoiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_main_choice, container, false);
        Button btn_local = view.findViewById(R.id.btn_local);
        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLocalImages = new Intent(getActivity(), LocalImagesActivity.class);
                startActivity(openLocalImages);
            }
        });

        Button btn_cloud = view.findViewById(R.id.btn_cloud);
        btn_cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCloudImages = new Intent(getActivity(), CloudImagesActivity.class);
                startActivity(openCloudImages);
            }
        });
        return view;
    }
}