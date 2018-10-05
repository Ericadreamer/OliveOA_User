package com.oliveoa.view.documentmanagement;

import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveoa.util.NotificationUtil;
import com.oliveoa.util.ScreenSizeUtils;
import com.oliveoa.view.R;
import com.othershe.dutil.DUtil;
import com.othershe.dutil.callback.SimpleDownloadCallback;
import com.othershe.dutil.download.DownloadManger;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class DownloadService extends Service {
    private Context mContext;

    private DownloadBinder mBinder = new DownloadBinder();

    public class DownloadBinder extends Binder {
        public void startDownload(String path, final String name, String url, final int notifyId) {
            DUtil.init(mContext)
                    .path(path)
                    .name(name)
                    .url(url)
                    .childTaskCount(3)
                    .build()
                    .start(new SimpleDownloadCallback() {
                        @Override
                        public void onStart(long currentSize, long totalSize, float progress) {
                            System.out.println("start");
                            NotificationUtil.createProgressNotification(mContext, name, "没有附件需要下载", R.mipmap.ic_launcher, notifyId);
                        }

                        @Override
                        public void onProgress(long currentSize, long totalSize, float progress) {
                           /* if(totalSize==-1){
                                //System.out.println(totalSize);
                                System.out.println(filesize);
                                double progress1 =currentSize*1.0/ filesize*100.f;
                                //System.out.println(progress1+"");
                                DecimalFormat df = new DecimalFormat("0.00");
                                df.setRoundingMode(RoundingMode.HALF_UP);
                                progress = Float.parseFloat((df.format(progress1)));
                                System.out.println(progress1+"");
                            }*/
                            System.out.println(currentSize+"==  "+totalSize+"=="+progress);
                            NotificationUtil.updateNotification(notifyId, progress);
                        }

                        @Override
                        public void onFinish(File file) {
                            NotificationUtil.cancelNotification(notifyId);
                            Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                            System.out.println(file.getPath());
                            System.out.println(file.getAbsolutePath());
                            try {
                                System.out.println(file.getCanonicalPath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //customDialog(file.getAbsolutePath());
                            openfile(file.getPath());
                        }

                        @Override
                        public void onCancel() {
                            System.out.println("cancel");
                            super.onCancel();
                        }

                        @Override
                        public void onWait() {
                            System.out.println("wait");
                        }

                        @Override
                        public void onPause() {
                            System.out.println("pause");
                            super.onPause();
                        }

                        @Override
                        public void onError(String error) {
                            System.out.println(error);
                            super.onError(error);
                        }
                    });
        }

        public void pauseDownload(String url) {
            DownloadManger.getInstance(mContext).pause(url);
        }

        public void resumeDownload(String url) {
            DownloadManger.getInstance(mContext).resume(url);
        }

        public void cancelDownload(String url) {
            DownloadManger.getInstance(mContext).cancel(url);
        }

        public void restartDownload(String url) {
            DownloadManger.getInstance(mContext).restart(url);
        }

        public float getProgress(String url) {
            if (DownloadManger.getInstance(mContext).getCurrentData(url) != null) {
                return DownloadManger.getInstance(mContext).getCurrentData(url).getPercentage();
            }
            return -1;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 自定义对话框
     */
    private void customDialog(final String saveurl) {
        final Dialog dialog = new Dialog( getBaseContext(),R.style.Theme_AppCompat_Light_Dialog);
        View view = View.inflate(mContext, R.layout.dialog_normal, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        TextView tcontent = (TextView)view.findViewById(R.id.tcontent);
        tcontent.setText("附件已存入\""+saveurl+"\"中，确定是否现在打开？");
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(true);
        //设置对话框的大小
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(mContext).getScreenHeight() * 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(mContext).getScreenWidth() * 0.75f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfile(saveurl);
            }
        });
       dialog.show();
    }
    private  void openfile(String path){
        try {
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            //String path = "/mnt/test/TB_DRAWING.xls";
            File file = new File(path);
            String type = fileNameMap.getContentTypeFor(path);
            //                  解决部分三星手机无法获取到类型的问题
            if (TextUtils.isEmpty(type)) {
                String extension = MimeTypeMap.getFileExtensionFromUrl(path);
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            if (TextUtils.isEmpty(type)) {
                Toast.makeText(mContext, "无法正确读取文件类型，请尝试在文件夹中直接打开！", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, type);
            mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "手机上未安装能打开该文件的程序！", Toast.LENGTH_SHORT).show();
        }
    }

}
