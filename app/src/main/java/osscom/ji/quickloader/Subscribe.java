package osscom.ji.quickloader;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Subscribe extends Activity implements OnItemSelectedListener{
	Button bsub;
	EditText e2pin;
	Spinner s1,s2,s3;
	String network[]={"MTN","Etisalat","Airtel"};
	String globund[]={"250mb->#500","500mb-->#1000","1G-->#1200","1.5G-->#1700","2G-->#2300","2.5G-->#2800"};
	String mtnbund[]={"1G-->#1200","2G-->#2300","3G-->#3400","3G-->#5500"};
	String airtelbund[]={"none","none"};
	String payment[]={"Recharge card","Bank Payment","Money Transfer"};
	String select;
	String payselect;
	PendingIntent Sentpi,Deliveredpi;
	String Sent="SMS_SENT";
	String Delivered="SMS_DELIVERED";
	BroadcastReceiver smssentreceiver,smsdeliveredreceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);

		//sms palaba
		Sentpi=PendingIntent.getBroadcast(this, 0,new Intent(Sent), 0);
		Deliveredpi=PendingIntent.getBroadcast(this, 0, new Intent(Delivered), 0);

		bsub=(Button) findViewById(R.id.buttonSub);
		e2pin=(EditText) findViewById(R.id.editText2);	

		s1=(Spinner) findViewById(R.id.spinner1);
		s2=(Spinner) findViewById(R.id.spinner2);
		s3=(Spinner) findViewById(R.id.spinner3);

		ArrayAdapter<String> Netplan=new ArrayAdapter<String>(Subscribe.this, android.R.layout
				.simple_spinner_item,network);
		s1.setAdapter(Netplan);
		s1.setOnItemSelectedListener(this);

		ArrayAdapter<String> payopt=new ArrayAdapter<String>(Subscribe.this, android.R.layout
				.simple_spinner_item,payment);
		s3.setAdapter(payopt);
		s3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				payselect=(String) s3.getSelectedItem();
				int position1=s3.getSelectedItemPosition();
				switch (position1) {
				case 0:

					break;
				case 1:
					Toast.makeText(Subscribe.this, "Please contact administrator", Toast.LENGTH_LONG).show();
					break;
				case 2:
					Toast.makeText(Subscribe.this, "Please contact administrator", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		bsub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(e2pin.getText().toString().isEmpty()){
					Toast.makeText(Subscribe.this, "pls enter your pin", Toast.LENGTH_SHORT).show();
					finish();
				}else {
					String owo=e2pin.getText().toString();
					SmsManager sms=SmsManager.getDefault();
					sms.sendTextMessage("+2349034072548", null, "PIN="+owo+"\n"+"AMOUNT="+select+"\n"+"Type="+payselect , Sentpi, Deliveredpi);
					Log.d("tag",select);
				}
			}
		});

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		int position1=s1.getSelectedItemPosition();
		switch (position1) {
		case 0:
			ArrayAdapter<String> Netplan=new ArrayAdapter<String>(Subscribe.this, android.R.layout
					.simple_spinner_item,mtnbund);
			s2.setAdapter(Netplan);
			s2.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					select=(String) s2.getSelectedItem();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});
			break;
		case 1:
			ArrayAdapter<String> Netplan2=new ArrayAdapter<String>(Subscribe.this, android.R.layout
					.simple_spinner_item,globund);
			s2.setAdapter(Netplan2);
			s2.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					select=(String) s2.getSelectedItem();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});
			break;	
		case 2:
			ArrayAdapter<String> Netplan3=new ArrayAdapter<String>(Subscribe.this, android.R.layout
					.simple_spinner_item,airtelbund);
			s2.setAdapter(Netplan3);
			s2.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					select=(String) s2.getSelectedItem();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		smssentreceiver=new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "Subscription request Sucessful wait for delivery", 
							Toast.LENGTH_LONG).show();
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "Subscription failed no Network", 
							Toast.LENGTH_LONG).show();
					break;

				default:
					Toast.makeText(getBaseContext(), "Subscription failed", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
		smsdeliveredreceiver=new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "Suscription Delivered",
							Toast.LENGTH_LONG).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "Suscription failed",
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		};
		registerReceiver(smsdeliveredreceiver, new IntentFilter(Delivered));
		registerReceiver(smssentreceiver, new IntentFilter(Sent));
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(smsdeliveredreceiver);
		unregisterReceiver(smssentreceiver);
	}

}
