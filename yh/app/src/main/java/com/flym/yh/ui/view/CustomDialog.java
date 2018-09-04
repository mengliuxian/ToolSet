package com.flym.yh.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.utils.TextWatcherWrapper;

/**
 * Created by mengl on 2018/3/21.
 */

public class CustomDialog extends Dialog {


    public CustomDialog(final Context context) {
        super(context, R.style.CustomDialog);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Activity activity = (Activity) context;
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    //强制隐藏键盘
                    imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
    }

    public static Dialog registerSuccess(Context context, View.OnClickListener left, View.OnClickListener rght) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_register_success, null);
        final Dialog dialog = initDialog(context, view, false);
        if (left == null) {
            left = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            };
        }
        TextView no = (TextView) view.findViewById(R.id.no_text);
        TextView yes = (TextView) view.findViewById(R.id.yes_text);

        no.setOnClickListener(left);
        yes.setOnClickListener(rght);
        return dialog;
    }

    public static Dialog amendContentHint(Context context, String title, String hint, View.OnClickListener rght, final EditTextReultListence listener) {

        View view = LayoutInflater.from(context).inflate(R.layout.amend_content_hint, null);
        final Dialog dialog = initDialog(context, view, true);

        TextView submit = (TextView) view.findViewById(R.id.submit);
        TextView titleText = (TextView) view.findViewById(R.id.title);
        EditText edit = (EditText) view.findViewById(R.id.edit);
        edit.setHint(hint);

        titleText.setText(title);
        edit.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                listener.onEditReult(s.toString());
            }
        });

        submit.setOnClickListener(rght);
        return dialog;
    }

    public static Dialog textContentHint(Context context, String title, String hint, View.OnClickListener rght, final EditTextReultListence listener) {

        View view = LayoutInflater.from(context).inflate(R.layout.text_content_hint, null);
        final Dialog dialog = initDialog(context, view, true);

        TextView submit = (TextView) view.findViewById(R.id.submit);
        TextView titleText = (TextView) view.findViewById(R.id.title);
        TextView edit = (TextView) view.findViewById(R.id.edit);
        edit.setHint(hint);
        titleText.setText(title);
        submit.setOnClickListener(rght);
        return dialog;
    }


    public static Dialog genderContentHint(final Context context, String title, View.OnClickListener rght, final EditTextReultListence listener) {

        View view = LayoutInflater.from(context).inflate(R.layout.gender_content_hint, null);
        final Dialog dialog = initDialog(context, view, true);

        TextView submit = (TextView) view.findViewById(R.id.submit);
        TextView titleText = (TextView) view.findViewById(R.id.title);
        final ImageView woman = (ImageView) view.findViewById(R.id.check_num_woman);
        final ImageView man = (ImageView) view.findViewById(R.id.check_num_man);
        RelativeLayout manLayout = (RelativeLayout) view.findViewById(R.id.man_layout);
        RelativeLayout womanLayout = (RelativeLayout) view.findViewById(R.id.woman_layout);

        manLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man.setVisibility(View.VISIBLE);
                woman.setVisibility(View.INVISIBLE);
                listener.onEditReult(context.getString(R.string.man));
            }
        });
        womanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man.setVisibility(View.INVISIBLE);
                woman.setVisibility(View.VISIBLE);
                listener.onEditReult(context.getString(R.string.woman));

            }
        });

        titleText.setText(title);
        submit.setOnClickListener(rght);
        return dialog;
    }

    public static Dialog sendPrescription(final Context context, View.OnClickListener rght) {

        View view = LayoutInflater.from(context).inflate(R.layout.send_prescription, null);
        final Dialog dialog = initDialog(context, view, true);

        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        TextView canfim = (TextView) view.findViewById(R.id.canfim);


        canfim.setOnClickListener(rght);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog addFastReply(final Context context, View.OnClickListener rght, final EditTextReultListence listener) {

        View view = LayoutInflater.from(context).inflate(R.layout.add_fast_reply, null);
        final Dialog dialog = initDialog(context, view, true);

        TextView cancle = (TextView) view.findViewById(R.id.cancle);
        EditText edContent = (EditText) view.findViewById(R.id.ed_content);
        TextView canfim = (TextView) view.findViewById(R.id.canfim);

        edContent.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (listener != null) {
                    listener.onEditReult(s.toString());
                }
            }
        });

        canfim.setOnClickListener(rght);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog sendPrescriptionSucces(final Context context, View.OnClickListener rght) {

        View view = LayoutInflater.from(context).inflate(R.layout.send_prescription_succes, null);
        final Dialog dialog = initDialog(context, view, true);

        TextView canfim = (TextView) view.findViewById(R.id.canfim);

        canfim.setOnClickListener(rght);
        return dialog;
    }

    public static Dialog theTitleContentHint(final Context context, String title, View.OnClickListener rght, final EditTextReultListence listener) {

        View view = LayoutInflater.from(context).inflate(R.layout.the_title_content_hint, null);
        final Dialog dialog = initDialog(context, view, true);


        TextView submit = (TextView) view.findViewById(R.id.submit);
        TextView titleText = (TextView) view.findViewById(R.id.title);
        final TextView text = (TextView) view.findViewById(R.id.text);
        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        final TextView chiefPhysician = (TextView) view.findViewById(R.id.chief_physician);
        final TextView associateSeniorDoctor = (TextView) view.findViewById(R.id.associate_senior_doctor);
        final TextView attendingPhysician = (TextView) view.findViewById(R.id.attending_physician);
        final TextView resident = (TextView) view.findViewById(R.id.resident);
        final TextView physician = (TextView) view.findViewById(R.id.physician);
        final TextView associateProfessor = (TextView) view.findViewById(R.id.associate_professor);
        final TextView professor = (TextView) view.findViewById(R.id.professor);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = null;
                switch (v.getId()) {
                    case R.id.chief_physician:
                        chiefPhysician.setSelected(true);
                        associateSeniorDoctor.setSelected(false);
                        attendingPhysician.setSelected(false);
                        resident.setSelected(false);
                        physician.setSelected(false);
                        associateProfessor.setSelected(false);
                        professor.setSelected(false);
                        data = chiefPhysician.getText().toString();
                        break;
                    case R.id.associate_senior_doctor:
                        chiefPhysician.setSelected(false);
                        associateSeniorDoctor.setSelected(true);
                        attendingPhysician.setSelected(false);
                        resident.setSelected(false);
                        physician.setSelected(false);
                        associateProfessor.setSelected(false);
                        professor.setSelected(false);
                        data = associateSeniorDoctor.getText().toString();
                        break;
                    case R.id.attending_physician:
                        chiefPhysician.setSelected(false);
                        associateSeniorDoctor.setSelected(false);
                        attendingPhysician.setSelected(true);
                        resident.setSelected(false);
                        physician.setSelected(false);
                        associateProfessor.setSelected(false);
                        professor.setSelected(false);
                        data = attendingPhysician.getText().toString();
                        break;
                    case R.id.resident:
                        chiefPhysician.setSelected(false);
                        associateSeniorDoctor.setSelected(false);
                        attendingPhysician.setSelected(false);
                        resident.setSelected(true);
                        physician.setSelected(false);
                        associateProfessor.setSelected(false);
                        professor.setSelected(false);
                        data = resident.getText().toString();
                        break;
                    case R.id.physician:
                        chiefPhysician.setSelected(false);
                        associateSeniorDoctor.setSelected(false);
                        attendingPhysician.setSelected(false);
                        resident.setSelected(false);
                        physician.setSelected(true);
                        associateProfessor.setSelected(false);
                        professor.setSelected(false);
                        data = physician.getText().toString();
                        break;
                    case R.id.associate_professor:
                        chiefPhysician.setSelected(false);
                        associateSeniorDoctor.setSelected(false);
                        attendingPhysician.setSelected(false);
                        resident.setSelected(false);
                        physician.setSelected(false);
                        associateProfessor.setSelected(true);
                        professor.setSelected(false);
                        data = associateProfessor.getText().toString();
                        break;
                    case R.id.professor:
                        chiefPhysician.setSelected(false);
                        associateSeniorDoctor.setSelected(false);
                        attendingPhysician.setSelected(false);
                        resident.setSelected(false);
                        physician.setSelected(false);
                        associateProfessor.setSelected(false);
                        professor.setSelected(true);
                        data = professor.getText().toString();
                        break;
                }
                text.setText(data);
                listener.onEditReult(data);
                layout.setVisibility(View.GONE);

            }
        };
        chiefPhysician.setOnClickListener(onClickListener);
        associateSeniorDoctor.setOnClickListener(onClickListener);
        attendingPhysician.setOnClickListener(onClickListener);
        resident.setOnClickListener(onClickListener);
        physician.setOnClickListener(onClickListener);
        associateProfessor.setOnClickListener(onClickListener);
        professor.setOnClickListener(onClickListener);


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
            }
        });


        titleText.setText(title);
        submit.setOnClickListener(rght);
        return dialog;
    }


    public static Dialog initDialog(Context context, View view, boolean cancel) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(cancel);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(attributes);
        dialog.show();
        return dialog;
    }

    public interface EditTextReultListence {
        void onEditReult(String s);
    }

}
