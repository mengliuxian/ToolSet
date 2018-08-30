
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.flym.name.acase.net.DisposableWrapper;
import com.flym.name.acase.net.GsonUtil;
import com.flym.name.acase.net.LoadingDialog;
import com.flym.name.acase.net.RetrofitUtil;
import com.flym.name.acase.net.SimpleTransFormer;
import com.flym.name.acase.picture.PictureDialog;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author mainActivity
 */
public class MainActivity extends AppCompatActivity {
    private String canBack = "";
    private TextView tv;
    private String isOk = "1";
    private String path = "";
    protected CompositeDisposable compositeDisposable;
    private LoadingDialog loadingDialog;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE); //禁用截屏
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);
        compositeDisposable = new CompositeDisposable();
        loadUrl();
//        loadContent("");
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:apps()");
            }
        });

    }

    //    TextView toolbarTitle;

//    LinearLayout rootView;
//    Toolbar toolbar;

//    String mUrl = "http://mall2.gmcciot.com:8010/ext/";
//    String mUrl = "http://php.ssssapp.com/ext/";
    String mUrl = "http://221.179.101.33:8010/ext";
    public static final int INPUT_FILE_REQUEST_CODE = 3;
    //    RelativeLayout errorView;
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 4;
    private String mCameraPhotoPath;
    private ValueCallback<Uri[]> mFilePathCallback;


    int type;
    boolean isOnPause;

//    private void getHtmlContent() {
//        compositeDisposable.add(
//                RetrofitUtil.getService().getHtmlContentByType(type)
//                        .compose(new SimpleTransFormer<SimpleString>(SimpleString.class))
//                        .subscribeWith(new DisposableWrapper<SimpleString>(LoadingDialog.show(WebViewActivity.this)) {
//                            @Override
//                            public void onNext(SimpleString simpleString) {
//                                loadContent(simpleString.content);
//                            }
//                        }));
//    }

    private void loadContent(String content) {
        initWebView();
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width,initial-scale=1,user-scalable=0\">\n" +
                "    <title>jms-admin</title>\n" +
                "<script type=\"text/javascript\">" +
                "function apps(){" +
                "document.getElementById(\"content\").innerHTML +=" +
                "\"Android\";}" +
                "</script>" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"app\">" +
                "</div>\n" +
                "    <div id=\"content\">内容显示" +
                "</div>\n" +
//                "<from><input type='file'/></from>\n" +
                "  </body>\n" +
                "</html>";

        webView.loadData(html, "text/html", "utf-8");
    }

    private void loadUrl() {
        initWebView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(mUrl);
            }
        }, 500);
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        //防止安全漏洞
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        String userAgentString = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(userAgentString);
        webView.setWebChromeClient(new WebChromeClient() {
//        window.

            //4.1的文件上传
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                MainActivity.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), MainActivity.FILECHOOSER_RESULTCODE);
            }

            //5.0的文件上传
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (Exception ex) {
                        // Error occurred while creating the File
                        Log.e("WebViewSetting", "Unable to create Image File", ex);
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
//                PermissionUtil.checkCameraPermission(WebViewActivity.this);
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                errorView.setVisibility(View.VISIBLE);
                Log.e("lsq  -->error", "onReceivedError: ");
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                errorView.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                if (webView != null && !TextUtils.isEmpty(webView.getTitle()) && type == 0) {
////                    toolbarTitle.setText(webView.getTitle());
//                }
                super.onPageFinished(view, url);
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, String userAgent,
                                        String contentDisposition,
                                        String mimetype, long contentLength) {

                Log.e("lsq  -->url", url);
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                Log.e("lsq  -->uri", uri.toString());
                MainActivity.this.startActivity(intent);

//                DownApkTask downApkTask = DownApkTask.getInstance(
//                        MainActivity.this, url, new Upload() {
//                            @Override
//                            public void setF(int i) {
//
//                            }
//
//                            @Override
//                            public void succ(final File file) {
////                        Log.e("lsq  -->", "下载完成");
//                            }
//                        });
//                downApkTask.execute("");
            }
        });
    }


    //在sdcard卡创建缩略图
    //createImageFileInSdcard
    @SuppressLint("SdCardPath")
    private File createImageFile() {
        //mCameraPhotoPath="/mnt/sdcard/tmp.png";
        File file = new File(Environment.getExternalStorageDirectory() +
                "/", getPackageName() + System.currentTimeMillis() + ".png");
        mCameraPhotoPath = file.getAbsolutePath();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private byte[] bytes;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            if (result != null) {
//                String imagePath = ImageFilePath.getPath(this, result);
//                if (!TextUtils.isEmpty(imagePath)) {
//                    result = Uri.parse("file:///" + imagePath);
//                }
            }
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == INPUT_FILE_REQUEST_CODE && mFilePathCallback != null) {
            // 5.0的回调
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        if (webView.getSettings().getUserAgentString().contains("MQQBrowser")) {
                            results = new Uri[]{Uri.parse(mCameraPhotoPath.substring("file:".length()))};
                        } else {
                            results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                        }
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
            super.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == 101) {
            if (resultCode == -1) {
                if (data != null) {
                    List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
                    File f = new File(photos.get(0));
                    bytes = PictureDialog.WriteFromStream(MainActivity.this, f);
                    Log.d("bytes:  ", bytes.length + "");
                    Log.e("lsq  -->", f.getName());
                    upLoad(f);
                }
            }
        } else if (requestCode == 103) {
            if (resultCode == -1) {
                File f = new File(path);
                bytes = PictureDialog.WriteFromStream(MainActivity.this, f);
                Log.d("bytes:  ", bytes.length + "");
                //文件大于2M就去压缩
                if (bytes.length > (1000 * 1024 * 2)) {
                    f = PictureDialog.compressWithRx(MainActivity.this, f);
                }
                Log.e("lsq  -->", f.getAbsolutePath());
                upLoad(f);
            }
        }
    }


//    @OnClick({R.id.reload_btn, R.id.close_btn})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.reload_btn:
//                webView.reload();
//                break;
//            case R.id.close_btn:
//                finish();
//                break;
//        }
//    }

    public class JavaScriptInterface {
        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void shareGoods(final String goodsId, final String goodsTitle, final String imgUrl, final String backGod) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            GoodsUtil.shareGoods(context, Long.valueOf(goodsId), goodsTitle, backGod, imgUrl);
                        }
                    }, 200);
//                    GoodsUtil.shareGoods(context, Long.valueOf(goodsId));
                }
            });
        }

        @JavascriptInterface
        public void setPageTitle(final String title) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    toolbarTitle.setText(title);
                }
            });
        }

        @JavascriptInterface
        public void buyGoods(String goodsId, int quantity) {
//            GoodsUtil.buyGoods(context, goodsId, quantity);
        }

        @JavascriptInterface
        public void toCart() {
//            ActivityManager.getInstance().startNextActivity(ShoppingCartActivity.class);
        }

        @JavascriptInterface
        public void addCart(String goodsId, int quantity) {
//            GoodsUtil.addGoodsToShopCart(context, Long.parseLong(goodsId), quantity);
        }

        @JavascriptInterface
        public String getToken() {
//            return UserManager.getUserToken(WebViewActivity.this);
            return null;
        }

        @JavascriptInterface
        public void toBackAction() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onBackPressed();
                }
            });

        }

        @JavascriptInterface
        public void hideToolBar(final boolean b) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    toolbar.setVisibility(b ? View.GONE : View.VISIBLE);
                }
            });
        }

        @JavascriptInterface
        public void show(String b) {
            canBack = b;
            Log.e("lsq  -->", b);
            webView.post(new Runnable() {
                @Override
                public void run() {
                    goToBack();
                }
            });
        }

        @JavascriptInterface
        public void uploadPick() {
//            webView.post(new Runnable() {
//                @Override
//                public void run() {
//            webView.loadUrl("javascript:apps()");
//                }
//            });
//            webView.loadUrl("javascript:test()");
            webView.post(new Runnable() {
                @Override
                public void run() {
                    isOk = "1";
                    path = PictureDialog.getPath(MainActivity.this);//MainActivity.this
                    PictureDialog.selectPicture(MainActivity.this, path);
                }
            });

        }

        @JavascriptInterface
        public String checkOK() {
            return isOk;
        }

        @JavascriptInterface
        public void upload(String url) {
            Log.e("lsq  -->url", url);
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            Log.e("lsq  -->uri", uri.toString());
            MainActivity.this.startActivity(intent);
        }
    }

    public boolean goBack() {
        if (webView.canGoBack()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webView.goBack();
            return true;
        }
        return false;
    }

    /**
     * 监听回退键
     */
    private long exitTime;

    @Override
    public void onBackPressed() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:window.Android.show(document.getElementById('isTrue').value);");
            }
        });

    }

    public void goToBack() {
        if (canBack.equals("true")) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } else {
            if (type != 0 || !goBack()) {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (type != 0 || !goBack()) {
            return super.onSupportNavigateUp();
        } else {
            return false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            try {
                webView.getClass().getMethod("onPause").invoke(webView, (Object[]) null);
                isOnPause = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDestroy() {
//        rootView.removeAllViews();
        webView.removeAllViews();
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (isOnPause) {
                if (webView != null) {
                    webView.getClass().getMethod("onResume").invoke(webView, (Object[]) null);
                }
                isOnPause = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void upLoad(final File file) {
        loadingDialog = LoadingDialog.show(this, "上传图片中");
        RequestBody responseBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), responseBody);
        builder.addPart(part);

        compositeDisposable.add(RetrofitUtil.getApiService().uploadFile(builder.build())
                .compose(new SimpleTransFormer<UploadBean>(UploadBean.class))
                .subscribeWith(new DisposableWrapper<UploadBean>(loadingDialog) {
                    @Override
                    public void onNext(UploadBean uploadBean) {
                        Log.e("lsq  -->", uploadBean.getResp());
                        //"resp":"{\"mediaId\":\"27296ddc-b90d-4753-a140-30cfa6ada6b7\"
                        String s = uploadBean.getResp();
//                        isOk = s.substring(s.indexOf(":") + 2, s.length() - 2);
                        isOk = GsonUtil.toJson(uploadBean);
                        Log.e("lsq  -->", isOk);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Toast.makeText(MainActivity.this, "上传错误", Toast.LENGTH_SHORT).show();
                        Log.e("lsq  -->", "上传错误");
                    }
                }));
    }


}
