package bluescreen1.poat.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import bluescreen1.poat.R;

public class Sign_In_Fragment extends DialogFragment {

    View root;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root = inflater.inflate(R.layout.sign_in_dialog,null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(root)
                // Add action buttons
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        setData();
                        mListener.onLoginDialogPositiveClick(Sign_In_Fragment.this);
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Sign_In_Fragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public interface LoginDialogListener {
        public void onLoginDialogPositiveClick(DialogFragment dialog);
        public void onLoginDialogNegativeClick(DialogFragment dialog);
    }
    // Use this instance of the interface to deliver action events
    LoginDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (LoginDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

    public void setData(){

        EditText username = (EditText) root.findViewById(R.id.login_username);
        EditText password = (EditText) root.findViewById(R.id.login_password);
        LoginActivity parent = (LoginActivity) getActivity();
        parent.setData(username.getText().toString(), password.getText().toString());
    }
}