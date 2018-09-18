package com.ebediening.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavResponse implements Parcelable{


    @SerializedName("flag")
    @Expose
    public Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<FavData> getFavDataList() {
        return favDataList;
    }

    public void setFavDataList(List<FavData> favDataList) {
        this.favDataList = favDataList;
    }

    @SerializedName("data")
    @Expose
    public List<FavData> favDataList;

    protected FavResponse(Parcel in) {
        if (in.readByte() == 0) {
            flag = null;
        } else {
            flag = in.readInt();
        }
    }

    public static final Creator<FavResponse> CREATOR = new Creator<FavResponse>() {
        @Override
        public FavResponse createFromParcel(Parcel in) {
            return new FavResponse(in);
        }

        @Override
        public FavResponse[] newArray(int size) {
            return new FavResponse[size];
        }
    };

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
    }


    public static class FavData implements Parcelable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("restaurant_id")
        @Expose
        private String restaurantId;
        @SerializedName("user_id")
        @Expose
        private String userId;
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
        @SerializedName("restaurant_name")
        @Expose
        private String restaurantName;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("restaurant_address")
        @Expose
        private String restaurantAddress;

        protected FavData(Parcel in) {
            id = in.readString();
            restaurantId = in.readString();
            userId = in.readString();
            createdOn = in.readString();
            modifiedOn = in.readString();
            restaurantName = in.readString();
            firstName = in.readString();
            lastName = in.readString();
            restaurantAddress = in.readString();
        }

        public static final Creator<FavData> CREATOR = new Creator<FavData>() {
            @Override
            public FavData createFromParcel(Parcel in) {
                return new FavData(in);
            }

            @Override
            public FavData[] newArray(int size) {
                return new FavData[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
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

        public String getRestaurantAddress() {
            return restaurantAddress;
        }

        public void setRestaurantAddress(String restaurantAddress) {
            this.restaurantAddress = restaurantAddress;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(restaurantId);
            dest.writeString(userId);
            dest.writeString(createdOn);
            dest.writeString(modifiedOn);
            dest.writeString(restaurantName);
            dest.writeString(firstName);
            dest.writeString(lastName);
            dest.writeString(restaurantAddress);
        }
    }
}
