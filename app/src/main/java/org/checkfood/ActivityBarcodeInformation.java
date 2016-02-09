package org.checkfood;



import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import android.support.v7.widget.Toolbar;
import org.checkfood.model.Additive;
import org.checkfood.model.Product;
import org.checkfood.widget.AdditivesBaseAdapter;
import org.checkfood.widget.DangerView;
import org.checkfood.xml.XMLParser;

import org.checkfood.R;

public class ActivityBarcodeInformation extends ListActivity implements View.OnClickListener {
    private Product product;
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        String xml = getIntent()!=null?getIntent().getStringExtra("xml"):null;
        try {
            this.product = XMLParser.parseProduct(xml);
            if(product.getNotFound()){
                onBackPressed();
                Toast toast = Toast.makeText(getApplicationContext(), "Product not found!", Toast.LENGTH_SHORT);
                toast.show();

            }

        } catch(XmlPullParserException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        boolean isSoundEnabled = settings.getBoolean("sound", true);

        final SoundPool mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        if (isSoundEnabled) {
            int dangerLevel = product.getDanger();
            if (dangerLevel >= 0 && dangerLevel <= 2) {
                mSoundPool.load(this, R.raw.sound_safe, 1);
            }

            if (dangerLevel > 2) {
                mSoundPool.load(this, R.raw.sound_danger, 1);
            }
            mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    mSoundPool.play(sampleId, 1, 1, 1, 0, 1);
                }
            });
        }

                initViews();

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (product.getAdditiveList().size() > 0 && position != 0 && position != getListView().getCount()) {
            Additive additive = product.getAdditiveList().get(position - 1);
            if (additive.getLink() != null && additive.getLink().toString().length() > 0) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(additive.getLink().toString())));
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            RelativeLayout activityBarcodeInformationProductImagePreload = (RelativeLayout)findViewById(R.id.relativeLayoutActivityBarcodeInformationProductImagePreload);
            activityBarcodeInformationProductImagePreload.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(result);

        }
    }



    private void initViews() {
        setContentView(R.layout.layout_activity_barcode_information);

        addHeader();
        addFooter();
        prepareActionBar();


        if (product.getAdditiveList().size() > 0) {
            AdditivesBaseAdapter additivesBaseAdapter = new AdditivesBaseAdapter(getApplicationContext(), product);
           getListView().setAdapter(additivesBaseAdapter);
        } else {
            String[] values = new String[] { getString(R.string.textview_title_activity_barcode_information_empty_list) };
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    R.layout.layout_activity_barcode_information_listview_empty_row, R.id.textViewEmpty, values);
            getListView().setAdapter(arrayAdapter);
        }

    }

    private void addHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.layout_activity_barcode_information_listview_header,
                null);

        ImageView productImage = (ImageView) headerView
                .findViewById(R.id.imageViewActivityBarcodeInformationProductImage);

        RelativeLayout activityBarcodeInformationProductImagePreload = (RelativeLayout) headerView
                .findViewById(R.id.relativeLayoutActivityBarcodeInformationProductImagePreload);


        if (product.getImageLink() != null
                && product.getImageLink().length() > 0) {
            new DownloadImageTask(productImage).execute(product.getImageLink());
        }

        Typeface productName = Typeface.createFromAsset(getAssets(), "fonts/Open_Sans_Condensed_Light.ttf");
        TextView productNameView = (TextView) headerView.findViewById(R.id.textViewActivityBarcodeInformationProductName);
        productNameView.setTypeface(productName);
        productNameView.setText(product.getName());

        TextView productDescription = (TextView) headerView
                .findViewById(R.id.textViewActivityBarcodeInformationProductDescription);
        Typeface dangerDescription = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        productDescription.setTypeface(dangerDescription);

        setProductDescription(product, productDescription);

        DangerView productDanger = (DangerView) headerView
                .findViewById(R.id.dangerViewActivityBarcodeInformationProductDanger);
        productDanger.setDanger(product.getDanger());

        headerView.setEnabled(false);
        headerView.setClickable(false);
        headerView.setFocusable(false);
        headerView.setFocusableInTouchMode(false);

       getListView().addHeaderView(headerView);
    }

    private void addFooter() {
        View footerView = getLayoutInflater().inflate(R.layout.layout_activity_barcode_information_listview_footer,
                null);
        Button productLink = (Button) footerView.findViewById(R.id.buttonActivityBarcodeInformationProductLink);
        productLink.setOnClickListener(this);
        footerView.setEnabled(false);
        footerView.setClickable(false);
        getListView().addFooterView(footerView);
    }

    private void setProductDescription(Product product, TextView productDescription) {
        switch (product.getDanger()) {
            case -1:
                productDescription
                        .setText(R.string.textview_title_activity_barcode_information_panel_product_description_no_info);
                break;

            case 0:
                productDescription
                        .setText(R.string.textview_title_activity_barcode_information_panel_product_description_safe);
                break;

            case 1:
                productDescription
                        .setText(R.string.textview_title_activity_barcode_information_panel_product_description_safe);
                break;

            case 2:
                productDescription
                        .setText(R.string.textview_title_activity_barcode_information_panel_product_description_safe);
                break;

            case 3:
                productDescription
                        .setText(R.string.textview_title_activity_barcode_information_panel_product_description_moderately_dangerous);
                break;

            case 4:
                productDescription
                        .setText(R.string.textview_title_activity_barcode_information_panel_product_description_danger);
                break;

            case 5:
                productDescription
                        .setText(R.string.textview_title_activity_barcode_information_panel_product_description_danger);
                break;
        }

        if (product.getDanger() > -2
                && product.getDanger() < 3) {
            productDescription.setTextColor(getResources().getColor(R.color.color_safe));
        }

        if (product.getDanger() > 2
                && product.getDanger()< 6) {
            productDescription.setTextColor(getResources().getColor(R.color.color_danger));
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonActivityBarcodeInformationProductLink:
                if (product.getLink().toString() != null
                        && product.getLink().toString().length() > 0) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(product.getLink().toString())));
                }
                break;
        }
    }

    private void prepareActionBar() {
        String barcode = getIntent().getStringExtra("barcode");
        Toolbar toolbar =  (Toolbar) findViewById(R.id.tool_bar);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        Drawable[] layers = new Drawable[2];

        GradientDrawable gradientDrawable = null;
        TextView tv = (TextView)findViewById(R.id.barcode);
        tv.setText(barcode);
        switch (product.getDanger()) {
            case -1:
                toolbar.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.gradient_activity_barcode_information_panel_gray));
                        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
                        getResources().getColor(R.color.color_gradient_icon_gray_start),
                        getResources().getColor(R.color.color_gradient_icon_gray_end) });
                gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;

            case 0:
                toolbar.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.gradient_activity_barcode_information_panel_green));
                        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
                        getResources().getColor(R.color.color_gradient_icon_green_start),
                        getResources().getColor(R.color.color_gradient_icon_green_end) });
                gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;

            case 1:
                toolbar.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.gradient_activity_barcode_information_panel_yellow));
                        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
                        getResources().getColor(R.color.color_gradient_icon_yellow_start),
                        getResources().getColor(R.color.color_gradient_icon_yellow_end) });
                gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;

            case 2:
                toolbar.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.gradient_activity_barcode_information_panel_orange));
                gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
                        getResources().getColor(R.color.color_gradient_icon_orange_start),
                        getResources().getColor(R.color.color_gradient_icon_orange_end) });
                gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;

            case 3:
                toolbar.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.gradient_activity_barcode_information_panel_mandarin));

                gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
                        getResources().getColor(R.color.color_gradient_icon_mandarin_start),
                        getResources().getColor(R.color.color_gradient_icon_mandarin_end) });
                gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;

            case 4:
                toolbar.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.gradient_activity_barcode_information_panel_red));
                gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
                        getResources().getColor(R.color.color_gradient_icon_red_start),
                        getResources().getColor(R.color.color_gradient_icon_red_end) });
                gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;

            case 5:
                toolbar.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.gradient_activity_barcode_information_panel_red));
                gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
                        getResources().getColor(R.color.color_gradient_icon_red_start),
                        getResources().getColor(R.color.color_gradient_icon_red_end) });
                gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;
        }

        gradientDrawable.setDither(true);
        bitmapDrawable.setDither(true);

        layers[0] = gradientDrawable;
        layers[1] = bitmapDrawable;

        LayerDrawable layerDrawable = new LayerDrawable(layers);
        ImageView i = (ImageView)findViewById(R.id.icon_tb);
        i.setImageDrawable(layerDrawable);
        ImageView arr = (ImageView)findViewById(R.id.arr_left);

        Bitmap bm =  BitmapFactory.decodeResource(getResources(), R.drawable.ic_back);

        BitmapDrawable bd = new BitmapDrawable(getResources(), bm);


        arr.setImageDrawable(bd);
        arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
