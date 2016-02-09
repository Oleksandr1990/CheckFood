package org.checkfood.slidingmenu;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.TextHttpResponseHandler;

import org.checkfood.ActivityBarcodeInformation;
import org.checkfood.R;
import org.checkfood.http.HTTPTool;

import cz.msebera.android.httpclient.Header;

public class EnterFragment extends Fragment {
        public EnterFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_enter, container, false);
        Button enterButton = (Button) rootView.findViewById(R.id.buttonActivityManualInputGetInformation);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final EditText barcode = (EditText) getActivity().findViewById(R.id.editTextActivityManualInputEnteredBarcode);


                HTTPTool.get(barcode.getText().toString(), null, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityBarcodeInformation.class);
                        intent.putExtra("xml", responseString);
                        intent.putExtra("barcode", barcode.getText().toString());
                        startActivity(intent);
                    }
                });




            }

        });
        return rootView;
    }
}