package com.example.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener{

	private Handler mHandler = new Handler();
	EditText textOut;
	TextView textIn;
	
	private void setText(final String message){
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				textIn.setText(textIn.getText()+message);
			}
		});
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textOut = (EditText) findViewById(R.id.textout);
		Button buttonSend = (Button) findViewById(R.id.send);
		textIn = (TextView) findViewById(R.id.textin);
		buttonSend.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View arg0) {

				new AsyncTask<String, Void, String>() {

					/**
					 * doInBackground is executed on a background thread.
					 */
					@Override
					protected String doInBackground(String... params) {

						Socket socket = null;
						DataOutputStream dataOutputStream = null;
						DataInputStream dataInputStream = null;

						try {
							socket = new Socket("192.168.0.126", 9080);
							dataOutputStream = new DataOutputStream(socket
									.getOutputStream());
							dataInputStream = new DataInputStream(socket
									.getInputStream());
							dataOutputStream.writeUTF(textOut.getText()
									.toString());

							setText(dataInputStream.readUTF());
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							if (socket != null) {
								try {
									socket.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							if (dataOutputStream != null) {
								try {
									dataOutputStream.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							if (dataInputStream != null) {
								try {
									dataInputStream.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
						return null;
					}

				}.execute();

			}

		});
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
