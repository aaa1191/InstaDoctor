package com.softgates.instadoctor.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softgates.instadoctor.R;
import com.softgates.instadoctor.util.ProgressDialog;
import com.softgates.instadoctor.demoo.initsdk.InitAuthSDKCallback;
import com.softgates.instadoctor.demoo.initsdk.InitAuthSDKHelper;
import com.softgates.instadoctor.demoo.startjoinmeeting.UserLoginCallback;
import com.softgates.instadoctor.demoo.ui.APIUserStartJoinMeetingActivity;

import java.util.List;

import us.zoom.sdk.InMeetingNotificationHandle;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingInviteMenuItem;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomUIDelegate;

public class VideoCalling  extends Activity implements InitAuthSDKCallback
        , UserLoginCallback.ZoomDemoAuthenticationListener, View.OnClickListener {

    private final static String TAG = "ZoomSDKExample";
    private Button mBtnEmailLogin;
    private Button mBtnSSOLogin;
    private Button mBtnWithoutLogin;
    private View layoutJoin;
    private View mProgressPanel;
    private EditText numberEdit;
    private EditText nameEdit;
    private ZoomSDK mZoomSDK;
    ProgressDialog progressDialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialogClass = new  ProgressDialog(VideoCalling.this);

        mZoomSDK = ZoomSDK.getInstance();
        if (mZoomSDK.isLoggedIn()) {
            finish();
            showEmailLoginUserStartJoinActivity();
            return;
        }

        setContentView(R.layout.init_auth_sdk);

        mBtnEmailLogin = (Button) findViewById(R.id.btnEmailLogin);
        mBtnEmailLogin.setOnClickListener(this);

        mBtnSSOLogin = (Button) findViewById(R.id.btnSSOLogin);
        mBtnSSOLogin.setOnClickListener(this);

        mBtnWithoutLogin = (Button) findViewById(R.id.btnWithoutLogin);
        mBtnWithoutLogin.setOnClickListener(this);
        mProgressPanel = (View) findViewById(R.id.progressPanel);

        layoutJoin = findViewById(R.id.layout_join);
        numberEdit = findViewById(R.id.edit_join_number);
        nameEdit = findViewById(R.id.edit_join_name);
        mProgressPanel.setVisibility(View.GONE);

        InitAuthSDKHelper.getInstance().initSDK(this, this);

        if (mZoomSDK.isInitialized()) {
          /*  mBtnEmailLogin.setVisibility(View.VISIBLE);
            mBtnSSOLogin.setVisibility(View.VISIBLE);
            mBtnWithoutLogin.setVisibility(View.VISIBLE);
            layoutJoin.setVisibility(View.VISIBLE);

            View view = findViewById(R.id.btnSettings);
            if (null != view) {
                view.setVisibility(View.VISIBLE);
            }*/
          //  showAPIUserActivity();
         //mk   ZoomSDK.getInstance().getMeetingService().addListener(this);
         //mk   ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(true);
        } else {
          /*  mBtnEmailLogin.setVisibility(View.GONE);
            mBtnSSOLogin.setVisibility(View.GONE);
            mBtnWithoutLogin.setVisibility(View.GONE);
            layoutJoin.setVisibility(View.GONE);*/
        }
    }

    InMeetingNotificationHandle handle = new InMeetingNotificationHandle() {

        @Override
        public boolean handleReturnToConfNotify(Context context, Intent intent) {
           /* //mk   intent = new Intent(context, MyMeetingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setAction(InMeetingNotificationHandle.ACTION_RETURN_TO_CONF);
            context.startActivity(intent);*/
            return true;
        }
    };

    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
        Log.i(TAG, "onZoomSDKInitializeResult, errorCode=" + errorCode + ", internalErrorCode=" + internalErrorCode);

        if (errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG).show();
        } else {
            ZoomSDK.getInstance().getMeetingSettingsHelper().enable720p(false);
            ZoomSDK.getInstance().getMeetingSettingsHelper().enableShowMyMeetingElapseTime(true);
      //mk      ZoomSDK.getInstance().getMeetingService().addListener(this);
            ZoomSDK.getInstance().getMeetingSettingsHelper().setCustomizedNotificationData(null, handle);
            Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();

            showAPIUserActivity();

            if (mZoomSDK.tryAutoLoginZoom() == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
                UserLoginCallback.getInstance().addListener(this);
          //   showProgressPanel(true);


            } else {
             //   showProgressPanel(false);
            }

            ZoomSDK.getInstance().getZoomUIService().setZoomUIDelegate(new ZoomUIDelegate() {
                @Override
                public boolean onClickInviteButton(Context context, List<MeetingInviteMenuItem> inviteMenuList) {

                    inviteMenuList.add(new MeetingInviteMenuItem("SDK Contacts", R.drawable.zm_invite_contacts, new MeetingInviteMenuItem.MeetingInviteAction() {
                        @Override
                        public void onItemClick(Context context, MeetingInviteMenuItem.MeetingInviteItemInfo info) {
                            Log.d(TAG, "onItemClick:" + info.getTopic() + ":" + info.getContent() + ":" + info.getMeetingUrl() + ":" + info.getMeetingId());
                        }
                    }));

                    return false;
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (!mZoomSDK.isInitialized()) {
            Toast.makeText(this, "Init SDK First", Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }
        if (v.getId() == R.id.btnEmailLogin) {
            showEmailLoginActivity();
        } else if (v.getId() == R.id.btnSSOLogin) {
            showSSOLoginActivity();
        } else if (v.getId() == R.id.btnWithoutLogin) {
            showAPIUserActivity();
        }
    }

    public void onClickSettings(View view) {
        if (!mZoomSDK.isInitialized()) {
            Toast.makeText(this, "Init SDK First", Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }
        //mk   startActivity(new Intent(this, MeetingSettingActivity.class));

    }

    @Override
    public void onZoomSDKLoginResult(long result) {
        if ((int) result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
            showEmailLoginUserStartJoinActivity();
            finish();
        } else {
         //   showProgressPanel(false);
        }
    }

    @Override
    public void onZoomSDKLogoutResult(long result) {

    }

    @Override
    public void onZoomIdentityExpired() {
        if (mZoomSDK.isLoggedIn()) {
            mZoomSDK.logoutZoom();
        }
    }

    @Override
    public void onZoomAuthIdentityExpired() {
        Log.e(TAG, "onZoomAuthIdentityExpired");
    }

    public void onClickJoin(View view) {
        if (!mZoomSDK.isInitialized()) {
            Toast.makeText(this, "Init SDK First", Toast.LENGTH_SHORT).show();
            InitAuthSDKHelper.getInstance().initSDK(this, this);
            return;
        }

        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(false);
        } else {
            ZoomSDK.getInstance().getSmsService().enableZoomAuthRealNameMeetingUIShown(true);
        }
        String number = numberEdit.getText().toString();
        String name = nameEdit.getText().toString();

        JoinMeetingParams params = new JoinMeetingParams();
        params.meetingNo = number;
        params.displayName = name;
        ZoomSDK.getInstance().getMeetingService().joinMeetingWithParams(this, params);
    }

    private void showProgressPanel(boolean show) {
        if (show) {
            mBtnEmailLogin.setVisibility(View.GONE);
            mBtnSSOLogin.setVisibility(View.GONE);
            mBtnWithoutLogin.setVisibility(View.GONE);
            mProgressPanel.setVisibility(View.VISIBLE);
            layoutJoin.setVisibility(View.GONE);
            View view = findViewById(R.id.btnSettings);
            if (null != view) {
                view.setVisibility(View.GONE);
            }
        } else {
            View view = findViewById(R.id.btnSettings);
            if (null != view) {
                view.setVisibility(View.VISIBLE);
            }
            mBtnWithoutLogin.setVisibility(View.VISIBLE);
            mBtnEmailLogin.setVisibility(View.VISIBLE);
            mBtnSSOLogin.setVisibility(View.VISIBLE);
            mProgressPanel.setVisibility(View.GONE);
            layoutJoin.setVisibility(View.VISIBLE);
        }
    }

    public void zoomApi()
    {
        Intent intent = new Intent(VideoCalling.this, APIUserStartJoinMeetingActivity.class);
  //      intent.putExtra("mylist", (ArrayList<ZoomData>) response.body().getZoomData());
        startActivity(intent);

     /*   progressDialogClass.setLoading(true);

        Log.e("ZOOMAPI","Zoom api r is called.");

        // hashMap.put("user_password",password_edittext.getText().toString());
        ApiInterface requestInterface = Util.zoomApi();
        Call<ZoomResponse> call = requestInterface.getZoom();
        call.enqueue(new Callback<ZoomResponse>() {
            @Override
            public void onResponse(Call<ZoomResponse> call, retrofit2.Response<ZoomResponse> response) {
                progressDialogClass.setLoading(false);

                Log.e("ZOOMAPI","Zoom api response is called."+response.body().getStatus());
                Log.e("ZOOMAPI","Zoom api response is called."+response.body().getmessage());
                Log.e("ZOOMAPI","Zoom api response id id id is called."+response.body().getZoomData().get(0).getId());
                Log.e("ZOOMAPI","Zoom api response passward passward is called."+response.body().getZoomData().get(0).getPassword());

                if(response.body().getStatus()==200)
                {
                    //   startData(myList);
                    // Intent intent = new Intent(ApiActivity.this, CustomMarkerClusteringDemoSampleActivity.class);
                   Intent intent = new Intent(VideoCalling.this, APIUserStartJoinMeetingActivity.class);
                    intent.putExtra("mylist", (ArrayList<ZoomData>) response.body().getZoomData());
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(VideoCalling.this,"No Data Found",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ZoomResponse> call, Throwable t) {
                Log.e("ZOOMAPI","Zoom api response error is called."+t.getMessage().toString());

                progressDialogClass.setLoading(false);
            }
        });*/
    }

    private void showEmailLoginActivity() {
        //mk      Intent intent = new Intent(this, EmailUserLoginActivity.class);
        //mk      startActivity(intent);
    }

    private void showSSOLoginActivity() {
        //mk    Intent intent = new Intent(this, SSOUserLoginActivity.class);
        //mk     startActivity(intent);
    }

    private void showAPIUserActivity() {

        zoomApi();
           //  Intent intent = new Intent(this, APIUserStartJoinMeetingActivity.class);
        //     startActivity(intent);
    }

    private void showEmailLoginUserStartJoinActivity() {
        //mk    Intent intent = new Intent(this, LoginUserStartJoinMeetingActivity.class);
        //mk      startActivity(intent);
    }


 /*   @Override
    public void onMeetingStatusChanged(MeetingStatus meetingStatus, int errorCode, int internalErrorCode) {
        Log.d(TAG, "onMeetingStatusChanged " + meetingStatus);
        if (!ZoomSDK.getInstance().isInitialized()) {
            showProgressPanel(false);
            return;
        }
        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            if (meetingStatus == MeetingStatus.MEETING_STATUS_CONNECTING) {
                showMeetingUi();
            }
        }
    }*/

    private void showMeetingUi() {
        if (ZoomSDK.getInstance().getMeetingSettingsHelper().isCustomizedMeetingUIEnabled()) {
            SharedPreferences sharedPreferences = getSharedPreferences("UI_Setting", Context.MODE_PRIVATE);
            boolean enable = sharedPreferences.getBoolean("enable_rawdata", false);
            Intent intent = null;
            if (!enable) {
                //mk       intent = new Intent(this, MyMeetingActivity.class);
                intent.putExtra("from", 3);
            } else {
                //mk         intent = new Intent(this, RawDataMeetingActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            this.startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserLoginCallback.getInstance().removeListener(this);

        if (null != ZoomSDK.getInstance().getMeetingService()) {
          //mk  ZoomSDK.getInstance().getMeetingService().removeListener(this);
        }
        ZoomSDK.getInstance().getZoomUIService().setZoomUIDelegate(null);
        InitAuthSDKHelper.getInstance().reset();
    }
}
