package inbm.get.runningapp.info;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class GetInfo extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_info);
		TextView text1 = (TextView) findViewById(R.id.text1);
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(GetInfo.this, AppList.class);
				startActivity(intent1);
			}

		});

		Timer timer = new Timer();
		MyTask myTask = new MyTask();
		timer.schedule(myTask, 1000, 1000);

		String str = null;
		try {

			ActivityManager am = (ActivityManager) this
					.getSystemService(Context.ACTIVITY_SERVICE);

			List<RunningTaskInfo> taskInfo = am.getRunningTasks(5);

			ComponentName topActivity = taskInfo.get(1).topActivity;

			str = topActivity.getPackageName();

		}

		catch (Exception e)

		{

			text1.setText(e.toString());

		}

		text1.setText(str);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_get_info, menu);
		return true;
	}

	class MyTask extends TimerTask {
		public void run() {

			ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

			List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
			Log.i("ClassName",
					"Class Name: " + taskInfo.get(0).topActivity.getClassName());
			if (taskInfo.get(0).topActivity.getPackageName() != "inbm.get.runningapp.info") {
				Log.i("ClassName", "another app");
				
				
				
				ComponentName name=new ComponentName("inbm.subakc.test", "inbm.subakc.test.Intro");
Intent i=new Intent(Intent.ACTION_MAIN);

i.addCategory(Intent.CATEGORY_LAUNCHER);
i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);


i.setComponent(name);

getApplication().startActivity(i);

				
				
			}
		}
	}
}

