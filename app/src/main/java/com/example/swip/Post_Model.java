package com.example.swip;

public class Post_Model {
    public  String hotelLocation;
    public String hotelName;
    public String imageUri;
    private String key;
    private String ID;


    public Post_Model(String hotelLocation, String hotelName, String imageUri, String hotelRating, String hotelListTag, String hotelPricePerHour, String key, String ID, String email, String phone, String mapUrl, String websiteUrl) {
        this.hotelLocation = hotelLocation;
        this.hotelName = hotelName;
        this.imageUri = imageUri;

        this.key = key;
        this.ID = ID;

    }

    public Post_Model() {

    }

    public Post_Model(String mhotelLocation, String mhotelName, String mhotelRating, String mhotelPricePerHour, String email, String phone, String mapUrl, String webUrl, String mhotelTagList, String sImage) {
        this.hotelLocation = mhotelLocation;
        this.hotelName = mhotelName;
        this.imageUri = sImage;


    }






    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


}
