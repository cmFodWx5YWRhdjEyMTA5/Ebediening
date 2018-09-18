package com.ebediening;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ebediening.Adapter_Class.Adapter_No_Table;
import com.ebediening.Adapter_Class.Adapter_TimeSlots;
import com.ebediening.Response.BookingResponse;
import com.ebediening.Response.UserResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.CommonMethods;
import com.ebediening.Utilites.CommonUtilFunctions;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.RestClient;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class BookTableActivity extends BaseActivity implements View.OnClickListener {

    String id = "", fromTime = "", toTime = "";
    int maxPersoons = 0;
    ArrayList<String> personCount, timeSlots;
    public static final String BOOK = "book";
    BookingResponse.BookData bookData;
    String imagePath;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_atable);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("BOOK TABLE");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        bookData = (BookingResponse.BookData) getIntent().getParcelableExtra(BOOK);
        imagePath=getIntent().getStringExtra("imagePath");
        setFields(bookData);
    }

    private void setFields(BookingResponse.BookData bookData) {
        personCount = new ArrayList<>();
        timeSlots = new ArrayList<>();
        restName.setText(bookData.getRestaurantName());
        editName.setText(bookData.getContactName());
        editEmail.setText(bookData.getContactEmail());
        editPhone.setText(bookData.getContactPhone());
        editDate.setText(bookData.getBookingDate());
        editMessage.setText(bookData.getMessage());
        id = bookData.getId();
        maxPersoons = Integer.parseInt(bookData.getNoOfPersonsDropdownEndValue());
        fromTime =bookData.getFromTime();
        toTime = bookData.getToTime();
        Picasso.with(BookTableActivity.this).load(imagePath+bookData.getRestaurantImage()).placeholder(R.drawable.white_img).into(restImage);

        for (int i = 1; i <= maxPersoons; i++) {
            personCount.add(String.valueOf(i));
        }
//time slots
        if (fromTime==null || fromTime.equalsIgnoreCase("")) {
            fromTime="00:00:00";
            SimpleDateFormat slotTime = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat final_time = new SimpleDateFormat("HH:mm");
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            try {
                startCalendar.setTime(slotTime.parse(fromTime));
                endCalendar.setTime(slotTime.parse(toTime));

                while (endCalendar.after(startCalendar)) {
                    String slotStartTime = final_time.format(startCalendar.getTime());
                    startCalendar.add(Calendar.MINUTE, 15);
                    String slotEndTime = final_time.format(startCalendar.getTime());
                    timeSlots.add(slotStartTime);
//                    Log.d("DATE", slotStartTime + " - " + slotEndTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Adapter_TimeSlots adapter_time = new Adapter_TimeSlots(BookTableActivity.this, timeSlots);
            spTimeSlot.setAdapter(adapter_time);
        }
        else {
            SimpleDateFormat slotTime = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat final_time = new SimpleDateFormat("HH:mm");
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            try {
                startCalendar.setTime(slotTime.parse(fromTime));
                endCalendar.setTime(slotTime.parse(toTime));

                while (endCalendar.after(startCalendar)) {
                    String slotStartTime = final_time.format(startCalendar.getTime());
                    startCalendar.add(Calendar.MINUTE, 15);
                    String slotEndTime = final_time.format(startCalendar.getTime());
                    timeSlots.add(slotStartTime);
//                    Log.d("DATE", slotStartTime + " - " + slotEndTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Adapter_TimeSlots adapter_time = new Adapter_TimeSlots(BookTableActivity.this, timeSlots);
            spTimeSlot.setAdapter(adapter_time);
        }
        Adapter_No_Table adapter = new Adapter_No_Table(BookTableActivity.this, personCount);
        spNoTable.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_confirm:

                break;

            case R.id.edit_date:

                break;

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void confirmTable() {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        showProgressbar("Loading", "Please wait...");
        ebedieningService.bookTable(Preferences.getInstance().getUserId(),id,editName.getText().toString().trim(),spNoTable.getSelectedItem().toString(),editDate.getText().toString().trim(),spTimeSlot.getSelectedItem().toString(),editPhone.getText().toString().trim(),editEmail.getText().toString().trim(),editMessage.getText().toString().trim()).
                enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                        if (response.body().getFlag() == 1) {
                          finish();
                        } else {
                            if (response.body().getMessage().equalsIgnoreCase("Verify your email.")) {
                                verifyEmail();
                            } else {
                                CommonMethods.showtoast(BookTableActivity.this, response.body().getMessage().toString());
                            }
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), Ebediening.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void resendCode() {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        showProgressbar("Loading", "Please wait...");
        ebedieningService.resendCode(Preferences.getInstance().getUserId()).
                enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                        if (response.body().getFlag() == 1) {
                            CommonMethods.showtoast(BookTableActivity.this, response.body().getMessage());
                        } else {
                            CommonMethods.showtoast(BookTableActivity.this, response.body().getMessage());
                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), Ebediening.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void verifyEmail() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookTableActivity.this);
        alertDialogBuilder.setMessage("Please verify from your registered email to book a table. Please be sure to check your Spam/Junk box.")
                .setCancelable(false)
                .setPositiveButton("Resend",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            public void onClick(DialogInterface dialog, int id) {
                                //api call
                                resendCode();
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }




    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.rest_image)
    ImageView restImage;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.linear_confirm)
    void confirm(){
        if (editName.getText().toString().trim().equalsIgnoreCase("")) {
            CommonMethods.showtoast(BookTableActivity.this, "Please enter name");
        } else if (editEmail.getText().toString().trim().equalsIgnoreCase("")) {
            CommonMethods.showtoast(BookTableActivity.this, "Please enter email-id");
        } else if (!CommonMethods.isValidEmail(editEmail.getText().toString().trim())) {
            CommonMethods.showtoast(BookTableActivity.this, "Please enter valid email-id");
        } else if (editPhone.getText().toString().trim().equalsIgnoreCase("")) {
            CommonMethods.showtoast(BookTableActivity.this, "Please enter phone number");
        } else if (editPhone.getText().toString().trim().length() < 10) {
            CommonMethods.showtoast(BookTableActivity.this, "Please enter valid phone number");
        } else if (editDate.getText().toString().trim().equalsIgnoreCase("")) {
            CommonMethods.showtoast(BookTableActivity.this, "Please select date");
        } else {

            confirmTable();
        }
    }

    @OnClick(R.id.edit_date)
    void editDate(){
        CommonUtilFunctions.DatePickerDialog_yy_mm_dd(BookTableActivity.this, editDate);
    }

    @BindView(R.id.edit_date)
    EditText editDate;

    @BindView(R.id.rest_name)
    TextView restName;

    @BindView(R.id.edit_name)
    EditText editName;

    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_phone)
    EditText editPhone;

    @BindView(R.id.spinner_no_table)
    Spinner spNoTable;

    @BindView(R.id.spinner_time_slots)
    Spinner spTimeSlot;

    @BindView(R.id.edit_message)
    EditText editMessage;

}
