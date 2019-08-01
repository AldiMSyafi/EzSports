package com.aldimsyafi.ezsport;

public class Upload {
    private String mImageUrl;
    private String mTitleartikel;
    private String mDescartikel;

    public Upload() {
        //empty construct needed

    }

    public Upload (String mPostTitle, String mPostDesc, String mImageUri)
    {
        this.mTitleartikel = mPostTitle;
        this.mDescartikel = mPostDesc;
        this.mImageUrl = mImageUri;

    }
    public String getTitle(){
        return mTitleartikel;

    }
    public void setTitle(String mPostTitle){
        mTitleartikel =    mPostTitle;
    }
    public String getDesc(){
        return mDescartikel;
    }
    public void setDesc (String mPostDesc){
        mDescartikel = mPostDesc;
    }
    public String getImageUrl(){

        return mImageUrl;
    }
    public void setImageurl (String mSelectImage){

        mImageUrl = mSelectImage;
    }


}


