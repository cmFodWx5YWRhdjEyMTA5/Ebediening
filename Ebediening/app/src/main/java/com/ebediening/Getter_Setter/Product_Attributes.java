package com.ebediening.Getter_Setter;

/**
 * Created by dikhong on 07-05-2018.
 */

public class Product_Attributes {
    public String getAttribute_type() {
        return attribute_type;
    }

    public void setAttribute_type(String attribute_type) {
        this.attribute_type = attribute_type;
    }

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getAttribute_name() {
        return attribute_name;
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
    }

    public String getTotal_values_counter() {
        return total_values_counter;
    }

    public void setTotal_values_counter(String total_values_counter) {
        this.total_values_counter = total_values_counter;
    }

    public String getAttribute_value_id() {
        return attribute_value_id;
    }

    public void setAttribute_value_id(String attribute_value_id) {
        this.attribute_value_id = attribute_value_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getAttribute_value() {
        return attribute_value;
    }

    public void setAttribute_value(String attribute_value) {
        this.attribute_value = attribute_value;
    }

    public String getCurrency_type() {
        return currency_type;
    }

    public void setCurrency_type(String currency_type) {
        this.currency_type = currency_type;
    }

    String attribute_type;
    String attribute_id;
    String attribute_name;
    String total_values_counter;
    String attribute_value_id;
    String price;
    String restaurant_id;
    String attribute_value;
    String currency_type;

    public String getAll_attribute_type() {
        return all_attribute_type;
    }

    public void setAll_attribute_type(String all_attribute_type) {
        this.all_attribute_type = all_attribute_type;
    }

    String all_attribute_type;
}
