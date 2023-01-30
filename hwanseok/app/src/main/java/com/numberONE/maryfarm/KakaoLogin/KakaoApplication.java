package com.numberONE.maryfarm.KakaoLogin;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "f059c3fbe021d13fa0a5ffa33e906bf8");
    }
}
