package com.tony.sample;

import android.os.Environment;
import android.util.Log;

import com.tony.installpermission.R;

import java.io.File;


/**
 * Created by dev on 7/23/18.
 */

public class FileUtil {

    private static String createFolder(String paramString)
    {
        if (paramString == null)
            throw new NullPointerException("请传入有效的文件夹路径");
        File localFile = new File(paramString);
        if (!(localFile.exists())) {
            boolean result = localFile.mkdirs();
            Log.d("createFolder",String.valueOf(result));
            Log.d("createFolder",paramString);
        }
        return paramString;
    }

    public static String getAppPath(boolean isCache,String groupId)
    {
        String str;
        boolean hasCard = false;
        if (isCache)
        {
            str = App.getContext().getCacheDir().getAbsolutePath() + File.separator;
        }else {
            if (hasCard = hasSdcard()) {
                str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
            } else {
                str = Environment.getDataDirectory().getAbsolutePath() + File.separator;
            }
        }
        Log.d("hasCard",String.valueOf(hasCard)+" - "+str);
        return createFolder(str + App.getContext().getString(R.string.app_name) + File.separator + groupId + File.separator);

    }

    private static boolean hasSdcard()
    {
        return Environment.getExternalStorageState().equals("mounted");
    }

}
