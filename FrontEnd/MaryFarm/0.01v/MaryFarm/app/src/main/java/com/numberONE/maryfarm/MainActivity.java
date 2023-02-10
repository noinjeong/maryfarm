package com.numberONE.maryfarm;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.kakao.sdk.user.UserApiClient;
import com.numberONE.maryfarm.databinding.ActivityMainBinding;
import com.numberONE.maryfarm.ui.alarm.AlarmFragment;
import com.numberONE.maryfarm.ui.board.BoardMainFragment;
import com.numberONE.maryfarm.ui.chat.ChatFragment;
import com.numberONE.maryfarm.ui.diary.WriteFragment;
import com.numberONE.maryfarm.ui.home.HomeFragment;
import com.numberONE.maryfarm.ui.myfarm.MyfarmFragment;
import com.numberONE.maryfarm.ui.search.SearchMainFragment;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    private ActivityMainBinding binding;

    //바텀 네비 화면 전환용
    Fragment fragment_home,fragment_chat,fragment_write,fragment_myfarm_profile,fragment_alarm;

    //뒤로가기 버튼 눌렀던 시간 저장용
    private long backKeyPressedTime=0;

    //Drawer 조절용 토글 버튼 객체 생성
    ActionBarDrawerToggle barDrawerToggle;
    SearchView searchView;


    public MainActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //상단 메뉴 배경색 지정
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF));

        fragment_home =new HomeFragment();
        fragment_chat =new ChatFragment();
        fragment_write =new WriteFragment();
        fragment_myfarm_profile =new MyfarmFragment();
        fragment_alarm =new AlarmFragment();

            binding.navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.menu_bottom_home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,fragment_home).commitAllowingStateLoss();
                            return true;
                        case R.id.menu_bottom_chat:
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,fragment_chat).commitAllowingStateLoss();
                            return true;
                        case R.id.menu_bottom_write:
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,fragment_write).commitAllowingStateLoss();
                            return true;
                        case  R.id.menu_bottom_alarm:
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,fragment_alarm).commitAllowingStateLoss();
                            return true;
                        case R.id.menu_bottom_farm:
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,fragment_myfarm_profile).commitAllowingStateLoss();
                            return true;
                    }
                    return true;
                }
            });


        // 상단 로고
        getSupportActionBar().setIcon(R.drawable.logo_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // 상단 로고 위치 지정이 안 됌
//        getSupportActionBar().setIcon(R.drawable.logo_icon);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);   //홈 아이콘을 숨김처리합니다.
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
//        getSupportActionBar().setDisplayShowTitleEnabled(false); //액션바에 표시되는 제목의 표시유무를 설정합니다.

        //item icon 색조를 적용하지 않도록 , 이 설정 없을경우 item icon 전부 회색
        binding.drawerNav.setItemIconTintList(null);

//        drawerlayout
        binding.drawerNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.hamburger_1:
                        Toast.makeText(MainActivity.this,"이장님 말씀", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.hamburger_2:
                        Toast.makeText(MainActivity.this,"텃밭학교 ", Toast.LENGTH_SHORT).show();
//                        FragmentManager fragmentManager=getSupportFragmentManager();
//                        fragmentManager.beginTransaction().add(R.id.main_activity,searchFragment).commit();
                        break;
                    case R.id.hamburger_3:
                        Toast.makeText(MainActivity.this,"마을회관", Toast.LENGTH_SHORT).show();
                        BoardMainFragment boardFragment=new BoardMainFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,boardFragment).commitAllowingStateLoss();
                        break;
                    case R.id.hamburger_4:
                        Toast.makeText(MainActivity.this,"직거래 장터", Toast.LENGTH_SHORT).show();
                        break;
                        // 로그아웃 구현 필요
                    case R.id.logout:
                        Toast.makeText(MainActivity.this,"정상적으로 로그아웃되었습니다.",Toast.LENGTH_SHORT).show();
                        UserApiClient.getInstance();

                        break;

                }

                //클릭시 drawer 닫기
                binding.drawerLayout.closeDrawer(binding.drawerNav);

                return false;
            }
        });

//        barDrawerToggle=new ActionBarDrawerToggle(this,binding.drawerLayout, R.string.app_name, R.string.app_name);
        //ActionBar( 제목줄)의 홈 or 업버튼의 위치에 토글아이콘 보이게
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        //햄버거 모양으로 보이도록 토글버튼 동기 맞추기
//        barDrawerToggle.syncState();
//
//        //햄버거 아이콘과 화살표 아이콘이 자동으로 변환환하도록
//        binding.drawerLayout.addDrawerListener(barDrawerToggle);

//        drawerlayout 끝


// 키보드 외 화면 클릭시 키보드 내려가기
        binding.mainActivity.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                searchView.setQuery("",false); // 검색 후 검색창 공백 상태로
                searchView.setIconified(true); // 검색 후 icon 상태로 돌아오기
                hideKeyboard();
                return false;
            }
        });

    } // onCreate 끝


    //검색창 활성화
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("검색어를 입력하세요");
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    // 검색 버튼 로직 만들기
    SearchView.OnQueryTextListener queryTextListener =new SearchView.OnQueryTextListener() {
        @Override // 최종 검색을 위해 제출버튼 눌렀을 때
        public boolean onQueryTextSubmit(String query) {
            SearchMainFragment searchFragment=new SearchMainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,searchFragment).commitAllowingStateLoss();
            searchView.setQuery("",false); // 검색 후 검색창 공백 상태로
            searchView.setIconified(true); // 검색 후 icon 상태로 돌아오기
            Toast t = Toast.makeText(MainActivity.this,query,Toast.LENGTH_SHORT);
            t.show();
            return false;
        }

        @Override // 유저가 한글자 한글자 입력할 때
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };


    // 뒤로 가기 버튼 2번 클릭시 앱종료 로직
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()>backKeyPressedTime+2000){
            backKeyPressedTime=System.currentTimeMillis();
            Toast.makeText(this,"뒤로버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        //2초 이내에 뒤로가기 버튼 한번 더 클릭시 앱 종료
        if(System.currentTimeMillis() <= backKeyPressedTime+2000){
            finish();
        }
    }
    // 키보드 숨기기 로직
    void hideKeyboard()
    {
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}