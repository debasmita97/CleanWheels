package example.com.cleanwheels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {


    private Button buttonDelete;
    private TextView addressPickUp;
    private TextView date;
    private TextView phone;
    private TextView bookingId;
    private String address;
    private String bookingDate;
    private String phoneNumber;
    private String id;



    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        buttonDelete = findViewById(R.id.delete);

        addressPickUp = findViewById(R.id.address);
        date = findViewById(R.id.date);
        phone = findViewById(R.id.phone);
        bookingId = findViewById(R.id.bookingId);

        Intent receivedIntent = getIntent();

        id = receivedIntent.getStringExtra("id");
        address = receivedIntent.getStringExtra("address");
        bookingDate = receivedIntent.getStringExtra("Date");
        phoneNumber = receivedIntent.getStringExtra("phone");

        bookingId.setText(id);
        addressPickUp.setText(address);
        date.setText(bookingDate);
        phone.setText(phoneNumber);






        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = mDatabaseHelper.deleteData(phone.getText().toString());
                if(deletedRows > 0)
                    Toast.makeText(EditDataActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(EditDataActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
            }

        });




    }
}
