package com.example.laptoppoint.highwayrestaurantmanagement.mainly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptoppoint.highwayrestaurantmanagement.R;
import com.example.laptoppoint.highwayrestaurantmanagement.adapter.TableOrderAdapter;
import com.example.laptoppoint.highwayrestaurantmanagement.data.DataProvider;
import com.example.laptoppoint.highwayrestaurantmanagement.data.Order;
import com.example.laptoppoint.highwayrestaurantmanagement.data.TableOrder;

public class TableActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    Table table;

    /** The table order. */
    TableOrder tableOrder;

    /** The listview. */
    ListView listview;

    /** The button add. */
    ImageButton buttonAdd;

    /** The button finalize. */
    ImageButton buttonFinalize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Bundle b = getIntent().getExtras();
        table = DataProvider.getTable(b.getInt("tableID"));
        Log.d("CG","Table activity for "+table);

        //Set table order
        if(table.tableOrder==null)
            table.tableOrder=new TableOrder();
        tableOrder=table.tableOrder;

        //Set data
        listview = (ListView) findViewById(R.id.tableListView);
        listview.setAdapter(new TableOrderAdapter(this, tableOrder));
        listview.setOnItemClickListener(this);

        buttonAdd =(ImageButton) findViewById(R.id.orderAddButton);
        buttonAdd.setOnClickListener(this);

        buttonFinalize =(ImageButton) findViewById(R.id.tableOrderFinalizaButton);
        buttonFinalize.setOnClickListener(this);

    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStart()
     */
    protected void onStart()
    {
        super.onStart();

        TextView text= (TextView) findViewById(R.id.tableTitle);
        text.setText("Table "+table.id);

        text= (TextView) findViewById(R.id.tableCustomers);
        text.setText("Customers: "+table.curClients+"/"+table.maxClients+"");

        listview.invalidateViews();
    }

    /* Event triggered at click on "New Order button"
     *
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View button) {
        if(button==buttonAdd)
        {
            table.tableOrder.orders.add(new Order());
            this.listview.invalidateViews();
            Toast.makeText(this, "Created a new order for this table", Toast.LENGTH_SHORT).show();
        }
        else
        {
            listview.setOnItemClickListener(null);
            buttonAdd.setOnClickListener(null);
            table.tableOrder=null;
            table.empty=true;
            table.curClients=0;
            Toast.makeText(this, "Finalized the order!", Toast.LENGTH_SHORT).show();
        }
    }

    /* Event triggered on click on one of the orders
     *
     * (non-Javadoc)
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent myIntent = new Intent(this, OrderActivity.class);
        myIntent.putExtra("tableID", table.id);
        myIntent.putExtra("orderID", table.tableOrder.orders.get(position).id);
        this.startActivity(myIntent);
    }

}