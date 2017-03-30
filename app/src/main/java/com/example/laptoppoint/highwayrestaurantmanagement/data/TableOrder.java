package com.example.laptoppoint.highwayrestaurantmanagement.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Laptop Point on 3/30/2017.
 */

public class TableOrder implements Serializable {
private static final long serialVersionUID = -6577234748166232293L;

    /**
     * Instantiates a new table order.
     */
    public TableOrder() {
        super();
        orders=new ArrayList<Order>();
    }

    /** The orders. */
    public ArrayList<Order> orders;

    /** The duration, in minutes. */
    public int duration;
}


