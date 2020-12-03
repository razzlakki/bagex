package com.technorabit.mylibrary.stepper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.technorabit.mylibrary.R;
import com.technorabit.mylibrary.stepper.model.StepperElement;

import java.util.ArrayList;

public class StepperView extends FrameLayout {
    private ArrayList<StepperElement> stepperElements;

    public StepperView(@NonNull Context context) {
        super(context);
    }

    public StepperView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StepperView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setElements(ArrayList<StepperElement> stepperElements) {
        this.stepperElements = stepperElements;
        updateView();
    }

    private void updateView() {
        View baseView = inflate(getContext(), R.layout.steper_base_layout, null);
        LinearLayout linearLayout = baseView.findViewById(R.id.rootStepper);
        linearLayout.removeAllViews();
        for (int i = 0; i < stepperElements.size(); i++) {
            linearLayout.addView(getStepItem(stepperElements.get(i), i == 0, (i == stepperElements.size() - 1)));
            if (!(i == stepperElements.size() - 1))
                linearLayout.addView(addStatusLine(stepperElements.get(i)));
        }
        addView(baseView);
    }

    private View addStatusLine(StepperElement stepperElement) {
        View baseView = inflate(getContext(), R.layout.stepper_line_item, null);
        TextView statusText = baseView.findViewById(R.id.statusText);
        if (stepperElement.statusName != null)
            statusText.setText(stepperElement.statusName);
        else
            statusText.setText("");
        View lineItem = baseView.findViewById(R.id.lineItem);
        lineItem.setBackgroundColor(stepperElement.isActive ? getResources().getColor(R.color.colorPrimaryDark) : getResources().getColor(R.color.inactiveColor));
        return baseView;
    }

    private View getStepItem(StepperElement stepperElement, boolean isFirstItem, boolean isLastItem) {
        View baseView = inflate(getContext(), R.layout.stepper_item, null);
        TextView textView = baseView.findViewById(R.id.headerText);
        textView.setText(stepperElement.headName);
        textView.setTextColor(stepperElement.isActive ? getResources().getColor(R.color.colorPrimaryDark) : getResources().getColor(R.color.inactiveColor));
        FrameLayout ringBg = baseView.findViewById(R.id.ringBg);
        ringBg.setBackgroundResource(stepperElement.isActive ? R.drawable.circle_item : R.drawable.circle_inactive_item);
        FrameLayout circleBg = baseView.findViewById(R.id.circleBg);
        circleBg.setBackgroundResource(stepperElement.isActive ? R.drawable.circle_item_fill : R.drawable.circle_inactive_item_fill);
        return baseView;
    }


}
