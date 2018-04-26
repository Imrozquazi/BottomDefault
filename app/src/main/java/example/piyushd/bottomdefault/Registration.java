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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;



public class Registration extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView mName,mEmail,mPass,mContact;
    private  Button mbtnSignup;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



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


        View android = inflater.inflate(R.layout.fragment_registration, container, false);

        //linking java to xml
        mName = (TextView) android.findViewById(R.id.username);
        mEmail = ( TextView) android.findViewById(R.id.useremail);
        mContact = ( TextView) android.findViewById(R.id.mobile);
        mPass = ( TextView) android.findViewById(R.id.password);
        //mCol = (AutoCompleteTextView) findViewById(R.id.signup_College);

        //firebase linking
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mbtnSignup= (Button) android.findViewById(R.id.register);

        mbtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();
            }
        });

        return android;
    }

    private void signup() {

        //fetching details
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();

        String con = mContact.getText().toString();
        String pass = mPass.getText().toString();



       // mProSignUp.setTitle("Registering User");
        //mProSignUp.setMessage("Please wait while we create your account");
       // mProSignUp.setCanceledOnTouchOutside(false);
       // mProSignUp.show();


        //make a map to add it to database
        final Map<String, String> datamap = new HashMap<String, String>();
        datamap.put("Name", name);
        datamap.put("Email", email);
        datamap.put("Contact", con);
        datamap.put("pass",pass);

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    mDatabase.setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getActivity(),"Registered",Toast.LENGTH_LONG);

                            } else {

                                Toast.makeText(getActivity(), "Oopps...!!...Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }


}
