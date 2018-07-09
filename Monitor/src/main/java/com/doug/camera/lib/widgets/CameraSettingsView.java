package com.doug.camera.lib.widgets;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;

import org.doug.monitor.R;


/*
 *
 */
public class CameraSettingsView extends AppCompatImageButton {

    public CameraSettingsView(Context context) {
        this(context, null);
    }

    public CameraSettingsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSettingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.circle_frame_background_dark);
        setImageResource(R.drawable.ic_settings_white_24dp);
        setScaleType(ScaleType.CENTER);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (Build.VERSION.SDK_INT > 10) {
            if (enabled) {
                setAlpha(1f);
            } else {
                setAlpha(0.5f);
            }
        }
    }
}
