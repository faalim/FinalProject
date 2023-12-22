package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CreateNewListFragment extends DialogFragment {


    interface AddListFragmentListener{
        void addNewList(String listName, String listdescrip);
    }

    AddListFragmentListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_new_list_fragment, container, false);

        EditText listName = v.findViewById(R.id.listEdit);
        EditText shortDesrip = v.findViewById(R.id.editShortDescrip);
        Button save = v.findViewById(R.id.saveList);
        Button cancel = (v.findViewById(R.id.cancelList));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!listName.getText().toString().isEmpty()) {

                    listener.addNewList(listName.getText().toString(), shortDesrip.getText().toString());

                    dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



        return v;
    }

}
