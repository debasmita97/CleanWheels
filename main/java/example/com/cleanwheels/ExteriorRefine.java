package example.com.cleanwheels;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ExteriorRefine extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "ExteriorRefine";

    Button buttonER;
    Button buttonqrCode;
    EditText phone;
    TextView displayDate;
    String bookingDate;
    String phoneNumber;
    String pickUpAddress;
    String amount;
    EditText address;

    DatabaseHelper  mDatabaseHelper = new DatabaseHelper(this);

    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exterior_refine);

        final CheckBox carPickUp = findViewById(R.id.carPickup);
        final TextView amountER = findViewById(R.id.amountER);
        buttonqrCode =findViewById(R.id.qrCode);

        displayDate = findViewById(R.id.dateEx);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);

        displayDate.addTextChangedListener(bookingTextWatcher);
        phone.addTextChangedListener(bookingTextWatcher);
        address.addTextChangedListener(bookingTextWatcher);
        final String payableAmount = "₹ 699";
        final String payableWithCarPickUp = "₹ 899";

        amountER.setText(payableAmount);
        carPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carPickUp.isChecked()) {
                    amountER.setText(payableWithCarPickUp);
                } else
                    amountER.setText(payableAmount);
            }
        });



        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ExteriorRefine.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                displayDate.setText(date);
            }
        };

        VideoView exteriorVideoView = findViewById(R.id.exteriorVideo);
        String videoPath = "android.resource://" + getPackageName() + '/' + R.raw.exterior_refine;
        Uri uri = Uri.parse(videoPath);
        exteriorVideoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        exteriorVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(exteriorVideoView);

        buttonER = findViewById(R.id.buttonER);

        buttonER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                bookingDate = displayDate.getText().toString();
                phoneNumber = phone.getText().toString();
                pickUpAddress = address.getText().toString();
                amount = amountER.getText().toString();

                AddData( pickUpAddress, bookingDate,phoneNumber,amount);


            }


        });


        /*sentPI = PendingIntent.getBroadcast(ExteriorRefine.this, 0, new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(ExteriorRefine.this, 0, new Intent(DELIVERED), 0);

        buttonER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountToPay = amountER.getText().toString();
                String bookingDate = displayDate.getText().toString();
                String phoneNumber = phone.getText().toString();

                String message = "Booking Confirmed for Exterior Refine!\nBooking Date" + bookingDate + "\nPayable Amount" + amountToPay + "\nThank You";

                if (ContextCompat.checkSelfPermission(ExteriorRefine.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ExteriorRefine.this, new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                } else {
                    SmsManager sms = SmsManager.getDefault();

                    //phone - Recipient's phone number
                    //address - Service Center Address (null for default)
                    //message - SMS message to be sent
                    //piSent - Pending intent to be invoked when the message is sent
                    //piDelivered - Pending intent to be invoked when the message is delivered to the recipient
                    // sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
                    sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);

                }
            }

        });
*/
        buttonqrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQr();
            }
        });

    }


    public void AddData(String  pickUpAddress, String bookingDate, String phoneNumber, String amount) {
        boolean insertData = mDatabaseHelper.insertData(pickUpAddress,bookingDate,phoneNumber, amount);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void openQr()
    {
        String phoneNo = phone.getText().toString();
        Intent intent =new Intent(this,QRCode.class);
        intent.putExtra("Phone", phoneNo);
        startActivity(intent);
    }

    private TextWatcher bookingTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            // displayDate = findViewById(R.id.dateCombo);
            phone = findViewById(R.id.phone);
            String bookingDate = displayDate.getText().toString().trim();
            String phoneNumber = phone.getText().toString().trim();
            String addressPickUp = address.getText().toString().trim();

            buttonER.setEnabled(!bookingDate.isEmpty() && !phoneNumber.isEmpty() && !addressPickUp.isEmpty());
            buttonqrCode.setEnabled(!bookingDate.isEmpty() && !phoneNumber.isEmpty() && !addressPickUp.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    protected void onResume() {
        super.onResume();

        //The deliveredPI PendingIntent does not fire in the Android emulator.
        //You have to test the application on a real device to view it.
        //However, the sentPI PendingIntent works on both, the emulator as well as on a real device.

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent successfully!", Toast.LENGTH_SHORT).show();
                        break;

                    //Something went wrong and there's no way to tell what, why or how.
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure!", Toast.LENGTH_SHORT).show();
                        break;

                    //Your device simply has no cell reception. You're probably in the middle of
                    //nowhere, somewhere inside, underground, or up in space.
                    //Certainly away from any cell phone tower.
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No service!", Toast.LENGTH_SHORT).show();
                        break;

                    //Something went wrong in the SMS stack, while doing something with a protocol
                    //description unit (PDU) (most likely putting it together for transmission).
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU!", Toast.LENGTH_SHORT).show();
                        break;

                    //You switched your device into airplane mode, which tells your device exactly
                    //"turn all radios off" (cell, wifi, Bluetooth, NFC, ...).
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off!", Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        };


        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered!", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        //register the BroadCastReceivers to listen for a specific broadcast
        //if they "hear" that broadcast, it will activate their onReceive() method
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
    }
}

