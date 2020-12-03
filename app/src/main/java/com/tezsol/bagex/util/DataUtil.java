package com.tezsol.bagex.util;

import com.tezsol.bagex.R;
import com.tezsol.bagex.model.response.ExceptionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataUtil {
    private static DataUtil instance;
    private int pickupSelectedItem;
    private List<PickupBtnItem> pickUpListItems = new ArrayList<>();
    private List<ExceptionType> exceptionTypes = new ArrayList<>();
    private HashMap<String, String> scanStatus = new HashMap<>();
    public static String OFD = "OFD";
    public static String OUT_BOUND = "OutBound";
    public static String IN_BOUND = "InBound";
    public static String EXCEPTION = "Exception";
    public static String POD = "POD";
    public static String RTS = "RTS";


    public static DataUtil getInstance() {
        if (instance == null)
            instance = new DataUtil();
        return instance;
    }

    private DataUtil() {
        initDefaultItems();
        initScanDetails();
        initExceptions();
    }

    public List<ExceptionType> getExceptionTypes() {
        return exceptionTypes;
    }

    private void initExceptions() {
        exceptionTypes = new ArrayList<>();
        exceptionTypes.add(new ExceptionType("Refused By Branch Manager", 1));
        exceptionTypes.add(new ExceptionType("Wrong Mobile No", 2));
        exceptionTypes.add(new ExceptionType("Cnee Reached Maximum Allowed Sims", 3));
        exceptionTypes.add(new ExceptionType("Refused To Pay COD Amount", 4));
        exceptionTypes.add(new ExceptionType("Cancelled On Shipper Request", 5));
        exceptionTypes.add(new ExceptionType("Refuse To Acknowledge Occ Form", 6));
        exceptionTypes.add(new ExceptionType("Order Items Not Found", 7));
        exceptionTypes.add(new ExceptionType("Cancelled The Order", 8));
        exceptionTypes.add(new ExceptionType("Delivery Attempted /Customer Not Available", 9));
        exceptionTypes.add(new ExceptionType("Mobile Switched Off", 10));
    }

    private void initScanDetails() {
        scanStatus = new HashMap<>();
        scanStatus.put(OUT_BOUND, "SD006");
        scanStatus.put(IN_BOUND, "SD005");
        scanStatus.put(EXCEPTION, "SD004");
        scanStatus.put(OFD, "SAR07");
        scanStatus.put(POD, "SAR08");
        scanStatus.put(RTS, "SAR09");
    }

    public String getScanStatus(String key) {
        return scanStatus.get(key);
    }

    private void initDefaultItems() {
        pickUpListItems = new ArrayList<>();
        pickUpListItems.add(new PickupBtnItem("Yournext", R.drawable.ic_user));
        pickUpListItems.add(new PickupBtnItem("Call", R.drawable.ic_call));
        pickUpListItems.add(new PickupBtnItem("Whatsapp", R.drawable.ic_whatsapp));
        pickUpListItems.add(new PickupBtnItem("Message", R.drawable.ic_message));
        pickUpListItems.add(new PickupBtnItem("POD", R.drawable.ic_user));
    }


    public List<PickupBtnItem> getPickUpListItems() {
        return pickUpListItems;
    }

    public int getPickupSelectedItem() {
        return pickupSelectedItem;
    }

    public void setPickupSelectedItem(int pickupSelectedItem) {
        this.pickupSelectedItem = pickupSelectedItem;
    }


    public static class PickupBtnItem {
        private String name;
        private int icon;

        PickupBtnItem(String name, int icon) {
            this.name = name;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public int getIcon() {
            return icon;
        }
    }
}
