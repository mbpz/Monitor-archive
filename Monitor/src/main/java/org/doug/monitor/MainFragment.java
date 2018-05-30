package org.doug.monitor;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.an.deviceinfo.device.model.Device;
import com.an.deviceinfo.device.model.Memory;
import com.an.deviceinfo.permission.PermissionManager;
import com.an.deviceinfo.permission.PermissionUtils;
import com.an.deviceinfo.userapps.UserAppInfo;
import com.an.deviceinfo.userapps.UserApps;

import org.doug.monitor.util.Toaster;

import java.util.List;


public class MainFragment extends Fragment implements PermissionManager.PermissionCallback {

    private int position;
    private Activity mActivity;

    private RecyclerView recyclerView;
    private CustomListAdapter adapter;

    private PermissionManager permissionManager;
    private PermissionUtils permissionUtils;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static MainFragment newInstance(int pos) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("pos");
        }
        permissionManager = new PermissionManager(this);
        permissionUtils = new PermissionUtils(mActivity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.smoothScrollToPosition(0);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        askPermission();
    }

    private void askPermission() {
        String permission = null;
        switch (position) {
            case 0:
                permission = Manifest.permission.ACCESS_FINE_LOCATION;
                break;
            case 6:
                permission = Manifest.permission.READ_PHONE_STATE;
                break;
            case 8:
                permission = Manifest.permission.READ_CONTACTS;
                break;
        }
        if (permission != null) getPermission(permission);
        else initialize();
    }

    private void getPermission(String permission) {
        PermissionUtils permissionUtils = new PermissionUtils(mActivity);
        if (!permissionUtils.isPermissionGranted(permission)) {
            permissionManager.showPermissionDialog(permission)
                    .withCallback(this)
                    .withDenyDialogEnabled(true)
                    .withDenyDialogMsg(mActivity.getString(R.string.permission_location))
                    .build();
        } else initialize();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void initialize() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                switch (position) {
                    case 0: //call device
                        Device device = new Device(mActivity);
                        adapter = new CustomListAdapter(mActivity, device);
                        break;
                    case 1: //call memory
                        Memory memory = new Memory(mActivity);
                        adapter = new CustomListAdapter(mActivity, memory);
                        break;
//                    case 2: //call network
//                        Network network = new Network(mActivity);
//                        adapter = new CustomListAdapter(mActivity, network);
//                        break;
                    case 2: //call installed apps
                        UserAppInfo userAppInfo = new UserAppInfo(mActivity);
                        List<UserApps> userApps = userAppInfo.getInstalledApps(true);
                        adapter = new CustomListAdapter(mActivity, userApps);
                        break;
                }

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.handleResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(String[] permissions, int[] grantResults) {
        initialize();
    }

    @Override
    public void onPermissionDismissed(String permission) {

    }

    @Override
    public void onPositiveButtonClicked(DialogInterface dialog, int which) {
        /**
         * You can choose to open the
         * app settings screen
         * * */
        permissionUtils.openAppSettings();
        Toaster.showToast(mActivity, "User has opened the settings screen");
    }

    @Override
    public void onNegativeButtonClicked(DialogInterface dialog, int which) {
        /**
         * The user has denied the permission!
         * You need to handle this in your code
         * * */
        Toaster.showToast(mActivity, "User has denied the permissions");
    }

//    @Override
//    public void onResponse(Context context, final Ad ad) {
//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                adapter = new CustomListAdapter(mActivity, ad);
//                recyclerView.setAdapter(adapter);
//            }
//        });
//    }
}
