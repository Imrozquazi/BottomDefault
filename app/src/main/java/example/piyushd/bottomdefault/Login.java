package example.piyushd.bottomdefault;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth mAuth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mUser,mPass;
    private Button mlogin;


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

        View android = inflater.inflate(R.layout.fragment_registration, container, false);




        //linking java to xml
        mUser= (TextView) android.findViewById(R.id.username);
        mPass= ( TextView) android.findViewById(R.id.password);
        mlogin=(Button) android.findViewById(R.id.login);

        //FIREBASE
        mAuth= FirebaseAuth.getInstance();


        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user=mUser.getText().toString();
                String pass=mPass.getText().toString();
                signin(user,pass);
            }
        });
        return android;
    }



    private void signin(String user,String pass) {

        mAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                   Toast.makeText(getActivity(),"Succesfully logged in",Toast.LENGTH_LONG);

                }
                else {
                   // mProLogin.dismiss();
                    Toast.makeText(getActivity(),"UserName Or Password Incorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
