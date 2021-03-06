package com.example.smj.ui.main;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.callback.JWTGetLocal;
import com.example.smj.domain.usecase.JWTUseCase;
import com.example.smj.ui.Convenience.ConvenienceFragment;
import com.example.smj.ui.Boards.LivingTip.LivingTipFragment;
import com.example.smj.ui.Member.MyPageFragment;
import com.example.smj.ui.Alarms.ScheduleFragment;
import com.example.smj.ui.Boards.Transaction.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ActivityCompat.OnRequestPermissionsResultCallback, JWTGetLocal {
    private String at;
    private String jwt;
    private ViewPager2 mViewPager;
    private ViewPagerAdapter pageAdapter;
    private int num_page = 5;
    private List<Fragment>list = new ArrayList<Fragment>();
    private ConvenienceFragment convenienceFragment;
    private LivingTipFragment livingTipFragment;
    private MyPageFragment myPageFragment;
    private ScheduleFragment scheduleFragment;
    private TransactionFragment transactionFragment;
    private JWTUseCase jwtUseCase;
    private BottomNavigationView bottomNavigationView;
    private String myEmail;

    private String[] permission_list = {
            Manifest.permission.WRITE_CONTACTS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        init_Fragment();
        init_List();
        jwtUseCase = new JWTUseCase(this);
        /*다른 액티비티 갔다가 다시 메인으로 돌아올때 오류*/
        getJWT();
        mViewPager = findViewById(R.id.pager);
        pageAdapter = new ViewPagerAdapter(this,num_page);
        pageAdapter.addFragments(list);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setCurrentItem(2);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setUserInputEnabled(false);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.tab3);
        Intent intent = getIntent();
        myEmail = intent.getExtras().getString("myEmail");

        Bundle bundle = new Bundle();
        bundle.putString("myEmail",myEmail);
        myPageFragment.setArguments(bundle);
        Log.d("myEmail2",myEmail);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.tab2:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.tab3:
                        mViewPager.setCurrentItem(2);
                        return true;
                    case R.id.tab4:
                        mViewPager.setCurrentItem(3);
                        return true;
                    case R.id.tab5:
                        mViewPager.setCurrentItem(4);
                        return true;
                    default: return false;
                }
            }
        });


    }

    public void checkPermission(){
        //6.0미만시 권한체크가 필요없음 -> 메소드 종료
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        for(String permission : permission_list){
            int check = checkCallingOrSelfPermission(permission);
            if(check == PackageManager.PERMISSION_DENIED){
                //권한요청 메시지를 나오게함.
                requestPermissions(permission_list,0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
    }

    private void init_Fragment() {
        convenienceFragment = new ConvenienceFragment();
        livingTipFragment = new LivingTipFragment();
        myPageFragment = new MyPageFragment();
        scheduleFragment = new ScheduleFragment();
        transactionFragment = new TransactionFragment();
    }

    private void init_List(){
        list.add(convenienceFragment);
        list.add(transactionFragment);
        list.add(livingTipFragment);
        list.add(scheduleFragment);
        list.add(myPageFragment);
    }
    public void getJWT(){
        Intent intent = getIntent(); /*데이터 수신*/
        at = intent.getExtras().getString("accessToken"); /*String형*/
        jwtUseCase.sendAT(at);
    }

    public void clickSuccess(String jwt){
        this.jwt = jwt;
        JWTManager.putSharedPreference(this,getString(R.string.saved_JWT),jwt);
    }
}