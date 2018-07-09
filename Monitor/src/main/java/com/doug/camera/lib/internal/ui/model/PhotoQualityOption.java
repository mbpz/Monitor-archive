package com.doug.camera.lib.internal.ui.model;

import com.doug.camera.lib.configuration.Configuration;
import com.doug.camera.lib.internal.utils.Size;

/*
 *
 */

public class PhotoQualityOption implements CharSequence {

    @Configuration.MediaQuality
    private int mediaQuality;
    private String title;
    private Size size;

    public PhotoQualityOption(@Configuration.MediaQuality int mediaQuality, Size size) {
        this.mediaQuality = mediaQuality;
        this.size = size;

        title = String.valueOf(size.getWidth()) + " x " + String.valueOf(size.getHeight());
    }

    public Size getSize() {
        return size;
    }

    @Configuration.MediaQuality
    public int getMediaQuality() {
        return mediaQuality;
    }

    @Override
    public int length() {
        return title.length();
    }

    @Override
    public char charAt(int index) {
        return title.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return title.subSequence(start, end);
    }

    @Override
    public String toString() {
        return title;
    }
}
