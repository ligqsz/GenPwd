package com.pax.genpwd;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.EditText;
import android.widget.TextView;

import com.pax.genpwd.jni.Sha1Utils;
import com.pax.genpwd.utils.Density;
import com.pax.genpwd.utils.ToastUtils;
import com.pax.genpwd.utils.Utils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

/**
 * @author ligq
 */
public class MainActivity extends AppCompatActivity implements OnDateSelectedListener {

    private MaterialCalendarView calendarView;
    private String selectDate;
    private CustomEditText etSn;
    private CardView cvGenPwd;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Density.adaptScreen(this, 360, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListeners();
    }

    private void initListeners() {
        calendarView.setOnDateChangedListener(this);
        calendarView.setOnTitleClickListener(view ->
                showDatePickerDialog(this, calendarView.getSelectedDate()
                        , (view1, year, monthOfYear, dayOfMonth) -> {
                            CalendarDay currentDate = CalendarDay.from
                                    (year, monthOfYear, dayOfMonth);
                            calendarView.setCurrentDate(currentDate);
                            calendarView.setSelectedDate(currentDate);
                            selectDate = Utils.formatDate(currentDate.getDate());
                            String sn = etSn.getText().toString().trim();
                            if (Utils.checkSn(sn)) {
                                setTextResult(sn);
                            }
                        })

        );

        cvGenPwd.setOnClickListener(view -> {
            String sn = etSn.getText().toString().trim();
            if (!Utils.checkSn(sn)) {
                ToastUtils.showToast("Invalid SN!");
                return;
            }
            setTextResult(sn);
        });

        etSn.setOnKeyBoardHideListener((keyCode, event) -> etSn.clearFocus());
        etSn.setOnClickListener(view -> requestFocus(etSn));
    }

    private void setTextResult(String sn) {
        etSn.clearFocus();
        Utils.hideSystemKeyboard(this, getCurrentFocus());
        String data = Utils.genData(selectDate, sn);
        String reversalData = Utils.getReversalData(data);
        byte[] bytes = Sha1Utils.calcSha1(data, data.length(), reversalData, reversalData.length());
        String result = Utils.getFinalPwd(Utils.byteArr2IntArr(bytes), selectDate);
        tvResult.setText(result);
    }

    private void initView() {
        calendarView = findViewById(R.id.calendarView);
        etSn = findViewById(R.id.et_sn);
        cvGenPwd = findViewById(R.id.cv_gen);
        tvResult = findViewById(R.id.tv_result);
        //set current date
        Calendar calendar = Calendar.getInstance();
        calendarView.setSelectedDate(calendar.getTime());
        selectDate = Utils.formatDate(calendar.getTime());
    }

    public static void showDatePickerDialog(Context context, CalendarDay day,
                                            DatePickerDialog.OnDateSetListener callback) {
        if (day == null) {
            day = CalendarDay.today();
        }
        DatePickerDialog dialog = new DatePickerDialog(
                context, 0, callback, day.getYear(), day.getMonth(), day.getDay()
        );
        dialog.show();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        selectDate = Utils.formatDate(date.getDate());
        String sn = etSn.getText().toString().trim();
        if (Utils.checkSn(sn)) {
            setTextResult(sn);
        }
    }

    private void requestFocus(EditText et) {
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }
}
