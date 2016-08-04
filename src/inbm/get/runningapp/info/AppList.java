package inbm.get.runningapp.info;

import java.util.Collections;
import java.util.List;

import android.app.ActivityManager;
import android.app.ListActivity;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AppList extends ListActivity {

	/** Called when the activity is first created. */

	/***
	 * Copyright (c) 2008-2012 CommonsWare, LLC Licensed under the Apache
	 * License, Version 2.0 (the "License"); you may not use this file except in
	 * compliance with the License. You may obtain a copy of the License at
	 * http://www.apache.org/licenses/LICENSE-2.0. Unless required by applicable
	 * law or agreed to in writing, software distributed under the License is
	 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
	 * KIND, either express or implied. See the License for the specific
	 * language governing permissions and limitations under the License.
	 * 
	 * From _The Busy Coder's Guide to Advanced Android Development_
	 * http://commonsware.com/AdvAndroid
	 */
	ActivityManager am;
	AppAdapter adapter = null;
	List<RunningTaskInfo> taskInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		/*
		 * PackageManager pm=getPackageManager(); Intent main=new
		 * Intent(Intent.ACTION_MAIN, null);
		 * 
		 * main.addCategory(Intent.CATEGORY_LAUNCHER);
		 * 
		 * List<ResolveInfo> launchables=pm.queryIntentActivities(main, 0);
		 */
		am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);

		taskInfo = am.getRunningTasks(20);

		adapter = new AppAdapter(taskInfo);
		setListAdapter(adapter);
	}

	class AppAdapter extends ArrayAdapter<RunningTaskInfo> {
		// private PackageManager pm=null;

		AppAdapter(List<RunningTaskInfo> apps) {
			super(AppList.this, R.layout.row, apps);
			// this.pm=pm;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = newView(parent);
			}

			bindView(position, convertView);

			return (convertView);
		}

		private View newView(ViewGroup parent) {
			return (getLayoutInflater().inflate(R.layout.row, parent, false));
		}

		private void bindView(int position, View row) {

			TextView label = (TextView) row.findViewById(R.id.label);

			ComponentName topActivity = getItem(position).topActivity;

			// String str = topActivity.getPackageName();

			String str = topActivity.getClassName();
			label.setText(str);

			// ImageView icon=(ImageView)row.findViewById(R.id.icon);

			// icon.setImageDrawable(getItem(position).loadIcon(pm));
		}
	}

	// TODO Auto-generated method stub

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*
		 * ResolveInfo launchable=adapter.getItem(position); ActivityInfo
		 * activity=launchable.activityInfo; ComponentName name=new
		 * ComponentName(activity.applicationInfo.packageName, activity.name);
		 * Intent i=new Intent(Intent.ACTION_MAIN);
		 * 
		 * i.addCategory(Intent.CATEGORY_LAUNCHER);
		 * i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
		 * Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED); i.setComponent(name);
		 * 
		 * startActivity(i);
		 */

		// Toast.makeText(AppList.this, "list item clicked",
		// Toast.LENGTH_SHORT);
		ComponentName topActivity = taskInfo.get(position).topActivity;

		String packageName = topActivity.getPackageName();
		String className = topActivity.getClassName();
		
		
		Log.i("Dictionary", "class name: " + className);

		ComponentName name = new ComponentName(packageName, className);
		Intent i = new Intent(Intent.ACTION_MAIN);

		i.addCategory(Intent.CATEGORY_LAUNCHER);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		i.setComponent(name);

		startActivity(i);

	}
}
