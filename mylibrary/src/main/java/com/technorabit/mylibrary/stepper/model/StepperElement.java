package com.technorabit.mylibrary.stepper.model;

public class StepperElement {
    public boolean isActive;
    public String headName;
    public int headerColor;
    public int statusColor;
    public String statusName;

    public StepperElement(boolean isActive, String headName, int headerColor, int statusColor, String statusName) {
        this.isActive = isActive;
        this.headName = headName;
        this.headerColor = headerColor;
        this.statusColor = statusColor;
        this.statusName = statusName;
    }
}
