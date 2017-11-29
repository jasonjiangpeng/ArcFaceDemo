package facedemo.opencv.zhongke.facedemo;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;

import com.arcsoft.facedetection.AFD_FSDKEngine;
import com.arcsoft.facedetection.AFD_FSDKError;
import com.arcsoft.facedetection.AFD_FSDKFace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class FaceUtils {
     public static byte[] getNV21(int inputWidth, int inputHeight, Bitmap scaled) throws Exception {
        int[] argb = new int[inputWidth * inputHeight];
        scaled.getPixels(argb, 0, inputWidth, 0, 0, inputWidth, inputHeight);
        byte[] yuv = new byte[inputWidth * inputHeight * 3 / 2];
        encodeYUV420SP(yuv, argb, inputWidth, inputHeight);
        scaled.recycle();
        return yuv;
    }

    private static void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) throws Exception {
        final int frameSize = width * height;
        int yIndex = 0;
        int uvIndex = frameSize;
        int  R, G, B, Y, U, V;
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                //a = (argb[index] & 0xff000000) >> 24; // a is not used obviously
                R = (argb[index] & 0xff0000) >> 16;
                G = (argb[index] & 0xff00) >> 8;
                B = (argb[index] & 0xff) >> 0;
                Y = ((66 * R + 129 * G + 25 * B + 128) >> 8) + 16;
                U = ((-38 * R - 74 * G + 112 * B + 128) >> 8) + 128;
                V = ((112 * R - 94 * G - 18 * B + 128) >> 8) + 128;

                yuv420sp[yIndex++] = (byte) ((Y < 0) ? 0 : ((Y > 255) ? 255 : Y));
                if (j % 2 == 0 && index % 2 == 0) {
                    yuv420sp[uvIndex++] = (byte) ((V < 0) ? 0 : ((V > 255) ? 255 : V));
                    yuv420sp[uvIndex++] = (byte) ((U < 0) ? 0 : ((U > 255) ? 255 : U));
                }

                index++;
            }
        }
    }

    public static  void processFace(byte[] data, int width, int height) {
        AFD_FSDKEngine engine = new AFD_FSDKEngine();
        // 用来存放检测到的人脸信息列表
        List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
        //初始化人脸检测引擎，使用时请替换申请的APPID和SDKKEY
        AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(ConStants.Appid,ConStants.FD, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 16, 25);
        Log.d("com.arcsoft", "AFD_FSDK_InitialFaceEngine = " + err.getCode());
        //输入的data数据为NV21格式（如Camera里NV21格式的preview数据），其中height不能为奇数，人脸检测返回结果保存在result。
        err = engine.AFD_FSDK_StillImageFaceDetection(data, width, height, AFD_FSDKEngine.CP_PAF_NV21, result);
        Log.d("com.arcsoft", "AFD_FSDK_StillImageFaceDetection =" + err.getCode());
        Log.d("com.arcsoft", "Face=" + result.size());
        for (AFD_FSDKFace face : result) {
            Log.d("com.arcsoft", "Face:" + face.toString());
        }
        //销毁人脸检测引擎
        err = engine.AFD_FSDK_UninitialFaceEngine();
        Log.d("com.arcsoft", "AFD_FSDK_UninitialFaceEngine =" + err.getCode());
    }

    public static  void processFace(byte[] data, int width, int height,int degree) {
        AFD_FSDKEngine engine = new AFD_FSDKEngine();
        // 用来存放检测到的人脸信息列表
        List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
        //初始化人脸检测引擎，使用时请替换申请的APPID和SDKKEY
        AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(ConStants.Appid,ConStants.FD, degree, 32, 25);
    //    AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(ConStants.Appid,ConStants.FD, degree, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 25);
        Log.d("com.arcsoft", "AFD_FSDK_InitialFaceEngine = " + err.getCode());
        //输入的data数据为NV21格式（如Camera里NV21格式的preview数据），其中height不能为奇数，人脸检测返回结果保存在result。
        err = engine.AFD_FSDK_StillImageFaceDetection(data, width, height, AFD_FSDKEngine.CP_PAF_NV21, result);
        Log.d("com.arcsoft", "AFD_FSDK_StillImageFaceDetection =" + err.getCode());
        Log.d("com.arcsoft", "Face=" + result.size());
        for (AFD_FSDKFace face : result) {
            Log.d("com.arcsoft", "Face:" + face.toString());
        }
        //销毁人脸检测引擎
        err = engine.AFD_FSDK_UninitialFaceEngine();
        Log.d("com.arcsoft", "AFD_FSDK_UninitialFaceEngine =" + err.getCode());
    }
    public static  void startFace(Bitmap scaled, int width, int height) {

        try {
            byte[] nv21 = getNV21(width, height, scaled);
            processFace(nv21,width,height);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
