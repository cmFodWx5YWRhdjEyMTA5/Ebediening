package com.ebediening.Getter_Setter;

import android.widget.LinearLayout;

/**
 * Created by dikhong on 11-04-2018.
 */

public class menu_category {
    public String getSelect_unselect() {
        return select_unselect;
    }

    public void setSelect_unselect(String select_unselect) {
        this.select_unselect = select_unselect;
    }

    public LinearLayout getBorder() {
        return border;
    }

    public void setBorder(LinearLayout border) {
        this.border = border;
    }

    String select_unselect;
    LinearLayout border;
}
