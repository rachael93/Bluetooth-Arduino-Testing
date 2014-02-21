/**
 * 
 */
package com.frasersharp.arduinobluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
 * Class for connecting to, sending data to, and receiving data from an arduino
 * bluetooth serial device.
 * 
 * @author Fraser Sharp
 * @version 0.2, 21/02/2014
 * 
 */
public class BluetoothSerial {

	private static final String tag = "BluetoothSerial";
	private String macAddress;
	private int baudRate;
	private BluetoothAdapter adapter;
	private BluetoothSocket socket;
	private InputStream inStream;
	private OutputStream outStream;

	public BluetoothSerial() {
		adapter = null;
		socket = null;
		inStream = null;
		outStream = null;
	}

	/**
	 * @param MacAddress
	 *            MAC Address of the Arduino bluetooth device.
	 */
	public BluetoothSerial(String MacAddress) {
		macAddress = MacAddress;
		adapter = null;
		socket = null;
		inStream = null;
		outStream = null;
	}

	/**
	 * @param MacAddress
	 *            MAC Address of the Arduino bluetooth device.
	 * @param BaudRate
	 *            Baud rate for the connection
	 */
	public BluetoothSerial(String MacAddress, int BaudRate) {
		baudRate = BaudRate;
		macAddress = MacAddress;
		adapter = null;
		socket = null;
		inStream = null;
		outStream = null;
	}

	/**
	 * Attempt to establish serial connection
	 * 
	 * @throws BluetoothSerialConnectionException
	 */
	public void connect() throws BluetoothSerialConnectionException {
		Log.d(tag, "Attempting to establish Bluetooth connection...");
		adapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothDevice device = null;

		try {
			device = adapter.getRemoteDevice(macAddress);
			adapter.cancelDiscovery();
		} catch (NullPointerException e) {
			Log.e(tag, "Error getting remote device.");
			throw new BluetoothSerialConnectionException();
		}

		try {
			socket = device.createRfcommSocketToServiceRecord(UUID
					.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			socket.connect();
			Log.d(tag, "Connection succeful");
		} catch (IOException e) {
			Log.e(tag, "Failed to connect to socket");
			throw new BluetoothSerialConnectionException();
		}

		try {
			Log.d(tag, "Establishing data streams");
			inStream = socket.getInputStream();
			outStream = socket.getOutputStream();
			Log.d(tag, "Data streams estabilshed");
		} catch (IOException e) {
			Log.e(tag, "Error setting up data streams.");
			throw new BluetoothSerialConnectionException();
		}
	}

	/**
	 * Attempt to close serial connection
	 * 
	 * @throws BluetoothSerialConnectionException
	 */
	public void disconnect() throws BluetoothSerialConnectionException {
		try {
			inStream.close();
			outStream.close();
			socket.close();
		} catch (IOException e) {
			throw new BluetoothSerialConnectionException();
		} catch (NullPointerException e) {
			Log.e(tag, "No connection to close.");
		}
	}

	/**
	 * Sends a char to the Arduino
	 * 
	 * @param toWrite
	 *            char to write
	 */
	public void write(char toWrite) {
		try {
			outStream.write(toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends an int to the Arduino
	 * 
	 * @param toWrite
	 *            int to write
	 */
	public void write(int toWrite) {
		try {
			outStream.write(toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a byte to the Arduino
	 * 
	 * @param toWrite
	 *            byte to write
	 */
	public void write(byte toWrite) {
		try {
			outStream.write(toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a String to the Arduino, and terminates it with a newline
	 * 
	 * @param toWrite
	 *            String to write
	 */
	public void writeLine(String toWrite) {
		try {
			outStream.write(toWrite.getBytes("UTF-8"));
			// DRASTIC - may need newline here
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read a char from the Arduino
	 * 
	 * @return char from Arduino
	 * @throws IOException
	 */
	public char readChar() throws IOException {
		if (inStream.available() > 0) {
			char out = (char) inStream.read();
			return out;
		} else {
			Log.e(tag, "Nothing to read");
			throw new IOException();
		}
	}

	/**
	 * Read an int from the Arduino
	 * 
	 * @return int from Arduino
	 * @throws IOException
	 */
	public int readInt() throws IOException {
		if (inStream.available() > 0) {
			int out = (int) inStream.read();
			return out;
		} else {
			Log.e(tag, "Nothing to read");
			throw new IOException();
		}
	}

	/**
	 * Read a byte from the Arduino
	 * 
	 * @return byte from Arduino
	 * @throws IOException
	 */
	public byte readByte() throws IOException {
		if (inStream.available() > 0) {
			byte out = (byte) inStream.read();
			return out;
		} else {
			Log.e(tag, "Nothing to read");
			throw new IOException();
		}
	}

	/**
	 * Read a line from the Arduino
	 * 
	 * @return String line from Arduino
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		String out = "";
		Boolean linedone = false;

		while (!linedone) {
			if (inStream.available() > 0) {
				char in = (char) inStream.read();
				if (in == '\n') {
					linedone = true;
				} else {
					out += in;
				}
			} else {
				Log.w(tag, "Line ended unexpectedly");
				linedone = true;
			}
		}

		return out;
	}

	/**
	 * Read a number of chars from the Arduino
	 * 
	 * @param numberToRead
	 *            Number of chars to read
	 * @return Array of chars taken from the arduino
	 * @throws IOException
	 */
	public char[] readNChars(int numberToRead) throws IOException {

		if (inStream.available() > 0) {

			if (inStream.available() > numberToRead) {
				char[] out = new char[numberToRead];
				for (int i = 0; i < numberToRead; i++) {
					out[i] = (char) inStream.read();
				}
				return out;
			} else {
				Log.e(tag,
						"Not enough bytes available to read.  Reading in what is availble.");
				char[] out = new char[inStream.available()];
				for (int i = 0; i < inStream.available(); i++) {
					out[i] = (char) inStream.read();
				}
				return out;
			}
		} else {
			Log.e(tag, "Nothing to read");
			throw new IOException();
		}
	}

	/**
	 * Flush the serial buffer
	 */
	public void flush() {
		try {
			outStream.flush();
		} catch (IOException e) {
			Log.e(tag, "Unable to flush buffer.  Are you connected?");
		}
	}

	/**
	 * @return The MAC address of the Arduino bluetooth device.
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @param macAddress
	 *            The MAC address of the Arduino bluetooth device
	 */
	public void setMacAddress(String macAddress_) {
		macAddress = macAddress_;
	}

	/**
	 * Get the number of bytes available to be read from the Arduino
	 * 
	 * @return Bytes available
	 * @throws IOException
	 */
	public int available() throws IOException {
		return inStream.available();
	}

	/**
	 * @return the baudRate
	 */
	public int getBaudRate() {
		return baudRate;
	}

	/**
	 * @param baudRate
	 *            the baudRate to set
	 */
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
}
