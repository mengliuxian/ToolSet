package com.flym.yh.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.flym.yh.R;
import com.flym.yh.base.BasePopupWindow;
import com.flym.yh.utils.FragmentUtil;

/**
 * Created by mengl on 2018/3/22.
 */

public class PositionPop extends BasePopupWindow implements View.OnClickListener {

    private final TextView chiefPhysician;
    private final TextView associateSeniorDoctor;
    private final TextView attendingPhysician;
    private final TextView resident;
    private final TextView physician;
    private final TextView associateProfessor;
    private final TextView professor;
    private ReturnDataListence listence;

    public PositionPop(Context context, int w, ReturnDataListence r) {
        super(context, R.layout.pop_position);
        this.setWidth(w);
        listence = r;
        chiefPhysician = (TextView) rootView.findViewById(R.id.chief_physician);
        associateSeniorDoctor = (TextView) rootView.findViewById(R.id.associate_senior_doctor);
        attendingPhysician = (TextView) rootView.findViewById(R.id.attending_physician);
        resident = (TextView) rootView.findViewById(R.id.resident);
        physician = (TextView) rootView.findViewById(R.id.physician);
        associateProfessor = (TextView) rootView.findViewById(R.id.associate_professor);
        professor = (TextView) rootView.findViewById(R.id.professor);

        chiefPhysician.setOnClickListener(this);
        associateSeniorDoctor.setOnClickListener(this);
        attendingPhysician.setOnClickListener(this);
        resident.setOnClickListener(this);
        physician.setOnClickListener(this);
        associateProfessor.setOnClickListener(this);
        professor.setOnClickListener(this);

    }

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
        listence.returnData(data);
        dismiss();

    }

    public interface ReturnDataListence {
        void returnData(String content);
    }

}
