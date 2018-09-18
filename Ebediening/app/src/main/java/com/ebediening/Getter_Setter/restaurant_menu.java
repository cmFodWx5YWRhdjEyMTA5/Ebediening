package com.ebediening.Getter_Setter;

/**
 * Created by HP on 26-03-2018.
 */

public class restaurant_menu {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFood_category_id() {
        return food_category_id;
    }

    public void setFood_category_id(String food_category_id) {
        this.food_category_id = food_category_id;
    }

    public String getDine_out_price() {
        return dine_out_price;
    }

    public void setDine_out_price(String dine_out_price) {
        this.dine_out_price = dine_out_price;
    }

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }

    public String getTakeaway_price() {
        return takeaway_price;
    }

    public void setTakeaway_price(String takeaway_price) {
        this.takeaway_price = takeaway_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItem_label() {
        return item_label;
    }

    public void setItem_label(String item_label) {
        this.item_label = item_label;
    }

    String id;
    String name;
    String food_category_id;
    String dine_out_price;
    String delivery_price;
    String takeaway_price;
    String image;
    String description;
    String item_label;

    public String getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(String cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    String cart_quantity;
    public String getAttributes_counter() {
        return attributes_counter;
    }

    public void setAttributes_counter(String attributes_counter) {
        this.attributes_counter = attributes_counter;
    }

    String attributes_counter;

    public String getDineout_offer() {
        return dineout_offer;
    }

    public void setDineout_offer(String dineout_offer) {
        this.dineout_offer = dineout_offer;
    }

    String dineout_offer;
}
