package com.numberONE.maryfarm.KakaoLogin;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;
import com.numberONE.maryfarm.BuildConfig;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
<<<<<<< HEAD

        KakaoSdk.init(this, "1fc6b60080fc2f00d7f43085d079e13e");
=======
        KakaoSdk.init(this,BuildConfig.kakao_key);
//  BuildConfig - java(generated)폴더 안에 있는 com.numberONE.maryfarm의 BuildConfig 클래스와 연결 
>>>>>>> 7ef21055c76a810e425e75eef84e3c2c9fe68ca5
    }
}
