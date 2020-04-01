package com.putao.loadfilesync;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

/**
 * Created by admin on 2020/3/18.
 */

public class PermissionUtility extends Fragment{

    private static PermissionUtility instance;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String[] permissonText=GetPermissionsText(requestCode);
        if(permissonText!=null)
        {
            UnityPlayer.UnitySendMessage("Game","OnRequestPermissionResult",CheckHasPermission(requestCode)?"0":"-1");
        }
    }

    public static  void  Init() {
        instance = new PermissionUtility();
        UnityPlayer.currentActivity.getFragmentManager().beginTransaction().add(instance, null).commit();
    }

    private static final int CALENDAR = 0;
    private static final int CAMERA = 2;
    private static final int MICROPHONE = 3;
    private static final int ACCESS_FINE_LOCATION = 6;
    private static final int ACCESS_COARSE_LOCATION = 7;
    private static final int STORAGE = 8;
    private static final int BLUETOOTH=9;

    public static boolean CheckHasPermission(int permissiontype) {
        String[] permissonText = GetPermissionsText(permissiontype);
        if (permissonText == null && permissonText.length <= 0)
            return false;
        if (Build.VERSION.SDK_INT > 22) {
            boolean result = CheckPermissionGranted(permissonText[0]);
            if (permissonText.length == 1)
                return result;
            for (int i = 1; i < permissonText.length; i++) {
                result &= CheckPermissionGranted(permissonText[i]);
            }
            return result;
        }
        return true;
    }

    public static void RequestUserPermission(int permissiontype) {
        String[] permissonText = GetPermissionsText(permissiontype);
        if (permissonText == null || permissonText.length <= 0)
            return;
        if (Build.VERSION.SDK_INT > 22) {
            instance.requestPermissions(permissonText, permissiontype);
        }
    }

    public static void ShowAlertWithConfirmCancel(final String title, final String msg, final String confirm, final String cancel) {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new AlertDialog.Builder(UnityPlayer.currentActivity).setTitle(title).setMessage(msg).setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        UnityPlayer.UnitySendMessage("Game", "OnSystemAlertConfirmClick", "");
                    }
                }).setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                dialog.show();
            }
        });
    }

    public static void OpenSetting(int settingPage) {
        switch (settingPage) {
            case 1: //蓝牙设置页面
                UnityPlayer.currentActivity.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));

                break;
            case 2://wifi设置页面
                UnityPlayer.currentActivity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
            default:
                UnityPlayer.currentActivity.startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    }

    private static String[] GetPermissionsText(int type) {
        switch (type) {
            case CALENDAR:
                return new String[]{Manifest.permission.READ_CALENDAR};
            case CAMERA:
                return new String[]{Manifest.permission.CAMERA};
            case MICROPHONE:
                return new String[]{Manifest.permission.RECORD_AUDIO};
            case ACCESS_FINE_LOCATION:
                return new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            case ACCESS_COARSE_LOCATION:
                return new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
            case STORAGE:
                return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            case BLUETOOTH:
                return new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN};
        }
        Log.e("权限类型", "传入权限类型尚未支持:" + type);
        return null;
    }

    private static boolean CheckPermissionGranted(String permissionText)
    {
        return UnityPlayer.currentActivity.checkSelfPermission(permissionText)==PackageManager.PERMISSION_GRANTED;
    }
}
