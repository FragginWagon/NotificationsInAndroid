package tutorials.android.notifications;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AnotherActivity extends Activity {

	Button closeBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.another);

		Toast.makeText(this, "Activity started from Notifications Pending Intent!", Toast.LENGTH_LONG).show();
		
		closeBtn = (Button)findViewById(R.id.closeBtn);
		closeBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				finish();
				
			}
		});
		
	}

}