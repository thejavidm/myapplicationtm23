package mx.uabcs.idecasso.myapplicationtm.models;

import android.support.annotation.IntDef;

import org.parceler.Parcel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Parcel
public class Phone {
    public Long _id;
    public Long _idContact;
    private String number;
    private @Category int category;

    public Phone() {}

    public Phone(String number, int category) {
        this.number = number;
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Category
    public int getCategory() {
        return category;
    }

    public void setCategory(@Category int category) {
        this.category = category;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({HOME, WORK, OFFICE, MOBILE, OTHER})
    public @interface Category {}

    public static final int HOME = 0;
    public static final int WORK = 1;
    public static final int OFFICE = 2;
    public static final int MOBILE = 3;
    public static final int OTHER = 4;
}
