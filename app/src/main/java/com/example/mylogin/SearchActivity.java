package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


public class SearchActivity extends AppCompatActivity {

    private Button mSearchButton;
    public EditText mSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchEditText = (EditText) findViewById(R.id.editText);

        mSearchEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))) {

                    handled = true;
                }
                return handled;
            }
        });

        mSearchButton = (Button) findViewById(R.id.button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isNetworkAvailable(getApplicationContext())) {
                    String movieName = mSearchEditText.getText().toString();
                    if (!movieName.isEmpty()) {
                        Intent intent = new Intent(getApplicationContext(), OmdbActivity.class);
                        intent.putExtra("movie", mSearchEditText.getText().toString());
                        startActivity(intent);
                    } else {
                        Snackbar.make(v, getResources().getString(R.string.snackbar_title_empty),
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(v, getResources().getString(R.string.network_not_available),
                            Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }
}
