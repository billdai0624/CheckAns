package com.example.finals;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends Fragment implements OnClickListener{
	private LinearLayout screen;
	private EditText questJump;
	private TextView timer;
	private TextView currentQuest;
	private TextView totalQuest;
	private TextView urAnswer_statement;
	private TextView answer;
	private Button pause;
	private ImageView left;
	private ImageView right;
	private Button A;
	private Button B;
	private Button C;
	private Button D;
	private Button E;
	private Button F;
	private Button submit;
	private Bundle bd;
	private Bundle give;
	private int timerMode;
	private int i = 1;
	private long startTime;
	private long finishTime;
	private long min,sec;
	private Handler hd = new Handler();
	private Calendar c1 = Calendar.getInstance();
	private boolean flag = false;
	private boolean isChanged = false;
	private String[] userAns;
	private String[] trueAnswer;
	private String a,b,c,d,e,f,currentAnswer;
	@Override 
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.quiz,container,false);
		bd = getArguments();
		screen = (LinearLayout)v.findViewById(R.id.LinearLayout1);
		timer = (TextView)v.findViewById(R.id.timer);
		currentQuest = (TextView)v.findViewById(R.id.currentQuest);
		totalQuest = (TextView)v.findViewById(R.id.totalQuest);
		urAnswer_statement = (TextView)v.findViewById(R.id.textView4);
		answer = (TextView)v.findViewById(R.id.answer);
		pause = (Button)v.findViewById(R.id.pause);
		left = (ImageView)v.findViewById(R.id.left);
		right = (ImageView)v.findViewById(R.id.right);
		userAns = new String[bd.getInt("quantity")];
		trueAnswer = new String[bd.getInt("quantity")];
		A = (Button)v.findViewById(R.id.A);
		a = "";
		A.setOnClickListener(this);
		B = (Button)v.findViewById(R.id.B);
		b = "";
		B.setOnClickListener(this);
		C = (Button)v.findViewById(R.id.C);
		c = "";
		C.setOnClickListener(this);
		D = (Button)v.findViewById(R.id.D);
		d = "";
		D.setOnClickListener(this);
		E = (Button)v.findViewById(R.id.E);
		e = "";
		E.setOnClickListener(this);
		F = (Button)v.findViewById(R.id.F);
		f = "";
		F.setOnClickListener(this);
		currentAnswer = a + b + c + d + e + f;
		submit = (Button)v.findViewById(R.id.submit);
		submit.setOnClickListener(this);
		totalQuest.setText(Integer.toString(bd.getInt("quantity")));
		timerMode = bd.getInt("timerMode");
		startTime = System.currentTimeMillis();
		finishTime = bd.getLong("Timer") + bd.getLong("delta");
		c1.setTimeInMillis(0);
		for(int j=0;j<bd.getInt("quantity");j++)
			userAns[j] = "";
		for(int j=0;j<bd.getInt("quantity");j++)
			trueAnswer[j] = " ";
		currentQuest.setOnClickListener(new OnClickListener(){
			

			@Override
			public void onClick(View v) {
				
				View questJumpXML = inflater.inflate(R.layout.dialog_quest_jump,null);
				
				try {
					//questJumpXML = inflater.inflate(R.layout.dialog_input,null);
					questJump = (EditText)questJumpXML.findViewById(R.id.questJump);
				} 
				catch (InflateException e) {

				}
				Builder questJumpDialog = new AlertDialog.Builder(getActivity())
				.setTitle("Question jump")
				.setMessage("Please enter the question number")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(questJumpXML)
				.setCancelable(true);
				questJumpDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try{
							int k = Integer.parseInt(questJump.getText().toString());
							if(k>bd.getInt("quantity") || k<=0)
								throw new Exception();
							i = k;
							currentQuest.setText(Integer.toString(i));
							answer.setText(trueAnswer[i-1]);
							a = b = c = d = e = f = currentAnswer = "";
						
						}
						catch(Exception e){
							Toast.makeText(getActivity(), "The question number is out of bound",Toast.LENGTH_LONG).show();
						}
					}
					
				});
				questJumpDialog.setNegativeButton("Cancel", null).show();
			}
		});
		
		
		if(bd.getBoolean("withTimer")){
			if(timerMode==0)
				hd.post(updateTimer);
			else
				hd.post(countdown);
		}
		else{
			timer.setVisibility(8);
			pause.setVisibility(4);
		}
		pause.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				if(flag==false){
					pause.setText("Resume");
					A.setClickable(false);
					B.setClickable(false);
					C.setClickable(false);
					D.setClickable(false);
					E.setClickable(false);
					F.setClickable(false);
					flag = true;
				if(timerMode==0){
					
					finishTime = System.currentTimeMillis();
					hd.removeCallbacks(updateTimer);
				}
				else{
					c1.setTimeInMillis(System.currentTimeMillis());
					hd.removeCallbacks(countdown);
				}
				}
				else{
					pause.setText("Pause");
					A.setClickable(true);
					B.setClickable(true);
					C.setClickable(true);
					D.setClickable(true);
					E.setClickable(true);
					F.setClickable(true);
					flag = false;
					if(timerMode==0){
						c1.setTimeInMillis(c1.getTimeInMillis() + (System.currentTimeMillis() - finishTime));
						//startTime = System.currentTimeMillis() - c.getTimeInMillis();
						hd.post(updateTimer);
					}
					else{
						finishTime += (System.currentTimeMillis() - c1.getTimeInMillis());
						hd.post(countdown);
					}
				}
			}
			
		});
		
		final GestureDetector gd = new GestureDetector(getActivity(),new GestureDetector.OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				if (e1.getX() - e2.getX() > 60 && Math.abs(velocityX) > 0) {
				// Fling left
					if(i==bd.getInt("quantity")){
						
							inquire();
					}
					if(i<bd.getInt("quantity")){
						//userAns[i-1] = currentAnswer;
						//answer.setText(userAns[i-1]);
						i++;
						currentQuest.setText(Integer.toString(i));
						answer.setText(trueAnswer[i-1]);
						a = b = c = d = e = f = currentAnswer = "";
					}
					
				} else if (e2.getX() - e1.getX() > 60 && Math.abs(velocityX) > 0) {
				// Fling right
					if(i>1){
						//userAns[i-1] = currentAnswer;
						//answer.setText(userAns[i-1]);
						i--;
						currentQuest.setText(Integer.toString(i));
						answer.setText(trueAnswer[i-1]);
						a = b = c = d = e = f = currentAnswer = "";	        
					}
				}
					
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				
				return false;
			}
		});		
		
		screen.setClickable(true);
		screen.setLongClickable(true);
		screen.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				return gd.onTouchEvent(event);
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		return v;
	}
	private Runnable updateTimer = new Runnable(){

		@Override
		public void run() {
			Long spentTime1 = System.currentTimeMillis() - c1.getTimeInMillis() - startTime;
			           //計算目前已過分鐘數
			             min = (spentTime1/1000)/60;
			           //計算目前已過秒數
			             sec = (spentTime1/1000) % 60;
			            timer.setText(min + "min : " + sec + "s");
			           hd.postDelayed(this, 1000);
			
		}
		
	};
	private Runnable countdown = new Runnable(){

		@Override
		public void run() {
			
			Long spentTime2 = finishTime - System.currentTimeMillis();
			if(spentTime2<=0){
				timer.setTextColor(0xFFFF0000);
				A.setClickable(false);
				B.setClickable(false);
				C.setClickable(false);
				D.setClickable(false);
				E.setClickable(false);
				F.setClickable(false);
				return;
			}
			
			           //計算目前已過分鐘數
			            min = (spentTime2/1000)/60;
			           //計算目前已過秒數
			            sec = (spentTime2/1000) % 60;
			            timer.setText(min + "min : " + sec + "s");
			           hd.postDelayed(this, 1000);
			
		}
		
	};

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
			case R.id.A:
				if(a=="A")
					a = "";
				else
					a = "A";
				break;
			case R.id.B:
				if(b=="B")
					b = "";
				else
					b = "B";
				break;
			case R.id.C:
				if(c=="C")
					c = "";
				else
					c = "C";
				break;
			case R.id.D:
				if(d=="D")
					d = "";
				else
					d = "D";
				break;
			case R.id.E:
				if(e=="E")
					e = "";
				else
					e = "E";
				break;
			case R.id.F:
				if(f=="F")
					f = "";
				else
					f = "F";
				break;
			case R.id.submit:
					inquire();
				break;
			default:
		}
		currentAnswer = a + b + c + d + e + f;
		trueAnswer[i-1] = currentAnswer;
		answer.setText(currentAnswer);
	}
	public Bundle getData(){
		give = new Bundle();
		give.putStringArray("answer", userAns);
		return give;
	}
	private void inquire(){
		Builder builder = new AlertDialog.Builder(getActivity());
		if(isChanged==true)
			builder.setMessage("Have entered all correct answers?");
		else
			builder.setMessage("Have you answered all questions?");
		builder.setTitle("Submit");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(isChanged==false)
					changeForCheck();
				else{
					compare();
				}
			}
		});
		builder.setNegativeButton("No",null);
		builder.create().show();
	}
	
	private void changeForCheck(){
		isChanged = true;
		if(timerMode==0)
			hd.removeCallbacks(updateTimer);
		else{
			hd.removeCallbacks(countdown);
			A.setClickable(true);
			B.setClickable(true);
			C.setClickable(true);
			D.setClickable(true);
			E.setClickable(true);
			F.setClickable(true);
		}
		timer.setTextColor(0xFF000000);
		timer.setText("Please enter the correct answer");
		i = 1;
		
		pause.setVisibility(8);
		urAnswer_statement.setText("Correct answer");
		for(int i=0;i<bd.getInt("quantity");i++)
			userAns[i] = trueAnswer[i];
		//userAns = trueAnswer;
		for(int j=0;j<bd.getInt("quantity");j++)
			trueAnswer[j] = " ";
		a = b = c = d = e = f = currentAnswer = "";	
		currentQuest.setText(Integer.toString(i));
		answer.setText("");
		
	}
	private void compare(){
		Intent it = new Intent();
		it.setClass(getActivity(), Result.class);
		Bundle bun = new Bundle();
		bun.putStringArray("userAns",userAns);
		bun.putStringArray("correctAns", trueAnswer);
		bun.putInt("timerMode", timerMode);
		bun.putBoolean("withTimer", bd.getBoolean("withTimer"));
		bun.putLong("min", min);
		bun.putLong("sec", sec);
		bun.putInt("quantity", bd.getInt("quantity"));
		bun.putInt("point", bd.getInt("point"));
		((MainActivity)getActivity()).refreshSetting();
		it.putExtras(bun);
		startActivity(it);
		
	}
	
}