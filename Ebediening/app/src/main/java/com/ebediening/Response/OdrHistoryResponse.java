package com.ebediening.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.List;

public class OdrHistoryResponse implements Parcelable {

    protected OdrHistoryResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imageBasePath = in.readString();
        historyDataList = in.createTypedArrayList(HistoryData.CREATOR);
    }

    public static final Creator<OdrHistoryResponse> CREATOR = new Creator<OdrHistoryResponse>() {
        @Override
        public OdrHistoryResponse createFromParcel(Parcel in) {
            return new OdrHistoryResponse(in);
        }

        @Override
        public OdrHistoryResponse[] newArray(int size) {
            return new OdrHistoryResponse[size];
        }
    };

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getImageBasePath() {
        return imageBasePath;
    }

    public void setImageBasePath(String imageBasePath) {
        this.imageBasePath = imageBasePath;
    }

    public List<HistoryData> getHistoryDataList() {
        return historyDataList;
    }

    public void setHistoryDataList(List<HistoryData> historyDataList) {
        this.historyDataList = historyDataList;
    }

    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("image_base_path")
    @Expose
    public String imageBasePath;

    @SerializedName("data")
    @Expose
    public List<HistoryData> historyDataList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (flag == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(flag);
        }
        dest.writeString(imageBasePath);
        dest.writeTypedList(historyDataList);
    }

    public static class HistoryData implements Parcelable {

        public HistoryData(Parcel in) {
            orderId = in.readString();
            invoiceNumber = in.readString();
            total = in.readString();
            orderDate = in.readString();
            orderTime = in.readString();
            deliveryType = in.readString();
            itemsCounter = in.readString();
            taxAmount = in.readString();
            discountAmount = in.readString();
            restaurantName = in.readString();
            restaurantImage = in.readString();
            currencyType = in.readString();
        }


        public HistoryData() {


        }

        public static final Creator<HistoryData> CREATOR = new Creator<HistoryData>() {
            @Override
            public HistoryData createFromParcel(Parcel in) {
                return new HistoryData(in);
            }

            @Override
            public HistoryData[] newArray(int size) {
                return new HistoryData[size];
            }
        };

        public LinkedHashMap<String, KeyList> getData() {
            return data;
        }

        public void setData(LinkedHashMap<String, KeyList> data) {
            this.data = data;
        }

        public LinkedHashMap<String, KeyList> data;

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("invoice_number")
        @Expose
        private String invoiceNumber;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("order_time")
        @Expose
        private String orderTime;
        @SerializedName("delivery_type")
        @Expose
        private String deliveryType;
        @SerializedName("items_counter")
        @Expose
        private String itemsCounter;
        @SerializedName("tax_amount")
        @Expose
        private String taxAmount;
        @SerializedName("discount_amount")
        @Expose
        private String discountAmount;
        @SerializedName("delivery_charges")
        @Expose
        private Object deliveryCharges;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("restaurant_image")
        @Expose
        private String restaurantImage;
        @SerializedName("currency_type")
        @Expose
        private String currencyType;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public String getItemsCounter() {
            return itemsCounter;
        }

        public void setItemsCounter(String itemsCounter) {
            this.itemsCounter = itemsCounter;
        }

        public String getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }

        public Object getDeliveryCharges() {
            return deliveryCharges;
        }

        public void setDeliveryCharges(Object deliveryCharges) {
            this.deliveryCharges = deliveryCharges;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getRestaurantImage() {
            return restaurantImage;
        }

        public void setRestaurantImage(String restaurantImage) {
            this.restaurantImage = restaurantImage;
        }

        public String getCurrencyType() {
            return currencyType;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(orderId);
            dest.writeString(invoiceNumber);
            dest.writeString(total);
            dest.writeString(orderDate);
            dest.writeString(orderTime);
            dest.writeString(deliveryType);
            dest.writeString(itemsCounter);
            dest.writeString(taxAmount);
            dest.writeString(discountAmount);
            dest.writeString(restaurantName);
            dest.writeString(restaurantImage);
            dest.writeString(currencyType);
        }

        public static class KeyList implements Parcelable{

            @SerializedName("item_name")
            @Expose
            public String itemName;

            protected KeyList(Parcel in) {
                itemName = in.readString();
                description = in.readString();
                total = in.readString();
                quantity = in.readString();
                attributes = in.readString();
            }

            public static final Creator<KeyList> CREATOR = new Creator<KeyList>() {
                @Override
                public KeyList createFromParcel(Parcel in) {
                    return new KeyList(in);
                }

                @Override
                public KeyList[] newArray(int size) {
                    return new KeyList[size];
                }
            };

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getAttributes() {
                return attributes;
            }

            public void setAttributes(String attributes) {
                this.attributes = attributes;
            }

            @SerializedName("description")
            @Expose
            public String description;

            @SerializedName("total")
            @Expose
            public String total;

            @SerializedName("quantity")
            @Expose
            public String quantity;

            @SerializedName("attributes")
            @Expose
            public String attributes;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(itemName);
                dest.writeString(description);
                dest.writeString(total);
                dest.writeString(quantity);
                dest.writeString(attributes);
            }
        }
    }
}
