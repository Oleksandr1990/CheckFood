package org.checkfood.widget;





import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.checkfood.model.Additive;
import org.checkfood.model.Product;
import org.checkfood.R;

public class AdditivesBaseAdapter extends BaseAdapter {
    private Product product;
    private Context context;
    private Typeface typefaceAdditiveNumber;
    private Typeface typefaceAdditiveName;
    private Typeface typefaceAdditiveDescription;

    public AdditivesBaseAdapter(Context context, Product product) {
	super();
	this.context = context;
	this.product = product;
	 typefaceAdditiveNumber = Typeface.createFromAsset(context.getAssets(),
	            "fonts/Roboto-Black.ttf");
	 typefaceAdditiveName = Typeface.createFromAsset(context.getAssets(),
	            "fonts/Roboto-Light.ttf");
	 typefaceAdditiveDescription = Typeface.createFromAsset(context.getAssets(),
	            "fonts/Roboto-Regular.ttf");
    }

    @Override
    public int getCount() {
	return product.getAdditiveList().size();
    }

    @Override
    public Additive getItem(int position) {
	return product.getAdditiveList().get(position);
    }

    @Override
    public long getItemId(int position) {
	Additive additive = getItem(position);
	return product.getAdditiveList().indexOf(additive);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder viewHolder = null;

	Additive additive = getItem(position);

	if (convertView == null) {
	    convertView = View.inflate(context, R.layout.additives_base_adapter_row, null);
	    viewHolder = new ViewHolder();
	    viewHolder.additiveViewNumber = (AdditiveView) convertView
		    .findViewById(R.id.additiveViewAdditiviesBaseAdapterNumber);
	    viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textViewAdditiviesBaseAdapterName);
	    viewHolder.dangerViewDangerLevel = (DangerView) convertView
		    .findViewById(R.id.dangerViewAdditiviesBaseAdapterDangerLevel);
	    viewHolder.textViewDangerDescription = (TextView) convertView
		    .findViewById(R.id.textViewAdditiviesBaseAdapterDangerDescription);
	    viewHolder.imageViewLinkAllowed = (ImageView) convertView
		    .findViewById(R.id.imageViewAdditiviesBaseAdapterLinkAllowed);
	    viewHolder.imageViewLinkNotAllowed = (ImageView) convertView
		    .findViewById(R.id.imageViewAdditiviesBaseAdapterLinkNotAllowed);

	    viewHolder.linearLayoutAllowed = (LinearLayout) convertView.findViewById(R.id.linearLayoutAllowed);
	    viewHolder.linearLayoutNotAllowed = (LinearLayout) convertView.findViewById(R.id.linearLayoutNotAllowed);

	    viewHolder.textViewAllowedDescription = (TextView) convertView
		    .findViewById(R.id.textViewAdditiviesBaseAdapterNotAllowedDescription);

	    convertView.setTag(viewHolder);

	} else {
	    viewHolder = (ViewHolder) convertView.getTag();
	}
	
	viewHolder.additiveViewNumber.setTypeface(typefaceAdditiveNumber);
	viewHolder.textViewName.setTypeface(typefaceAdditiveName);
	viewHolder.textViewAllowedDescription.setTypeface(typefaceAdditiveDescription);
	viewHolder.textViewDangerDescription.setTypeface(typefaceAdditiveDescription);

	viewHolder.additiveViewNumber.setDanger(additive.getDanger());
	viewHolder.additiveViewNumber.setText(additive.getNumber());
	viewHolder.textViewName.setText(additive.getName());
	viewHolder.textViewName.getEllipsize();

		if (!additive.getNotAllowedEU() && !additive.getNotAllowedRU()) {
			viewHolder.linearLayoutAllowed.setVisibility(View.VISIBLE);
			viewHolder.linearLayoutNotAllowed.setVisibility(View.GONE);

			if (additive.getLink() != null && additive.getLink().toString().length() > 0) {
				viewHolder.imageViewLinkAllowed.setVisibility(View.VISIBLE);
				changeItemEnable(convertView, false);
			} else {
				viewHolder.imageViewLinkAllowed.setVisibility(View.INVISIBLE);
				changeItemEnable(convertView, true);
			}

			viewHolder.dangerViewDangerLevel.setDanger(additive.getDanger());
			prepareDangerDescription(additive.getDanger(), viewHolder.textViewDangerDescription);
		} else {
			viewHolder.linearLayoutAllowed.setVisibility(View.GONE);
			viewHolder.linearLayoutNotAllowed.setVisibility(View.VISIBLE);

			if (additive.getLink() != null && additive.getLink().toString().length() > 0) {
				viewHolder.imageViewLinkNotAllowed.setVisibility(View.VISIBLE);
				changeItemEnable(convertView, false);
			} else {
				viewHolder.imageViewLinkNotAllowed.setVisibility(View.INVISIBLE);
				changeItemEnable(convertView, true);
			}

			if (additive.getNotAllowedEU() && !additive.getNotAllowedRU()) {
				viewHolder.textViewAllowedDescription
						.setText(R.string.textview_title_additives_base_adapter_not_allowed_in_eu);
			}

			if (!additive.getNotAllowedEU() && additive.getNotAllowedRU()) {
				viewHolder.textViewAllowedDescription
						.setText(R.string.textview_title_additives_base_adapter_not_allowed_in_russia);
			}

			if (additive.getNotAllowedEU() && additive.getNotAllowedRU()) {
				viewHolder.textViewAllowedDescription
						.setText(R.string.textview_title_additives_base_adapter_not_allowed_in_eu_and_russia);
			}
		}


	return convertView;
    }

    private void changeItemEnable(View convertView, boolean isEnable) {
	convertView.setEnabled(isEnable);
	convertView.setClickable(isEnable);
	convertView.setFocusable(isEnable);
    }

    private void prepareDangerDescription(int danger, TextView textView) {
	String dangerDesription = "";
	int color = 0;
	switch (danger) {
	case -1:
	    dangerDesription = context
		    .getString(R.string.textview_title_additives_base_adapter_danger_description_no_information);
	    break;

	case 0:
	    dangerDesription = context
		    .getString(R.string.textview_title_additives_base_adapter_danger_description_absolutely_safe);
	    break;

	case 1:
	    dangerDesription = context
		    .getString(R.string.textview_title_additives_base_adapter_danger_description_safe);
	    break;

	case 2:
	    dangerDesription = context
		    .getString(R.string.textview_title_additives_base_adapter_danger_description_safe);
	    break;

	case 3:
	    dangerDesription = context
		    .getString(R.string.textview_title_additives_base_adapter_danger_description_moderately_danger);
	    break;

	case 4:
	    dangerDesription = context
		    .getString(R.string.textview_title_additives_base_adapter_danger_description_danger);
	    break;

	case 5:
	    dangerDesription = context
		    .getString(R.string.textview_title_additives_base_adapter_danger_description_very_danger);
	    break;
	}

	if (danger > -2 && danger < 3) {
	    color = context.getResources().getColor(R.color.color_safe);
	}

	if (danger > 2 && danger < 6) {
	    color = context.getResources().getColor(R.color.color_danger);
	}

	textView.setTextColor(color);
	textView.setText(dangerDesription);

    }

    private static class ViewHolder {
	private AdditiveView additiveViewNumber;
	private TextView textViewName;
	private DangerView dangerViewDangerLevel;
	private TextView textViewDangerDescription;
	private ImageView imageViewLinkAllowed;
	private ImageView imageViewLinkNotAllowed;

	private LinearLayout linearLayoutAllowed;
	private LinearLayout linearLayoutNotAllowed;
	private TextView textViewAllowedDescription;

    }

}
