package com.ebediening.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingResponse implements Parcelable{

    @SerializedName("flag")
    @Expose
    public Integer flag;

    public List<BookData> getBookDataList() {
        return bookDataList;
    }

    public String getImageBasePath() {
        return imageBasePath;
    }

    public void setImageBasePath(String imageBasePath) {
        this.imageBasePath = imageBasePath;
    }

    public void setBookDataList(List<BookData> bookDataList) {
        this.bookDataList = bookDataList;
    }

    @SerializedName("image_base_path")
    @Expose
    public String imageBasePath;

    protected BookingResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
        imageBasePath = in.readString();
        bookDataList = in.createTypedArrayList(BookData.CREATOR);
    }

    public static final Creator<BookingResponse> CREATOR = new Creator<BookingResponse>() {
        @Override
        public BookingResponse createFromParcel(Parcel in) {
            return new BookingResponse(in);
        }

        @Override
        public BookingResponse[] newArray(int size) {
            return new BookingResponse[size];
        }
    };

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @SerializedName("data")
    @Expose
    public List<BookData> bookDataList;

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
        dest.writeTypedList(bookDataList);
    }

    public static class BookData implements Parcelable {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("admin_id")
        @Expose
        private Object adminId;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("table_id")
        @Expose
        private String tableId;
        @SerializedName("booking_date")
        @Expose
        private String bookingDate;
        @SerializedName("booking_time")
        @Expose
        private String bookingTime;
        @SerializedName("no_of_tables")
        @Expose
        private String noOfTables;
        @SerializedName("no_of_persons")
        @Expose
        private String noOfPersons;
        @SerializedName("contact_name")
        @Expose
        private String contactName;
        @SerializedName("contact_email")
        @Expose
        private String contactEmail;
        @SerializedName("contact_phone")
        @Expose
        private String contactPhone;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("booking_status")
        @Expose
        private String bookingStatus;
        @SerializedName("is_confirm")
        @Expose
        private String isConfirm;
        @SerializedName("from_time")
        @Expose
        private String fromTime;
        @SerializedName("to_time")
        @Expose
        private String toTime;
        @SerializedName("is_active")
        @Expose
        private String isActive;
        @SerializedName("created_on")
        @Expose
        private String createdOn;
        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("modified_on")
        @Expose
        private String modifiedOn;
        @SerializedName("modified_by")
        @Expose
        private String modifiedBy;
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("opening_timing")
        @Expose
        private String openingTiming;
        @SerializedName("closing_timing")
        @Expose
        private String closingTiming;
        @SerializedName("restaurant_image")
        @Expose
        private String restaurantImage;
        @SerializedName("no_of_persons_dropdown_end_value")
        @Expose
        private String noOfPersonsDropdownEndValue;

        protected BookData(Parcel in) {
            id = in.readString();
            userId = in.readString();
            restaurantId = in.readString();
            tableId = in.readString();
            bookingDate = in.readString();
            bookingTime = in.readString();
            noOfTables = in.readString();
            noOfPersons = in.readString();
            contactName = in.readString();
            contactEmail = in.readString();
            contactPhone = in.readString();
            message = in.readString();
            bookingStatus = in.readString();
            isConfirm = in.readString();
            fromTime = in.readString();
            toTime = in.readString();
            isActive = in.readString();
            createdOn = in.readString();
            createdBy = in.readString();
            modifiedOn = in.readString();
            modifiedBy = in.readString();
            restaurantName = in.readString();
            openingTiming = in.readString();
            closingTiming = in.readString();
            restaurantImage = in.readString();
            noOfPersonsDropdownEndValue = in.readString();
        }

        public static final Creator<BookData> CREATOR = new Creator<BookData>() {
            @Override
            public BookData createFromParcel(Parcel in) {
                return new BookData(in);
            }

            @Override
            public BookData[] newArray(int size) {
                return new BookData[size];
            }
        };

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

        public Object getAdminId() {
            return adminId;
        }

        public void setAdminId(Object adminId) {
            this.adminId = adminId;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getTableId() {
            return tableId;
        }

        public void setTableId(String tableId) {
            this.tableId = tableId;
        }

        public String getBookingDate() {
            return bookingDate;
        }

        public void setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
        }

        public String getBookingTime() {
            return bookingTime;
        }

        public void setBookingTime(String bookingTime) {
            this.bookingTime = bookingTime;
        }

        public String getNoOfTables() {
            return noOfTables;
        }

        public void setNoOfTables(String noOfTables) {
            this.noOfTables = noOfTables;
        }

        public String getNoOfPersons() {
            return noOfPersons;
        }

        public void setNoOfPersons(String noOfPersons) {
            this.noOfPersons = noOfPersons;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

        public String getIsConfirm() {
            return isConfirm;
        }

        public void setIsConfirm(String isConfirm) {
            this.isConfirm = isConfirm;
        }

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getToTime() {
            return toTime;
        }

        public void setToTime(String toTime) {
            this.toTime = toTime;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getOpeningTiming() {
            return openingTiming;
        }

        public void setOpeningTiming(String openingTiming) {
            this.openingTiming = openingTiming;
        }

        public String getClosingTiming() {
            return closingTiming;
        }

        public void setClosingTiming(String closingTiming) {
            this.closingTiming = closingTiming;
        }

        public String getRestaurantImage() {
            return restaurantImage;
        }

        public void setRestaurantImage(String restaurantImage) {
            this.restaurantImage = restaurantImage;
        }

        public String getNoOfPersonsDropdownEndValue() {
            return noOfPersonsDropdownEndValue;
        }

        public void setNoOfPersonsDropdownEndValue(String noOfPersonsDropdownEndValue) {
            this.noOfPersonsDropdownEndValue = noOfPersonsDropdownEndValue;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(userId);
            dest.writeString(restaurantId);
            dest.writeString(tableId);
            dest.writeString(bookingDate);
            dest.writeString(bookingTime);
            dest.writeString(noOfTables);
            dest.writeString(noOfPersons);
            dest.writeString(contactName);
            dest.writeString(contactEmail);
            dest.writeString(contactPhone);
            dest.writeString(message);
            dest.writeString(bookingStatus);
            dest.writeString(isConfirm);
            dest.writeString(fromTime);
            dest.writeString(toTime);
            dest.writeString(isActive);
            dest.writeString(createdOn);
            dest.writeString(createdBy);
            dest.writeString(modifiedOn);
            dest.writeString(modifiedBy);
            dest.writeString(restaurantName);
            dest.writeString(openingTiming);
            dest.writeString(closingTiming);
            dest.writeString(restaurantImage);
            dest.writeString(noOfPersonsDropdownEndValue);
        }
    }
}
