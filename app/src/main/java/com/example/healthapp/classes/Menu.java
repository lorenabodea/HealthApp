package com.example.healthapp.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu implements Parcelable {

    String food;
    Integer quantity;


    public Menu(String food, Integer quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    protected Menu(Parcel in) {
        food = in.readString();
        quantity = in.readInt();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "food='" + food + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(food);
        dest.writeInt(quantity);
    }
}
