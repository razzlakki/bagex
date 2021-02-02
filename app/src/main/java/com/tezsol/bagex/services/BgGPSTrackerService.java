package com.tezsol.bagex.services;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class BgGPSTrackerService extends IntentService {

    public BgGPSTrackerService() {
        super("BgGPSTrackerService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

//    private FusedLocationProviderClient mFusedLocationClient;
//    private LocationCallback locationCallback;
//    public static int INTERVEL = 15;
//
//    public BgGPSTrackerService() {
//        super("BgGPSTrackerService");
//    }
//
//    public static void startLatLongService(Context context) {
//        Intent intent = new Intent(context, BgGPSTrackerService.class);
//        PendingIntent pintent = PendingIntent.getService(context, 0, intent, 0);
//        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), INTERVEL * 60 * 1000, pintent);
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        startLocationUpdated();
//    }
//
//    private void startLocationUpdated() {
//        Log.e("BgGPSTrackerJobService", "****** Location Before ********");
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Log.e("BgGPSTrackerJobService", "****** Location Enabled ********");
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setFastestInterval(INTERVEL * 1000);
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    if (location != null) {
//                        setDataToServer(location);
//                    }
//                }
//            }
//        };
//
//        mFusedLocationClient.requestLocationUpdates(new LocationRequest(), locationCallback, Looper.myLooper());
//
//        mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
//            if (location != null) {
//                setDataToServer(location);
//            }
//        });
//    }
//
//    private void setDataToServer(Location location) {
//        MessageEvent data = new MessageEvent();
//        data.location = location;
//        data.gpsEnabled = true;
//        EventBus.getDefault().post(data);
//        SharedPreferenceUtil.putString(this, SharedPreferenceUtil.LATITUDE, location.getLatitude() + "");
//        SharedPreferenceUtil.putString(this, SharedPreferenceUtil.LONGITUDE, location.getLongitude() + "");
//
//        String lastTrack = SharedPreferenceUtil.getString(this, SharedPreferenceUtil.LAST_LOC_TRACK_POINT);
//        if (lastTrack != null) {
//            if (lastTrack.equalsIgnoreCase(location.getLatitude() + "," + location.getLongitude()))
//                return;
//        }
//        SharedPreferenceUtil.putString(this, SharedPreferenceUtil.LAST_LOC_TRACK_POINT, location.getLatitude() + "," + location.getLongitude());
//        LocationModel locationModel = new LocationModel();
//        locationModel.setLatitude(location.getLatitude() + "");
//        locationModel.setLongitude(location.getLongitude() + "");
//        stopLocationUpdates();
//        new UpdateCourierAsyncTask2(this, locationModel,
//                responseModel -> {
//                    sendToLocalServer(location);
//                    if (SharedPreferenceUtil.getBoolean(this, SharedPreferenceUtil.IS_LOGIN)) {
//                        initTimer();
//                    }
//                    stopSelf();
//                }).execute();
//    }
//
//    private void initTimer() {
//        startLatLongService(this);
//    }
//
//
//    @Override
//    public void onDestroy() {
//        stopLocationUpdates();
//        startLocationUpdated();
//        super.onDestroy();
//    }
//
//    private void stopLocationUpdates() {
//        if (mFusedLocationClient != null) {
//            mFusedLocationClient.removeLocationUpdates(locationCallback);
//            Log.e("BgGPSTrackerJobService", "****** Location updated stopped ********");
//        }
//    }

}