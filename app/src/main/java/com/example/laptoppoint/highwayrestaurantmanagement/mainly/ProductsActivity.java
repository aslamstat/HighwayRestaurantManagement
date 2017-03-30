package com.example.laptoppoint.highwayrestaurantmanagement.mainly;

import android.icu.text.DisplayContext;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.laptoppoint.highwayrestaurantmanagement.R;
import com.example.laptoppoint.highwayrestaurantmanagement.adapter.ProductsAdapter;
import com.example.laptoppoint.highwayrestaurantmanagement.data.DataProvider;
import com.example.laptoppoint.highwayrestaurantmanagement.data.Order;
import com.example.laptoppoint.highwayrestaurantmanagement.data.OrderProduct;
import com.example.laptoppoint.highwayrestaurantmanagement.data.Product;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Table table;

    /** The order. */
    Order order;

    /** The products. */
    ArrayList<Product> products;

    /** The category. */
    Type category;

    /** The listview. */
    GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Bundle b = getIntent().getExtras();
        table = DataProvider.getTable(b.getInt("tableID"));

        //Set order
        order=DataProvider.getOrder(table.id, b.getInt("orderID"));
        Log.d("CG","Product Category activity for "+order);

        //Get the products
        category=(Type) b.get("category");
        products=DataProvider.getProductsFromCategory(category);

        //Prepare the grid
        gridview = (GridView) findViewById(R.id.productsGridView);
        gridview.setAdapter(new ProductsAdapter(getBaseContext(), products));

        gridview.setOnItemClickListener(this);

    }

    /* Event triggered on click on one of the products
     *
     * (non-Javadoc)
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        //Add the new product to the order
        Product selectedProduct=products.get(position);

        //Check if there is already such a product in the order
        boolean done=false;
        for(OrderProduct op: order.products)
            if(op.product.equals(selectedProduct))
            {
                op.quantity++;
                done=true;
                Toast.makeText(getBaseContext(),"Product "+selectedProduct.name+" already ordered.", Toast.LENGTH_SHORT).show();
                break;
            }

        //Add a new order product
        if(!done)
        {
            OrderProduct op=new OrderProduct(selectedProduct);
            order.products.add(op);
        }
        //Update cost and duration
        order.totalPrice+=selectedProduct.price;
        if(selectedProduct.duration>order.duration)
            order.duration=selectedProduct.duration;

        //Return success, so the parent knows it was a successful selection
        setResult(RESULT_OK);
        finish();
    }


    public class Type {
    }
}