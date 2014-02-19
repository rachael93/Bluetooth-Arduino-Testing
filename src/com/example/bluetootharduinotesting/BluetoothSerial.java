package com.example.bluetootharduinotesting;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

public class BluetoothSerial {

	private String MACaddress = "00:13:12:13:61:53";
	private BluetoothAdapter adapter = null;
	private BluetoothSocket socket = null;
	private InputStream inStream = null;
	private OutputStream outStream = null;

	public String getMACaddress() {
		return MACaddress;
	}

	public void setMACaddress(String mACaddress) {
		MACaddress = mACaddress;
	}

	public BluetoothSerial() {
		connectDevice();
		new Thread(new Runnable() {
			public void run() {
				String buffer = "";
				
				try {
					inStream  = socket.getInputStream();
					outStream = socket.getOutputStream();
				} catch (IOException e) {
					Log.e("crappybtcode", "FAILED TO GET INPUTSTREAM FROM SOCKET");
				}

				Log.d("crappybtcode", "BEGINNING TO LISTENING");
				while (true) {
					
//					Log.d("crappybtcode","writing to stream");
//					try {
//						outStream.write(43);
//						Thread.sleep(500);
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
					try {
						if (inStream.available() > 0) {
							char in = (char)inStream.read();
							if (in=='\n') {
								Log.i("crappybtcode", "Received:  "+buffer);
								buffer = "";
							} else {
								buffer += in;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}



	private void connectDevice() {
		
		adapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothDevice device = null;
		
		try {
			Log.d("crappybtcode", "Should be connecting to " + MACaddress);
			 device = adapter.getRemoteDevice(MACaddress);
			adapter.cancelDiscovery();
		} catch (NullPointerException e) {
			Log.e("crappybtcode", "HALP");
		}
		
		try {
			socket = device.createRfcommSocketToServiceRecord(UUID
					.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			socket.connect();
			Log.d("crappybtcode", "NOTHING HAS GONE WRONG SOMEHOW");
		} catch (IOException e) {
			Log.d("crappybtcode", "EVERYTHING HAS GONE WRONG SOMEHOW");
		}
	}
}
