package com.chinamobile.hejiaqin.business.ui.login.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;

public class VoipSettingDialog extends Dialog {

    public VoipSettingDialog(Context context) {
        super(context);
    }

    public VoipSettingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        private String userName;
        private String password;
        private int icon;
        private String message;
        private boolean flag;
        private String positiveButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private String voipUserName;
        private String voipPassword;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
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
         * Set the Dialog title from resource
         *
         * @return
         */
        public Builder setIcon(int icon) {
            this.icon = icon;
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

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @return
         */
        public Builder setCancelable(boolean flag) {
            this.flag = flag;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setDefaultInfo(String voipUserName,
                                         String voipPassword) {
            this.voipUserName = voipUserName;
            this.voipPassword = voipPassword;
            return this;
        }


        public VoipSettingDialog show() {
            final VoipSettingDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public VoipSettingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final VoipSettingDialog dialog = new VoipSettingDialog(context, R.style.Dialog);

            final View layout = inflater.inflate(R.layout.dialog_voip_setting, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            TextView tvTitle = (TextView) layout.findViewById(R.id.title);
            if (title == null || "".equals(title)) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
            }

            ImageView iv_icon = (ImageView) layout.findViewById(R.id.iv_icon);
            if (icon == 0) {
                iv_icon.setVisibility(View.GONE);
            } else {
                iv_icon.setVisibility(View.VISIBLE);
            }

            dialog.setCancelable(flag);
            EditText userET = (EditText) layout.findViewById(R.id.voip_user_name);
            EditText passwordET = (EditText) layout.findViewById(R.id.voip_password);
            userET.setText(voipUserName != null ? String.valueOf(voipUserName) : "");
            passwordET.setText(voipPassword!=null? String.valueOf(voipPassword) : "");

            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    EditText userET = (EditText) layout.findViewById(R.id.voip_user_name);
                                    EditText passwordET = (EditText) layout.findViewById(R.id.voip_password);
                                    setUserName(userET.getText().toString());
                                    setPassword(passwordET.getText().toString());
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }


    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }
}