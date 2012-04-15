package tutorials.android.notifications;

import java.text.DecimalFormat;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	NotificationHelper notificationHelper;
	Button startBtn;
	Button stopBtn;
	int completed = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		startBtn = (Button) findViewById(R.id.startBtn);
		startBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				//lets get a random number which we will use as the ID of our notification
				Random rnd = new Random();
				int rndNumForId = rnd.nextInt((200 + 1) - 1) + 1;
				//we will pass the context and our random notification ID to the helper class constructor, our notification object will be created on the UI thread
				notificationHelper = new NotificationHelper(getApplicationContext(), rndNumForId);
				
				//lets start our long running process
				new LongRunningTask().execute();

			}
		});

	}

	public class LongRunningTask extends AsyncTask<String, Void, Void> {

		protected void onPreExecute() {

			//Since this is the UI thread we can disable our button so that it is not pressed again!
			startBtn.setEnabled(false);			
			completed = 0;
			//Create our notification via the helper class
			notificationHelper.createNotification();

		}

		@Override
		protected Void doInBackground(final String... args) {

			//In real life we would be doing something a bit more interesting here but this should suffice
			for (int x = 0; x < 10; x++) {

				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				completed += 10;
				
				//lets call our onProgressUpdate() method which runs on the UI thread
				
				publishProgress();
			}

			return null;
		}

		protected void onProgressUpdate(Void... v) {

			//lets format a string from the the 'completed' variable
			double dProgress = (double) completed;
			DecimalFormat twoDForm = new DecimalFormat("#.##");
			Double twodp = Double.valueOf(twoDForm.format(dProgress));
			Log.d(String.valueOf(completed), String.valueOf(twodp));

			//update notification via the helper on the UI thread
			notificationHelper.progressUpdate(twodp);
			

		}

		protected void onPostExecute(final Void result) {

			//this should be self explanatory
			notificationHelper.completed();
			startBtn.setEnabled(true);

		}
	}

}