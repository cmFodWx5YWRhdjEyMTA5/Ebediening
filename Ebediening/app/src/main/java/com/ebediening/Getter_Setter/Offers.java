package com.ebediening.Getter_Setter;

/**
 * Created by HP on 24-03-2018.
 */

public class Offers {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRestaurent_name() {
        return restaurent_name;
    }

    public void setRestaurent_name(String restaurent_name) {
        this.restaurent_name = restaurent_name;
    }

    public String getTotal_offers() {
        return total_offers;
    }

    public void setTotal_offers(String total_offers) {
        this.total_offers = total_offers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public String getDiscount_for() {
        return discount_for;
    }

    public void setDiscount_for(String discount_for) {
        this.discount_for = discount_for;
    }

    public String getDiscount_on() {
        return discount_on;
    }

    public void setDiscount_on(String discount_on) {
        this.discount_on = discount_on;
    }

    String id,image_url,restaurent_name,total_offers,title,discount_type,discount_for,discount_on;
}
