package tutorials.android.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationHelper {
	
   
    private int ID;
    private Notification notification;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private CharSequence ContentTitle;
    private Context context;
    
    public NotificationHelper(Context context, int id)
    {
    
    	this.ID = id;
        this.context = context;
    }

    public void createNotification() {
    	
        //Get an instance of Notification Manager
    	notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        /*Notification text and icon for the notification is set here and then finally we instantiate 
          a new Notification object passing in the data we defined previously
        */
        
    	CharSequence initialMenuBarText = "Start of Task";
        int iconToShow = android.R.drawable.stat_sys_download;
        long when = System.currentTimeMillis();
        notification = new Notification(iconToShow, initialMenuBarText, when);

        //We get full control over the title and subtitle that is displayed in the pulldown notification area
        
        // This is the title of the notification
        ContentTitle = "Long runnin process!"; 
        //This is the text below the title which we update in progressUpdate() (see below function)
        CharSequence contentText = "Our task has started"; 

        //We can set up a pending intent on the notification so that once clicked we carry out some other action. We will call another activity
        //First we create an intent object which hold our existing context and the activity that we want to open
        Intent notificationIntent = new Intent(context, AnotherActivity.class);
        
        //We set a flag on our intent: 'FLAG_ACTIVITY_SINGLE_TOP' so that if our notification in the notification area is pressed more than once, only one instance of AnotherActivity will ever be opened. 
        //Without this flag if the notification was clicked and then clicked again our activity will not be opened thus keeping the activity stack tidy
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        //add the titles and pending intent to our notification object
        notification.setLatestEventInfo(context, ContentTitle, contentText, pendingIntent);

        //We update the notification here by passing our new notification and our unique notification ID to the Notification Manager
        notificationManager.notify(ID, notification);
    }

	// We call this function within the onProgressUpdate() of our running Async Task. Essentially we pass to it the percentage complete of our long running operation and then notify our Notification.

    public void progressUpdate(double percentageComplete) {
    	
    	 
        CharSequence contentText = "Task is: " + percentageComplete + "% complete";
        notification.setLatestEventInfo(context, ContentTitle, contentText, pendingIntent);
        notificationManager.notify(ID, notification);
        
    }

	//We call this function from the onPostExecute() of our Async Task to tell the user and the notification manager that our long running process is compelted

    public void completed()    {
    	
    	//Lets display a toast so the user knows the long running process has finished
    	Toast.makeText(context, "Long running process COMPLETE!", Toast.LENGTH_LONG).show();
    	notificationManager.cancel(ID);

    }
}