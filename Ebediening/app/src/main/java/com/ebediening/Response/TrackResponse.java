package com.ebediening.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackResponse {
    @SerializedName("flag")
    @Expose
    public Integer flag;

    @SerializedName("message")
    @Expose
    public String message;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("data")
    @Expose
    public TrackData trackData;

    public TrackData getTrackData() {
        return trackData;
    }

    public void setTrackData(TrackData trackData) {
        this.trackData = trackData;
    }

    public class TrackData {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("is_order_placed")
        @Expose
        private String isOrderPlaced;
        @SerializedName("is_order_confirmed")
        @Expose
        private String isOrderConfirmed;
        @SerializedName("is_order_processed")
        @Expose
        private String isOrderProcessed;
        @SerializedName("is_dispatched")
        @Expose
        private String isDispatched;
        @SerializedName("is_delivered")
        @Expose
        private String isDelivered;
        @SerializedName("is_ready_to_pickup")
        @Expose
        private String isReadyToPickup;
        @SerializedName("estimated_time")
        @Expose
        private String estimatedTime;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("total_amount")
        @Expose
        private String totalAmount;
        @SerializedName("currency_type")
        @Expose
        private String currencyType;
        @SerializedName("delivery_type")
        @Expose
        private String deliveryType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsOrderPlaced() {
            return isOrderPlaced;
        }

        public void setIsOrderPlaced(String isOrderPlaced) {
            this.isOrderPlaced = isOrderPlaced;
        }

        public String getIsOrderConfirmed() {
            return isOrderConfirmed;
        }

        public void setIsOrderConfirmed(String isOrderConfirmed) {
            this.isOrderConfirmed = isOrderConfirmed;
        }

        public String getIsOrderProcessed() {
            return isOrderProcessed;
        }

        public void setIsOrderProcessed(String isOrderProcessed) {
            this.isOrderProcessed = isOrderProcessed;
        }

        public String getIsDispatched() {
            return isDispatched;
        }

        public void setIsDispatched(String isDispatched) {
            this.isDispatched = isDispatched;
        }

        public String getIsDelivered() {
            return isDelivered;
        }

        public void setIsDelivered(String isDelivered) {
            this.isDelivered = isDelivered;
        }

        public String getIsReadyToPickup() {
            return isReadyToPickup;
        }

        public void setIsReadyToPickup(String isReadyToPickup) {
            this.isReadyToPickup = isReadyToPickup;
        }

        public String getEstimatedTime() {
            return estimatedTime;
        }

        public void setEstimatedTime(String estimatedTime) {
            this.estimatedTime = estimatedTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getCurrencyType() {
            return currencyType;
        }

        public void setCurrencyType(String currencyType) {
            this.currencyType = currencyType;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }
    }


}
