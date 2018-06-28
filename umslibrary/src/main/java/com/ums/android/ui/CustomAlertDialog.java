package com.ums.android.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import com.ums.android.R;


/**
 * 
 * Create custom Dialog windows for your application Custom dialogs rely on
 * custom layouts wich allow you to create and use your own look & feel.
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 *
 */
public class CustomAlertDialog extends Dialog {

	public CustomAlertDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomAlertDialog(Context context) {
		super(context);
	}

	public void setMessage(String msg) {
		((TextView)findViewById(R.id.tv_message)).setText(msg);
	}
	
	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {

		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private boolean cancelable;

		private OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		/**
		 * Set the Dialog message from String
		 * @return
		 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}
		
		
		/**
		 * Set the Dialog cancel 
		 * 
		 * @param cancelable
		 * @return
		 */
		public Builder setCancelable(boolean cancelable){
			this.cancelable = cancelable;
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * contentView is not added to the Dialog...
		 * 
		 * @param v
		 * @return
		 */
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the positive button text and it's listener
		 * 
		 * @param positiveButtonText
		 * @param listener
		 * @return
		 */
		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button resource and it's listener
		 * 
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button text and it's listener
		 * 
		 * @param negativeButtonText
		 * @param listener
		 * @return
		 */
		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		/**
		 * Create the custom dialog
		 */
		public CustomAlertDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomAlertDialog dialog = new CustomAlertDialog(context,
					R.style.customer_dialog);
			View layout = inflater.inflate(
					R.layout.ums_common_custom_alertdialog, null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			if(title == null ||title.equals("")){
			}else{
				((TextView) layout.findViewById(R.id.tv_title)).setText(title);
			}
			
			// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.btn_positiveButton))
						.setText(positiveButtonText);

				((Button) layout.findViewById(R.id.btn_positiveButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								if (positiveButtonClickListener != null) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
								dialog.dismiss();
							}
						});

			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.btn_positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.btn_negativeButton))
						.setText(negativeButtonText);

				((Button) layout.findViewById(R.id.btn_negativeButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								if (negativeButtonClickListener != null) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
								dialog.dismiss();
							}
						});

			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.btn_negativeButton).setVisibility(
						View.GONE);
			}
			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.tv_message)).setText(message);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
			}
			dialog.setContentView(layout);
			dialog.setCancelable(cancelable);
			dialog.setCanceledOnTouchOutside(true);
			return dialog;
		}

	}

}
