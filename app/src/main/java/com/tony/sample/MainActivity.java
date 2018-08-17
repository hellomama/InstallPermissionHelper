package com.tony.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tony.installpermission.PermissionActivity;
import com.tony.installpermission.R;

import java.io.File;

public class MainActivity extends PermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.install);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                installApk();
            }
        });
    }

    @Override
    public String getAPKPath() {
        File file = new File(FileUtil.getAppPath(false,"upgrade"),"JustekAD.apk");
        return file.getPath();
    }
}
