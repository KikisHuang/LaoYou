package laoyou.com.laoyou.activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import java.io.IOException;
import java.io.InputStream;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import laoyou.com.laoyou.R;

/**
 * Created by lian on 2017/11/4.
 */
public class TestActivity extends InitActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";
    private GLSurfaceView img;
    private FrameLayout frame_main;
    private GPUImage gpuImage;
    private SeekBar seekbar;
    private Bitmap bitmap = null;
    private GPUImageSaturationFilter gpustatura;
    private Bitmap origiBitmap = null;

    @Override
    protected void click() {
        img.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);

        img = f(R.id.img);
        frame_main = f(R.id.frame_main);
        seekbar = f(R.id.seekbar);
     /*   //初始化图片
        img.setImageBitmap(getGPUImageFromAssets(0));*/
        // 使用GPUImage处理图像
  /*      gpuImage = new GPUImage(this);
        gpuImage.setImage(bitmap);
        gpuImage.setFilter(new GPUImageGrayscaleFilter());
        bitmap = gpuImage.getBitmapWithFilterApplied();
        //显示处理后的图片
        img.setImageBitmap(bitmap);*/

        seekbar.setMax(20);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                frame_main.post(new Runnable() {
                    @Override
                    public void run() {
                        //通过进度条的值更改饱和度
                        gpuImage.setImage(getGPUImageFromAssets(progress));
                    }
                });

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    //根据传进来的数值设置素材饱和度
    public Bitmap getGPUImageFromAssets(int progress) {
        long s = System.currentTimeMillis();
        if (origiBitmap == null) {
            //获得Assets资源文件
            AssetManager as = getAssets();
            InputStream is = null;

            try {
                //注意名字要与图片名字一致
                is = as.open("gpu_test.jpg");
                origiBitmap = BitmapFactory.decodeStream(is);
                is.close();

            } catch (IOException e) {
                Log.e("GPUImage", "Error");
            }
        }
        // 使用GPUImage处理图像
        if (gpuImage == null)
            gpuImage = new GPUImage(this);

        gpuImage.setImage(origiBitmap);
        gpuImage.setGLSurfaceView(img);

        if (gpustatura == null)
            gpustatura = new GPUImageSaturationFilter();

        gpustatura.setSaturation(progress);

        gpuImage.setFilter(gpustatura);
        if (bitmap != null) {
//            img.setImageBitmap(null);
            bitmap.recycle();
            bitmap = null;
        }
        bitmap = gpuImage.getBitmapWithFilterApplied();
        long e = System.currentTimeMillis();
        Log.i("start GPU Time == ", String.valueOf(s));
        Log.i("end GPU Time == ", String.valueOf(e));

        long ms = e - s;
        ms = ms / 1000;

        Log.i("Ms GPU Time == ", String.valueOf(ms) + "ms");

        return bitmap;
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img:

                break;
        }
    }

}
