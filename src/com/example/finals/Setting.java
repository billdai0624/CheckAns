package com.example.finals;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Setting extends Fragment {
	private TextView quantity;
	private TextView quantity_display;
	private TextView point;
	private TextView point_display;
	private int point_var;
	private int quantity_var;
	private TextView timer;
	private ToggleButton toggle;
	private TimePicker tp;
	private int timerMode = 0;
	private RadioGroup rgp;
	private RadioButton timing;
	private RadioButton countdown;
	private TextView timer_display;
	private EditText input;
	private EditText pointIn;
	private Calendar c1,c2;
	private Bundle bd;
	private boolean withTimer;
	@Override 
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	
		View v = inflater.inflate(R.layout.settings, container, false);
		quantity = (TextView)v.findViewById(R.id.setting_text0);
		point = (TextView)v.findViewById(R.id.score);
		point_display = (TextView)v.findViewById(R.id.point);
		quantity_display = (TextView)v.findViewById(R.id.questionNumber);
		timer = (TextView)v.findViewById(R.id.setting_text1);
		toggle = (ToggleButton)v.findViewById(R.id.toggle);
		timer_display = (TextView)v.findViewById(R.id.setting_text3);
		c1 = Calendar.getInstance();
		c2 = Calendar.getInstance();
		
		
		quantity.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				View inputXML = inflater.inflate(R.layout.dialog_input,null);
				try {
					input = (EditText)inputXML.findViewById(R.id.quantity);
				} 
				catch (InflateException e) {

				}
				Builder inputDialog = new AlertDialog.Builder(getActivity())
				.setTitle("Quantity")
				.setMessage("Please enter the quantity of your questions")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(inputXML)
				.setCancelable(false);
				inputDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try{
							quantity_var = Integer.parseInt(input.getText().toString());
							if(quantity_var>10000)
								throw new Exception();
							quantity_display.setText(input.getText());
						
						}
						catch(Exception e){
							Toast.makeText(getActivity(), "The quantity of your questions is abnormal",Toast.LENGTH_LONG).show();
						}
					}
					
				});
				inputDialog.setNegativeButton("Cancel", null).show();
				
			}
			
		});
		toggle.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if(toggle.isChecked()){
					withTimer = true;
					timer.setEnabled(true);
					timer.setAlpha((float)1.0);
					timer.setOnClickListener(new OnClickListener(){
						@Override
						public void onClick(View v) {
						
							View timePickerXML = inflater.inflate(R.layout.dialog_timepicker, null);
						/*	if (timePickerXML != null) {
							    ViewGroup parent = (ViewGroup) timePickerXML.getParent();
							    if (parent != null) {
							        parent.removeView(timePickerXML);
							    }
							}*/
							try {
								timePickerXML = inflater.inflate(R.layout.dialog_timepicker,null);
								tp = (TimePicker)timePickerXML.findViewById(R.id.timePicker1);
								rgp = (RadioGroup)timePickerXML.findViewById(R.id.radioGroup1);
								timing = (RadioButton)timePickerXML.findViewById(R.id.radio0);
								countdown = (RadioButton)timePickerXML.findViewById(R.id.radio1);
							} 
							catch (InflateException e) {

							}
							Builder timePickerDialog = new AlertDialog.Builder(getActivity())
							.setTitle("Timer")
							.setMessage("Please choose the timer mode")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setView(timePickerXML)
							.setCancelable(false);
							rgp.setOnCheckedChangeListener(new OnCheckedChangeListener(){
								@Override
								public void onCheckedChanged(RadioGroup group, int checkedId) {
									if(checkedId==R.id.radio0){
										tp.setVisibility(8);
									}
									else{
										tp.setVisibility(0);
									}
								}	
							});
							timePickerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										if(timing.isChecked()){ 
											timerMode = 0;
											timer_display.setText(R.string.timing);
										}
										else{
											timerMode = 1;
											c1.set(Calendar.HOUR_OF_DAY,tp.getCurrentHour());
											c1.set(Calendar.MINUTE, tp.getCurrentMinute());
											c2.setTimeInMillis(System.currentTimeMillis());
											try{
												if((c1.getTimeInMillis()/1000/60 - c2.getTimeInMillis()/1000/60)<1)
													throw new Exception();
												timer_display.setText("Quiz time:" + (c1.getTimeInMillis()/1000/60 - c2.getTimeInMillis()/1000/60) + "min");
											}
											catch(Exception e){
												Toast.makeText(getActivity(), "Wrong time setting", Toast.LENGTH_LONG).show();
											}
										}
										
									}
								});
							timePickerDialog.setNegativeButton("Cancel", null).show();
							
					
						}
					});
				}
				else{
					timer.setEnabled(false);
					timer.setAlpha((float)0.8);
					withTimer = false;
					
				}
			}
		});
		point.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				View pointXML = inflater.inflate(R.layout.dialog_point,null);
				try {
					pointIn = (EditText)pointXML.findViewById(R.id.pointIn);
				} 
				catch (InflateException e) {

				}
				Builder pointDialog = new AlertDialog.Builder(getActivity())
				.setTitle("Point")
				.setMessage("Please enter the point for a question")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(pointXML)
				.setCancelable(false);
				pointDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
							point_var = Integer.parseInt(pointIn.getText().toString());
							point_display.setText(pointIn.getText());
							
					}
					
				});
				pointDialog.setNegativeButton("Cancel", null).show();
				
			}
			
		});
		return v;
	}
	public Bundle getData(){
		bd = new Bundle();
		bd.putInt("quantity",quantity_var);
		bd.putInt("timerMode", timerMode);
		bd.putBoolean("withTimer", withTimer);
		bd.putInt("point", point_var);
		bd.putLong("Timer",c1.getTimeInMillis());
		
		
		return bd;
	}
}
		


