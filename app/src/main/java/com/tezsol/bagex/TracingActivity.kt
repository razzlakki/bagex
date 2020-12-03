package com.tezsol.bagex

import android.graphics.Color
import com.technorabit.mylibrary.stepper.model.StepperElement
import kotlinx.android.synthetic.main.activity_tracing.*
import kotlinx.android.synthetic.main.app_bar_all.*
import java.util.ArrayList

class TracingActivity : BaseActivity() {

    override fun onPostOnCreate() {
        headerText?.text = "Tracking"
        okBtn.setOnClickListener {

        }

        stepperView.setElements(getElements())
    }

    private fun getElements(): ArrayList<StepperElement>? {
        val elements: ArrayList<StepperElement> = ArrayList()
        elements.add(
            element = StepperElement(
                true,
                "Booking Registered",
                Color.CYAN,
                Color.CYAN,
                "29/10/2020 07:10 am"
            )
        )
        elements.add(
            element = StepperElement(
                false,
                "Booking Assigned",
                Color.CYAN,
                Color.CYAN,
                null
            )
        )
        elements.add(
            element = StepperElement(
                false,
                "Arriving",
                Color.CYAN,
                Color.CYAN,
                null
            )
        )
        elements.add(
            element = StepperElement(
                false,
                "Picked",
                Color.CYAN,
                Color.CYAN,
                null
            )
        )
        elements.add(
            element = StepperElement(
                false,
                "Reached DC",
                Color.CYAN,
                Color.CYAN,
                null
            )
        )
        elements.add(
            element = StepperElement(
                false,
                "To Airline",
                Color.CYAN,
                Color.CYAN,
                null
            )
        )
        return elements;
    }

    override fun getRootResource(): Int {
        return R.layout.activity_tracing
    }
}