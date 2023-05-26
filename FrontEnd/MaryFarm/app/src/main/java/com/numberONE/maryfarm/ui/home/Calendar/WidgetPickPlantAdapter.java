package com.numberONE.maryfarm.ui.home.Calendar;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.numberONE.maryfarm.MyRemoteViewsService;
import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.Calendar.ItemModel;
import com.numberONE.maryfarm.Retrofit.Calendar.MemoModel;
import com.numberONE.maryfarm.Retrofit.Calendar.RetrofitFactory;
import com.numberONE.maryfarm.Retrofit.Calendar.RetrofitService;
import com.numberONE.maryfarm.Retrofit.Calendar.WidgetModel;
import com.numberONE.maryfarm.WidgetMary;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WidgetPickPlantAdapter implements RemoteViewsService.RemoteViewsFactory {
    public String userId = "2626273197";

    //context 설정하기
    public Context context = null;
    public List<ItemModel> arrayList;

    public WidgetPickPlantAdapter(Context context) {
        this.context = context;
    }

    public void setData() {
        RetrofitService networkService = RetrofitFactory.create();
        networkService.getWidget(userId)
                .enqueue(new Callback<List<ItemModel>>() {
                    @Override
                    public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response){
                        Log.i(TAG, "onResponse 오늘의 작물: "+response.body().get(0));
                        if(response.isSuccessful()){
                            Log.i(TAG, "onResponse: 캘린더 서버 연결");
                            List<ItemModel> body = response.body();
                            arrayList = body;
                        }
                    }
                    @Override
                    public void onFailure(Call<List<ItemModel>> call, Throwable t){
                        Log.e(TAG, "onFailure: 서버 연결 실패");
                        Log.e(TAG, "onFailure:", t);
                    }
                });
    }

    //이 모든게 필수 오버라이드 메소드

    //실행 최초로 호출되는 함수
    @Override
    public void onCreate() {
        setData();
    }

    //항목 추가 및 제거 등 데이터 변경이 발생했을 때 호출되는 함수
    //브로드캐스트 리시버에서 notifyAppWidgetViewDataChanged()가 호출 될 때 자동 호출
    @Override
    public void onDataSetChanged() {
        setData();
    }

    //마지막에 호출되는 함수
    @Override
    public void onDestroy() {

    }

    // 항목 개수를 반환하는 함수
    @Override
    public int getCount() {
        // arrayList nullException 오류 나서 삼항연산자로 처리
        return arrayList!=null ? arrayList.size() : 0 ;
    }

    //각 항목을 구현하기 위해 호출, 매개변수 값을 참조하여 각 항목을 구성하기위한 로직이 담긴다.
    // 항목 선택 이벤트 발생 시 인텐트에 담겨야 할 항목 데이터를 추가해주어야 하는 함수
    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews listviewWidget = new RemoteViews(context.getPackageName(), R.layout.widget_calendar_pick_plant);
        listviewWidget.setTextViewText(R.id.textView, arrayList.get(position).getPlantName());
//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtra(WidgetMary.EXTRA_ITEM, position);
//        listviewWidget.setOnClickFillInIntent(R.id.widget, fillInIntent);   //pendingIntent와 같이 쓰이는 녀석. 클릭시 설정된 extraData를 추가적으로 전달할 수 있다.
        // 항목 선택 이벤트 발생 시 인텐트에 담겨야 할 항목 데이터를 추가해주는 코드
//        Intent dataIntent = new Intent();
//        dataIntent.putExtra("item_id", arrayList.get(position)._id);
//        dataIntent.putExtra("item_data", arrayList.get(position).content);
//        listviewWidget.setOnClickFillInIntent(R.id.text1, dataIntent);
        //setOnClickFillInIntent 브로드캐스트 리시버에서 항목 선택 이벤트가 발생할 때 실행을 의뢰한 인텐트에 각 항목의 데이터를 추가해주는 함수
        //브로드캐스트 리시버의 인텐트와 Extra 데이터가 담긴 인텐트를 함치는 역할을 한다.

        return listviewWidget;
    }

    //로딩 뷰를 표현하기 위해 호출, 없으면 null
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    //항목의 타입 갯수를 판단하기 위해 호출, 모든 항목이 같은 뷰 타입이라면 1을 반환하면 된다.
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    //각 항목의 식별자 값을 얻기 위해 호출
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 같은 ID가 항상 같은 개체를 참조하면 true 반환하는 함수
    @Override
    public boolean hasStableIds() {
        return false;
    }
}