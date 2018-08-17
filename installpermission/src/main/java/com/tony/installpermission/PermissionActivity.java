package com.tony.installpermission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import static com.tony.installpermission.Common.GET_UNKNOWN_APP_SOURCES;
import static com.tony.installpermission.Common.PERMISSION_INSTALL_APK_REQUEST_CODE;

public abstract class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract String getAPKPath();


    protected void installApk()
    {
        File file = new File(getAPKPath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O )
        {
            installAPKInAndroidO();
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(PermissionActivity.this, "com.tony.installpermission.fileProvider",file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(intent);
        }else{
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        }
    }

    @TargetApi(26)
    private void installAPKInAndroidO()
    {
        boolean result = getPackageManager().canRequestPackageInstalls();
        if (result)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(getAPKPath());
            Uri uri = FileProvider.getUriForFile(this, "com.justek.justekad.fileProvider",file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(intent);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, PERMISSION_INSTALL_APK_REQUEST_CODE);
        }
    }

    @TargetApi(26)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_INSTALL_APK_REQUEST_CODE)
        {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                installAPKInAndroidO();
            }else {
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
                startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_UNKNOWN_APP_SOURCES && resultCode == RESULT_OK)
        {
            installAPKInAndroidO();
        }else {
            Log.d("onActivityResult","not ok");
        }
    }
}
