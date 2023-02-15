package com.numberONE.maryfarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.kakao.sdk.user.UserApiClient;
import com.numberONE.maryfarm.KakaoLogin.KakaoLoginActivity;
import com.numberONE.maryfarm.databinding.ActivityMainBinding;
import com.numberONE.maryfarm.ui.alarm.AlarmFragment;
import com.numberONE.maryfarm.ui.board.BoardDetailFragment;
import com.numberONE.maryfarm.ui.board.BoardMainFragment;
import com.numberONE.maryfarm.ui.chat.ChatFragment;
import com.numberONE.maryfarm.ui.chat.ChatRoomFragment;
import com.numberONE.maryfarm.ui.diary.WriteFragment;
import com.numberONE.maryfarm.ui.home.HomeFragment;
import com.numberONE.maryfarm.ui.myfarm.MyfarmFragment;
import com.numberONE.maryfarm.ui.search.SearchMainFragment;

import de.hdodenhof.circleimageview.CircleImageView;

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

//      --- shared ->  pref 는 유저 이름 ,유저 고유번호 , 유저 프로필 사진 저장
        SharedPreferences preferences=getSharedPreferences("pref",Context.MODE_PRIVATE);
//      --- board 이동 시 , 저장된 값이 출력되는 것을 방지 ----
        SharedPreferences preferences_board=getSharedPreferences("board",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences_board.edit();
        editor.clear();
        editor.commit();
//      -------------------------------------------------

        //      ----- 햄버거 header 프로필 사진 , 유저 이름 --------
        String pref_Name= preferences.getString("pref_name","ham_pref_name_null");
        String pref_textUrl = preferences.getString("pref_img","ham_pref_img_null");

        TextView user_name =binding.drawerNav.getHeaderView(0).findViewById(R.id.hamburger_user_nickname);
        user_name.setText(pref_Name);
        CircleImageView user_profile= (CircleImageView) binding.drawerNav.getHeaderView(0).findViewById(R.id.hamburger_user_profile);

        GlideApp.with(this)
                .load(pref_textUrl)
                .skipMemoryCache(true)
                .error(R.drawable.loading_icon)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(user_profile);

        //      ----- 햄버거 header 프로필 사진 , 유저 이름 끝 -------

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);  //홈 아이콘을 숨김처리합니다.

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
                        BoardDetailFragment fragment=new BoardDetailFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,fragment).commitAllowingStateLoss();
                        break;
                    case R.id.hamburger_3:
                        Toast.makeText(MainActivity.this,"마을회관", Toast.LENGTH_SHORT).show();
                        BoardMainFragment boardFragment=new BoardMainFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity,boardFragment).commitAllowingStateLoss();
                        break;
                    case R.id.hamburger_4:
                        Toast.makeText(MainActivity.this,"직거래 장터", Toast.LENGTH_SHORT).show();
                        break;
                    // 로그아웃 ( sharedPreferences - 시작 시 값 초기화하는 방식 할지 ,로그아웃 할 때 값 초기화하는 방식 할지 )
                    case R.id.logout:
                        Toast.makeText(MainActivity.this,"정상적으로 로그아웃되었습니다.",Toast.LENGTH_SHORT).show();
                        UserApiClient.getInstance().logout(error ->{
                            Intent intent = new Intent(getApplicationContext(), KakaoLoginActivity.class);
                            moveTaskToBack(true); // 테스크를 백그라운드로 이동
                            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
                            startActivity(intent);
                            return null;
                        });
                        break;
                }

                //클릭시 drawer 닫기
                binding.drawerLayout.closeDrawer(binding.drawerNav);

                return false;
            }
        });

//        drawerlayout 끝

//       검색창 클릭 후 다른 위치 클릭시 키보드 감추기 코드
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_activity, new ChatFragment());

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

    //    검색창 클릭 후 다른 위치 클릭시 , 키보드 내리고 , 검색창 초기화 및 검색버튼으로 활성화
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard();
        searchView.setQuery("",false); // 검색 후 검색창 공백 상태로
        searchView.setIconified(true); // 검색 후 icon 상태로 돌아오기
        return super.dispatchTouchEvent(ev);
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
            //로그인 화면으로 가게 하고 싶으면 finish()로 처리하기
            moveTaskToBack(true); // 테스크를 백그라운드로 이동
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid()); // 앱 프로세스 종료
        }
    }
    // 키보드 숨기기 로직
    private void hideKeyboard(){
        InputMethodManager inputmanager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view =getCurrentFocus();
        if(view==null){
            view =new View(this);
        }
        inputmanager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    public void onChatFragmentChange(Integer index, String roomId) {
        if(index == 1) {
            Bundle bundle = new Bundle();
            bundle.putString("roomId", roomId);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ChatRoomFragment chatRoomFragment = new ChatRoomFragment();//프래그먼트2 선언
            chatRoomFragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
            transaction.replace(R.id.main_activity, chatRoomFragment)
//                    .setReorderingAllowed(true)
//                    .addToBackStack("ChatList")
                    .commit();
        }}

}
