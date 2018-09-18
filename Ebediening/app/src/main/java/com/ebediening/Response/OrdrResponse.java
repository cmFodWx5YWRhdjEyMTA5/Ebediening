package com.ebediening.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdrResponse implements Parcelable {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    protected OrdrResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        currencyType = in.readString();
        orderDataList = in.createTypedArrayList(OrderData.CREATOR);
    }

    public static final Creator<OrdrResponse> CREATOR = new Creator<OrdrResponse>() {
        @Override
        public OrdrResponse createFromParcel(Parcel in) {
            return new OrdrResponse(in);
        }

        @Override
        public OrdrResponse[] newArray(int size) {
            return new OrdrResponse[size];
        }
    };

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public List<OrderData> getOrderDataList() {
        return orderDataList;
    }

    public void setOrderDataList(List<OrderData> orderDataList) {
        this.orderDataList = orderDataList;
    }

    @SerializedName("currency_type")
    @Expose
    public String currencyType;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @SerializedName("data")
    @Expose
    public List<OrderData> orderDataList;

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
        dest.writeString(currencyType);
        dest.writeTypedList(orderDataList);
    }

    public static class OrderData implements Parcelable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("invoice_number")
        @Expose
        private String invoiceNumber;
        @SerializedName("delivery_type")
        @Expose
        private String deliveryType;
        @SerializedName("order_type")
        @Expose
        private Object orderType;
        @SerializedName("delivery_boy_id")
        @Expose
        private Object deliveryBoyId;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;
        @SerializedName("is_delivered")
        @Expose
        private Object isDelivered;
        @SerializedName("total_items")
        @Expose
        private String totalItems;

        protected OrderData(Parcel in) {
            id = in.readString();
            total = in.readString();
            invoiceNumber = in.readString();
            deliveryType = in.readString();
            paymentStatus = in.readString();
            totalItems = in.readString();
        }

        public static final Creator<OrderData> CREATOR = new Creator<OrderData>() {
            @Override
            public OrderData createFromParcel(Parcel in) {
                return new OrderData(in);
            }

            @Override
            public OrderData[] newArray(int size) {
                return new OrderData[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public Object getOrderType() {
            return orderType;
        }

        public void setOrderType(Object orderType) {
            this.orderType = orderType;
        }

        public Object getDeliveryBoyId() {
            return deliveryBoyId;
        }

        public void setDeliveryBoyId(Object deliveryBoyId) {
            this.deliveryBoyId = deliveryBoyId;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public Object getIsDelivered() {
            return isDelivered;
        }

        public void setIsDelivered(Object isDelivered) {
            this.isDelivered = isDelivered;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(total);
            dest.writeString(invoiceNumber);
            dest.writeString(deliveryType);
            dest.writeString(paymentStatus);
            dest.writeString(totalItems);
        }
    }
}
