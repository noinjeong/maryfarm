package com.numberONE.maryfarm.Recommendation;

public class RecommendationActivity {

    public class MainActivity extends AppCompatActivity {

        Call<data_model> call;
        TextView textView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            textView =findViewById(R.id.txt_view);

            call = retrofit_client.getApiService().test_api_get("5");
            call.enqueue(new Callback<data_model>(){
                //콜백 받는 부분
                @Override
                public void onResponse(Call<data_model> call, Response<data_model> response) {
                    data_model result = response.body();
                    String str;
                    str= result.getUserId() +"\n"+
                            result.getID()+"\n"+
                            result.getTitle()+"\n"+
                            result.getBody();
                    textView.setText(str);
                }

                @Override
                public void onFailure(Call<data_model> call, Throwable t) {

                }
            });


        }
    }

}
