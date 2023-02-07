package com.numberONE.maryfarm.ui.toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.databinding.ActivityToolbarBinding;

public class ToolbarActivity extends AppCompatActivity {

    ActivityToolbarBinding binding;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(this,binding.drawerLayout, R.string.drawer_open,R.string.drawer_close);
        toggle.syncState();

        binding.navigationHeaderView.setNavigationItemSelectedListener(menuItem ->{
            int id =menuItem.getItemId();
            if(id== R.id.hamburger_1){
                showToast("햄버거 1번 버튼 ");
            }else if( id==R.id.hamburger_2){
                showToast("햄버거 2번 버튼 ");
            }else if( id == R.id.hamburger_3){

            }else{
            }
            return false;

        });

    }

    private void showToast(String msg){
        Toast t= Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

