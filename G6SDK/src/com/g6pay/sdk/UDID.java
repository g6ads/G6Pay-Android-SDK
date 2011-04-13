package com.g6pay.sdk;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

/**
 * Class providing information about the unique identifiers of a device.
 * 
 * @author Peter Hsu - silversc3@yahoo.com
 *
 */
public class UDID {

    /**
     * This is a hash on all the stuff to make sure things are unique per
     * user per device..
     */
    public static final String UDID_HASH            = "udid";
    /**
     * This is available only on phone devices.  Also know as the MEID.
     * This is fairly available, but survives wipes and is a better measure
     * of the device than the user.
     * This also will not be available on the simulator
     */
    public static final String UDID_TELEPHONY_ID    = "telephonyId";
    /**
     * This is a 64-bit quantity that is generated and stored when the
     *  device first boots. It is reset when the device is wiped.
     *  There are downsides: First, it is not 100% reliable on releases of 
     *  Android prior to 2.2 (“Froyo”). Also, there has been at least one 
     *  widely-observed bug in a popular handset from a major manufacturer, 
     *  where every instance has the same ANDROID_ID.
     */
    public static final String UDID_ANDROID_ID      = "androidId";
    /**
     * Since Android 2.3 (“Gingerbread”) this is available via 
     * android.os.Build.SERIAL. Devices without telephony are required to
     *  report a unique device ID here; some phones may do so also.
     */
    public static final String UDID_SERIAL_NUMBER   = "serialNumber";
    
    /**
     * Note: use of this method requires the READ_PHONE_STATE permission:
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @param applicationContext
     * @return
     */
    public static HashMap<String, String> getDeviceId(Context ctx) throws java.lang.SecurityException {
        
        String serialNumber = ""; 
        String telephonyId = "";
        String androidId = "";

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serialNumber = (String) get.invoke(c, "ro.serialno");
        } catch (Exception ignored) {
        }
        
        TelephonyManager tm = (TelephonyManager)
            ctx.getSystemService(Context.TELEPHONY_SERVICE);

        if (tm.getDeviceId() != null)
            telephonyId = tm.getDeviceId();
        
        String aId = Secure.getString(ctx.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        if (aId != null)
            androidId = aId;
        
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long)telephonyId.hashCode() << 32) | serialNumber.hashCode());
        
        String deviceId = deviceUuid.toString();
        
        HashMap<String, String> udids = new HashMap<String, String>();
        
        udids.put(UDID_HASH, deviceId);
        udids.put(UDID_ANDROID_ID, androidId);
        udids.put(UDID_TELEPHONY_ID, telephonyId);
        udids.put(UDID_SERIAL_NUMBER, serialNumber);
        
        boolean isEmulator = Build.PRODUCT.equalsIgnoreCase("sdk") ||
        Build.PRODUCT.equalsIgnoreCase("google_sdk");
        if (isEmulator) {
            // TODO: handling for Emulator detection
        }
        // Log.d("G6Pay", udids.toString());
        
        return udids;
    }
}
