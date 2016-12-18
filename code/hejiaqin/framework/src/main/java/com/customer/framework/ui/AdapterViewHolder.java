package com.customer.framework.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class AdapterViewHolder {
	private final SparseArray<View> views;
	private final Context context;
	private int position;
	private View converView;

	private AdapterViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.context = context;
		this.position = position;
		this.views = new SparseArray<View>();
		converView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		converView.setTag(this);
	}

	public static AdapterViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId,
			int position) {
		if (convertView == null) {
            return new AdapterViewHolder(context, parent, layoutId, position);
		}
        AdapterViewHolder existingHelper = (AdapterViewHolder) convertView.getTag();
		existingHelper.position = position;
		return existingHelper;
	}

	public <T extends View> T getView(int viewId) {
		return retrieveView(viewId);
	}

	public AdapterViewHolder setText(int viewId, String value) {
		TextView view = retrieveView(viewId);
		view.setText(value);
		return this;
	}

	public AdapterViewHolder setText(int viewId, Spanned value) {
		TextView view = retrieveView(viewId);
		view.setText(value);
		return this;
	}

	public AdapterViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
		View view = retrieveView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	public AdapterViewHolder setImageResource(int viewId, int imageResId) {
		ImageView view = retrieveView(viewId);
		view.setImageResource(imageResId);
		return this;
	}

	public AdapterViewHolder setBackgroundColor(int viewId, int color) {
		View view = retrieveView(viewId);
		view.setBackgroundColor(color);
		return this;
	}

	public AdapterViewHolder setBackgroundRes(int viewId, int backgroundRes) {
		View view = retrieveView(viewId);
		view.setBackgroundResource(backgroundRes);
		return this;
	}

	public AdapterViewHolder setTextColor(int viewId, int textColor) {
		TextView view = retrieveView(viewId);
		view.setTextColor(textColor);
		return this;
	}

	public AdapterViewHolder setTextColorRes(int viewId, int textColorRes) {
		TextView view = retrieveView(viewId);
		view.setTextColor(context.getResources().getColor(textColorRes));
		return this;
	}

	public AdapterViewHolder setImageDrawable(int viewId, Drawable drawable) {
		ImageView view = retrieveView(viewId);
		view.setImageDrawable(drawable);
		return this;
	}

	public AdapterViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView view = retrieveView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}

	public AdapterViewHolder setVisible(int viewId, boolean visible) {
		View view = retrieveView(viewId);
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
		return this;
	}

	public AdapterViewHolder linkify(int viewId) {
		TextView view = retrieveView(viewId);
		Linkify.addLinks(view, Linkify.ALL);
		return this;
	}

	public AdapterViewHolder setTypeface(int viewId, Typeface typeface) {
		TextView view = retrieveView(viewId);
		view.setTypeface(typeface);
		view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		return this;
	}

	public AdapterViewHolder setTypeface(Typeface typeface, int... viewIds) {
		for (int viewId : viewIds) {
			TextView view = retrieveView(viewId);
			view.setTypeface(typeface);
			view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		}
		return this;
	}

	public AdapterViewHolder setProgress(int viewId, int progress) {
		ProgressBar view = retrieveView(viewId);
		view.setProgress(progress);
		return this;
	}

	public AdapterViewHolder setProgress(int viewId, int progress, int max) {
		ProgressBar view = retrieveView(viewId);
		view.setMax(max);
		view.setProgress(progress);
		return this;
	}

	public AdapterViewHolder setMax(int viewId, int max) {
		ProgressBar view = retrieveView(viewId);
		view.setMax(max);
		return this;
	}

	public AdapterViewHolder setRating(int viewId, float rating) {
		RatingBar view = retrieveView(viewId);
		view.setRating(rating);
		return this;
	}

	public AdapterViewHolder setRating(int viewId, float rating, int max) {
		RatingBar view = retrieveView(viewId);
		view.setMax(max);
		view.setRating(rating);
		return this;
	}

	public View getView() {
		return converView;
	}

	public int getPosition() {
		if (position == -1) {
			throw new IllegalStateException(
					"Use BaseAdapterHelper constructor " + "with position if you need to retrieve the position");
		}
		return position;
	}

	private <T extends View> T retrieveView(int viewId) {
		View view = views.get(viewId);
		if (view == null) {
			view = converView.findViewById(viewId);
			views.put(viewId, view);
		}
		return (T) view;
	}
}
