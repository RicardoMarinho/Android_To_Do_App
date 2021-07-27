package com.example.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.todoapp.Helper.DataBaseHelper;
import com.example.todoapp.Model.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    private EditText editText;
    private Button button;
    MediaPlayer mediaPlayer;
    Vibrator vibrator;

    private DataBaseHelper helper;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.edittext);
        button = (Button) view.findViewById(R.id.button_save);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.new_item);
        vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);


        helper = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String name = bundle.getString("NAME");
            editText.setText(name);

            if(name.length() > 0 ){
                button.setEnabled(false);
            }
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    button.setEnabled(false);
                    button.setBackgroundColor(Color.GRAY);
                }
                else{
                    button.setEnabled(true);
                    button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final boolean finalIsUpdate = isUpdate;
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Task task;
                String text = editText.getText().toString();


                if(finalIsUpdate){
                    task = new Task(bundle.getString("UID"));
                    task.set_name(text);
                    helper.Update(task);
                }else {
                    task = new Task();
                    task.set_name(text);
                    task.set_status(0);
                    helper.Add(task);
                }
                mediaPlayer.start();
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                dismiss();


            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();

        if(activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
