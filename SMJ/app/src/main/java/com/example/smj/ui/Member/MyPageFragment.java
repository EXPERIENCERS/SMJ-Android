package com.example.smj.ui.Member;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.smj.Manager.JWTManager;
import com.example.smj.R;
import com.example.smj.data.entity.Member.MemberData;
import com.example.smj.domain.usecase.MyPageMemberUseCase;
import com.example.smj.ui.Setting.AppSetting;
import com.example.smj.ui.manage.PostManageActivity;
import com.example.smj.ui.Message.MessageManagementActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPageFragment extends Fragment {

    Button letterBtn;
    Button settingAppBtn;
    Button memberOutBtn;
    Button logoutBtn;
    Button myPostBtn;
    String myEmail;
    private String key;
    private TextView userName;
    private CircleImageView profileImage;
    private List<MemberData> memberData = new ArrayList<>();
    private MyPageMemberUseCase myPageMemberUseCase = new MyPageMemberUseCase(this);

    public MyPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.activity_mypage, container, false);
        letterBtn = view.findViewById(R.id.myLetter);
        userName = view.findViewById(R.id.userName);
        profileImage = view.findViewById(R.id.profileImage);
        key = JWTManager.getSharedPreference(getActivity(),getString(R.string.saved_JWT));

        Bundle bundle = getArguments();
        if (bundle != null) {
            myEmail = bundle.getString("myEmail");
        }
        myPageMemberUseCase.getData(key);

        letterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MessageManagementActivity.class);//쪽지리스트액티비티로 연결
                startActivityForResult(intent, 1);
            }
        });

        settingAppBtn = view.findViewById(R.id.settingApp);
        settingAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AppSetting.class); //앱관리액티비티로 연결
                startActivityForResult(intent, 2);
            }
        });

        memberOutBtn = view.findViewById(R.id.memberOut);
        memberOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원탈퇴 관련
            }
        });

        logoutBtn = view.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃 관련
            }
        });

        myPostBtn = view.findViewById(R.id.myPost);
        myPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //내 게시글 관련
                Intent intent = new Intent(view.getContext(), PostManageActivity.class); //게시글관리액티비티로 연결
                startActivityForResult(intent, 3);
            }
        });

        return view;
    }

    public void onDataSuccess(List<MemberData> list) {
        memberData.clear();
        memberData = list;

        for (MemberData data : memberData) {
            if (myEmail.equals(data.getEmail())) {
                if(data.getImage() != null){
                    Uri uri = Uri.parse(data.getImage());
                    profileImage.setImageURI(uri);
                }
                userName.setText(data.getNickName());
            }
        }
    }
    //메세지 서버 수정 후 쪽지 알림 수정
}
