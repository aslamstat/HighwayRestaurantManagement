package com.example.laptoppoint.highwayrestaurantmanagement.data;

import java.util.ArrayList;

/**
 * Created by Laptop Point on 3/30/2017.
 */

public class Order {
    /** The global id. */
    private static int globalID=0;

    /** The id. */
    public int id;

    /** The ordered products. */
    public ArrayList<OrderProduct> products;

    /** The total price. */
    public float totalPrice;

    /** The duration, in minutes. */
    public int duration;

    /**
     * Instantiates a new order.
     */
    public Order() {
        id=globalID++;
        products=new ArrayList<OrderProduct>();
        totalPrice=0;
        duration=0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Order [id=" + id + ", products=" + products + ", totalPrice=" + totalPrice + "]";
    }
}


