package com.example.mylogin;

import android.R.layout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewHolder> implements AdapterView.OnItemSelectedListener {
    Context context;
    Activity activity;
    ArrayList<NoteModel> arrayList;
    DBHelper dbHelper;
    EditText editId;
    Button btnUpdate;
    Spinner spinner;
    RadioButton radioButton1, radioButton2;
    RadioGroup radioGroup;
    String spinnerValue, type;
    private View view;


    public NotesAdapter(Context context, Activity activity, ArrayList<NoteModel> arrayList) {
        this.context = context;
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public NotesAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotesAdapter.viewHolder holder, final int position) {
        holder.name.setText("Product name:" + arrayList.get(position).getName());
        holder.category.setText("Category:" + arrayList.get(position).getCategory());
        holder.type.setText("Type:" + arrayList.get(position).getType());

        dbHelper = new DBHelper(context);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            ;

            @Override
            public void onClick(View v) {
                //deleting note
                dbHelper.deleteData(arrayList.get(position).get_id());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            ;

            @Override
            public void onClick(View v) {
                //display edit dialog
                showDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name, category, type;
        ImageView delete, edit;

        public viewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            category = itemView.findViewById(R.id.Category);
            type = itemView.findViewById(R.id.type);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerValue = parent.getItemAtPosition(position).toString();
        Log.d("Log2", "spinner" + spinnerValue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("ResourceType")
    public void showDialog(final int pos) {

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.activity_user);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnUpdate = dialog.findViewById(R.id.btn_add);
        editId = dialog.findViewById(R.id.product);
        spinner = dialog.findViewById(R.id.spinner);
        radioButton1 = dialog.findViewById(R.id.radioButton);
        radioButton2 = dialog.findViewById(R.id.radioButton2);


        editId.setText(arrayList.get(pos).getName());
        spinner.setSelected(Boolean.parseBoolean(arrayList.get(pos).getCategory()));

        List<String> categories = new ArrayList<String>();
        categories.add("Bucket");
        categories.add("vessel");
        categories.add("spoon");
        categories.add("Mug");
        categories.add("plate");
        categories.add("glass");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

        radioGroup = dialog.findViewById(R.id.radioGroup);
        type = "";
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        type = "Plastic";
                        radioButton1.isChecked();

                        break;
                    case R.id.radioButton2:
                        type = "Glass";
                        radioButton2.isChecked();
                        break;

                }
            }
        });
        Log.d("Log", "Type" + type);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            ;

            @Override
            public void onClick(View v) {

                dbHelper.updateData(arrayList.get(pos).get_id(), editId.getText().toString(), spinnerValue, type);
                arrayList.get(pos).setName(editId.getText().toString());
                arrayList.get(pos).setCategory(spinnerValue);
                arrayList.get(pos).setType(type);
                Log.d("Log", "Type" + type);
                Log.d("Log", "spinner2" + spinnerValue);
                Log.d("Log", "list" + arrayList.get(pos).getCategory());

                dialog.cancel();

                notifyDataSetChanged();

            }
        });
    }


}
