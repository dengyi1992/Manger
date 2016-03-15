package com.dy.manager.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.dy.manager.R;

/**
 * Created by deng on 16-3-15.
 */
public class NewTask extends Activity implements NumberPicker.OnValueChangeListener,NumberPicker.OnScrollListener,NumberPicker.Formatter {
    private NumberPicker mHourPvNumberPicker;
    private TextView mHourTvTextView;
    private NumberPicker mMinutePvNumberPicker;
    private TextView mMinuteTvTextView;
    private NumberPicker mSecondPvNumberPicker;
    private TextView mSecondTvTextView;
    private EditText mIntefaceurlEditText;
    private EditText mIntefacetagEditText;
    private Button mTypeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);
        mHourPvNumberPicker = (NumberPicker) findViewById(R.id.hour_pv);
        mHourTvTextView = (TextView) findViewById(R.id.hour_tv);
        mMinutePvNumberPicker = (NumberPicker) findViewById(R.id.minute_pv);
        mMinuteTvTextView = (TextView) findViewById(R.id.minute_tv);
        mSecondPvNumberPicker = (NumberPicker) findViewById(R.id.second_pv);
        mSecondTvTextView = (TextView) findViewById(R.id.second_tv);
        mIntefaceurlEditText = (EditText) findViewById(R.id.intefaceurl);
        mIntefacetagEditText = (EditText) findViewById(R.id.intefacetag);
        mTypeButton = (Button) findViewById(R.id.bt_type);


    }
    private void init() {
        mHourPvNumberPicker.setFormatter(this);
        mHourPvNumberPicker.setOnValueChangedListener(this);
        mHourPvNumberPicker.setOnScrollListener(this);
        mHourPvNumberPicker.setMaxValue(24);
        mHourPvNumberPicker.setMinValue(0);
        mHourPvNumberPicker.setValue(9);

        mMinutePvNumberPicker.setFormatter(this);
        mMinutePvNumberPicker.setOnValueChangedListener(this);
        mMinutePvNumberPicker.setOnScrollListener(this);
        mMinutePvNumberPicker.setMaxValue(60);
        mMinutePvNumberPicker.setMinValue(0);
        mMinutePvNumberPicker.setValue(49);

        mSecondPvNumberPicker.setFormatter(this);
        mSecondPvNumberPicker.setOnValueChangedListener(this);
        mSecondPvNumberPicker.setOnScrollListener(this);
        mSecondPvNumberPicker.setMaxValue(60);
        mSecondPvNumberPicker.setMinValue(0);
        mSecondPvNumberPicker.setValue(49);
    }

    @Override
    public String format(int i) {
        return null;
    }

    @Override
    public void onScrollStateChange(NumberPicker numberPicker, int i) {

    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }
}
