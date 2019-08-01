package com.aldimsyafi.ezsport;

public class Upload_Info {
    private String mImageUrl;
    private String mTitleinfo;
    private String mDescinfo;



    public Upload_Info()
    {
        //Construktor Kosong

    }
    public Upload_Info(String mPostTitle_info, String mPostDesc_info, String ImageUrl)
    {
        if (mPostTitle_info.trim().equals(""))
        {
            mPostTitle_info = "Title salah";
        }
        else if (mPostDesc_info.trim().equals(""))
        {
            mPostDesc_info = "desc tidak ada";
        }
        mTitleinfo = mPostTitle_info;
        mDescinfo = mPostDesc_info;
        this.mImageUrl = ImageUrl;

    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmTitleinfo() {
        return mTitleinfo;
    }

    public void setmTitleinfo(String mTitleinfo) {
        this.mTitleinfo = mTitleinfo;
    }

    public String getmDescinfo() {
        return mDescinfo;
    }

    public void setmDescinfo(String mDescinfo) {
        this.mDescinfo = mDescinfo;
    }
}
