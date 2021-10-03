package example.com.cleanwheels;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//import k.kurchi.locationtest.EditDataActivity;
import example.com.cleanwheels.R;

/**
 * Created by User on 2/28/2017.
 */

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    example.com.cleanwheels.DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);


        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getAllData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(0));
            listData.add(data.getString(1));
            listData.add(data.getString(2));
            listData.add(data.getString(3));
            listData.add(data.getString(4));
            //listData.add(data.getString(4));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        /*Cursor res = mDatabaseHelper.getAllData();
        if(res.getCount() == 0) {
            // show message
            toastMessage("Error! Nothing found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("BookingId :"+ res.getString(0)+"\n");
            buffer.append("Address :"+ res.getString(1)+"\n");
            buffer.append("Date :"+ res.getString(2)+"\n");
            buffer.append("Address :"+ res.getString(3)+"\n\n");
        }
*/
        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phone = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + phone);

                Cursor data = mDatabaseHelper.getData(phone); //get the id associated with that name
                int bookingID = -1;
                String address = null;
                String date = null;


                while(data.moveToNext()){
                    bookingID = data.getInt(0);
                    address = data.getString(1);
                    date = data.getString(2);

                }
                if(bookingID > -1 && address != null && date != null ){
                    Log.d(TAG, "onItemClick: The ID is: " + bookingID + " The address is:" + address + "The booking date is:" + date );
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id",bookingID);

                    editScreenIntent.putExtra("address",address);
                    editScreenIntent.putExtra("Date", date);
                    editScreenIntent.putExtra("phone",phone);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}