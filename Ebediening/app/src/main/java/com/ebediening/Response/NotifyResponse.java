package com.ebediening.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotifyResponse {

    @SerializedName("flag")
    @Expose
    public Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<NotifyData> getNofifyData() {
        return nofifyData;
    }

    public void setNofifyData(List<NotifyData> nofifyData) {
        this.nofifyData = nofifyData;
    }

    @SerializedName("data")
    @Expose
    public List<NotifyData> nofifyData;

    public class NotifyData {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("notification_template_id")
        @Expose
        private String notificationTemplateId;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("booking_id")
        @Expose
        private String bookingId;
        @SerializedName("table_id")
        @Expose
        private String tableId;
        @SerializedName("notification_from")
        @Expose
        private String notificationFrom;
        @SerializedName("notification_type")
        @Expose
        private String notificationType;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("is_confirm")
        @Expose
        private Object isConfirm;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("is_important")
        @Expose
        private String isImportant;
        @SerializedName("is_read")
        @Expose
        private String isRead;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("created_on")
        @Expose
        private String createdOn;
        @SerializedName("created_by")
        @Expose
        private Object createdBy;
        @SerializedName("modified_on")
        @Expose
        private String modifiedOn;
        @SerializedName("modified_by")
        @Expose
        private Object modifiedBy;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("created_date")
        @Expose
        private String createdDate;
        @SerializedName("created_time")
        @Expose
        private String createdTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNotificationTemplateId() {
            return notificationTemplateId;
        }

        public void setNotificationTemplateId(String notificationTemplateId) {
            this.notificationTemplateId = notificationTemplateId;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getTableId() {
            return tableId;
        }

        public void setTableId(String tableId) {
            this.tableId = tableId;
        }

        public String getNotificationFrom() {
            return notificationFrom;
        }

        public void setNotificationFrom(String notificationFrom) {
            this.notificationFrom = notificationFrom;
        }

        public String getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getIsConfirm() {
            return isConfirm;
        }

        public void setIsConfirm(Object isConfirm) {
            this.isConfirm = isConfirm;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIsImportant() {
            return isImportant;
        }

        public void setIsImportant(String isImportant) {
            this.isImportant = isImportant;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public Object getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(Object modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }
    }
}
