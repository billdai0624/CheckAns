package com.example.finals;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Result extends Activity implements OnClickListener{
	private TextView[] questNumTv;
	private int[] questNumId = {R.id.quest_num1,R.id.quest_num2,R.id.quest_num3,R.id.quest_num4,R.id.quest_num5,R.id.quest_num6,R.id.quest_num7,R.id.quest_num8,R.id.quest_num9,R.id.quest_num10};
	private TextView[] userAnsTv;
	private int[] userAnsId = {R.id.user_ans1,R.id.user_ans2,R.id.user_ans3,R.id.user_ans4,R.id.user_ans5,R.id.user_ans6,R.id.user_ans7,R.id.user_ans8,R.id.user_ans9,R.id.user_ans10};
	private TextView[] correctAnsTv;
	private int[] correctAnsId = {R.id.correct_ans1,R.id.correct_ans2,R.id.correct_ans3,R.id.correct_ans4,R.id.correct_ans5,R.id.correct_ans6,R.id.correct_ans7,R.id.correct_ans8,R.id.correct_ans9,R.id.correct_ans10};
	private ImageView[] iv;
	private int[] ivId = {R.id.check1,R.id.check2,R.id.check3,R.id.check4,R.id.check5,R.id.check6,R.id.check7,R.id.check8,R.id.check9,R.id.check10};
	private TextView spentTime;
	private TextView totalPoints;
	private int correctId = R.drawable.correct;
	private int incorrectId = R.drawable.incorrect;
	private Button prev;
	private Button next;
	private Button exit;
	private Bundle bun;
	private int timerMode;
	private int quantity;
	private int point;
	private boolean pointFlag = true;
	private int totalPoint = 0;
	private int lowerBound = 0;
	private int upperBound = 10;
	private String[] userAns;
	private String[] correctAns;
	private long min,sec;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.result);
		questNumTv = new TextView[10];
		userAnsTv = new TextView[10];
		correctAnsTv = new TextView[10];
		iv = new ImageView[10];
		init();
		compare();
		
		super.onCreate(savedInstanceState);
	}
	
	private void init(){
		for(int i=0;i<10;i++){
			questNumTv[i] = (TextView)findViewById(questNumId[i]);
			userAnsTv[i] = (TextView)findViewById(userAnsId[i]);
			correctAnsTv[i] = (TextView)findViewById(correctAnsId[i]);
			iv[i] = (ImageView)findViewById(ivId[i]);
		}
		bun = new Bundle();
		bun = this.getIntent().getExtras();
		spentTime = (TextView)findViewById(R.id.spentTime);
		totalPoints = (TextView)findViewById(R.id.totalPoint);
		min = bun.getLong("min");
		sec = bun.getLong("sec");
		point = bun.getInt("point");
	//	if(quantity%10!=0)
	//		quantity = quantity + (10-(quantity % 10));
		timerMode = bun.getInt("timerMode");
		if(bun.getBoolean("withTimer")==false)
			spentTime.setText("Result");
		else{
			if(timerMode==0)
				spentTime.setText("You spent " + min + "min " + sec + "sec.");
			else
				if(min== 0 && sec == 0)
					spentTime.setText("You don't finish it in time!");
				else
					spentTime.setText("You finish it in time!");
		}
		prev = (Button)findViewById(R.id.prev);
		prev.setOnClickListener(this);
		next = (Button)findViewById(R.id.next);
		next.setOnClickListener(this);
		exit = (Button)findViewById(R.id.exitbt);
		exit.setOnClickListener(this);
		quantity = bun.getInt("quantity");
		userAns = new String[quantity];
		correctAns = new String[quantity];
		userAns = bun.getStringArray("userAns");
		correctAns = bun.getStringArray("correctAns");
		

	}
	
	private void compare(){
		int temp;
		if(upperBound>quantity)
			temp = quantity;
		else
			temp = upperBound;
		for(int i=0;lowerBound<temp;lowerBound++,i++){
			userAnsTv[i].setText(userAns[lowerBound]);
			correctAnsTv[i].setText(correctAns[lowerBound]);
			if(userAns[lowerBound].equals(correctAns[lowerBound])){	
				iv[i].setImageResource(correctId);
				totalPoint += point;
			}
			else{
				userAnsTv[i].setTextColor(0xFF000000);
				iv[i].setImageResource(incorrectId);
			}
		}
		if(pointFlag)
			totalPoints.setText("Get " + Integer.toString(totalPoint) + " points");
		lowerBound = upperBound;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.exitbt:
				finish();
				break;
			case R.id.prev:
				//if(lowerBound>upperBound)
			//		lowerBound = upperBound;
				if(lowerBound-10<=0){
					break;
				}
				else{
				/*	if(upperBound%10==0)
						upperBound -= 10;
					else
						upperBound -= upperBound % 10;*/
					lowerBound -= 20;
					upperBound -= 10;
					for(int i=0,l=lowerBound,u=upperBound;l<u;l++,i++){
						questNumTv[i].setText(Integer.toString(l+1));
					}
					compare();
				}
				break;
			case R.id.next:
				if(lowerBound>=quantity){
					break;
				}
				else{
					upperBound += 10;
					for(int i=0,l=lowerBound,u=upperBound;l<u;l++,i++){
						questNumTv[i].setText(Integer.toString(l+1));
					}
					compare();
					if(lowerBound>=quantity && pointFlag){
						pointFlag = false;
					}
				}
				break;
		}
		
	}
}
	
