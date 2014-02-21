/**
 * 
 */
package com.frasersharp.arduinobluetooth;

/**
 * Class for connecting to, sending data to, and receiving data from an arduino bluetooth serial device.
 * 
 * @author Fraser Sharp
 * @version 0.1, 21/02/2014
 *
 */
public class BluetoothSerial {
	
	private String macAddress;
	private int available;
	private int baudRate;
	
	public BluetoothSerial() {
		
	}
	
	/**
	 * @param MacAddress MAC Address of the Arduino bluetooth device.
	 */
	public BluetoothSerial(String MacAddress) {
		
	}
	
	/**
	 * @param MacAddress MAC Address of the Arduino bluetooth device.
	 * @param BaudRate Baud rate for the connection
	 */
	public BluetoothSerial(String MacAddress, int BaudRate) {
		
	}
	
	/**
	 * Attempt to establish serial connection
	 * 
	 * @throws BluetoothSerialConnectionException
	 */
	public void connect() throws BluetoothSerialConnectionException {
		
	}
	
	/**
	 * Attempt to close serial connection
	 * 
	 * @throws BluetoothSerialConnectionException
	 */
	public void disconnect() throws BluetoothSerialConnectionException {
	
	}
	
	/**
	 * Sends a char to the Arduino
	 * 
	 * @param toWrite char to write
	 */
	public void write(char toWrite) {
		
	}
	
	/**
	 * Sends an int to the Arduino
	 * 
	 * @param toWrite int to write
	 */
	public void write(int toWrite) {
		
	}
	
	/**
	 * Sends a byte to the Arduino
	 * 
	 * @param toWrite byte to write
	 */
	public void write(byte toWrite) {
		
	}
	
	/**
	 * Sends a String to the Arduino, and terminates it with a newline
	 * 
	 * @param toWrite String to write
	 */
	public void writeLine(String toWrite) {
		
	}
	
	/**
	 * Read a char from the Arduino
	 * 
	 * @return char from Arduino
	 */
	public char readChar() {
		return 0;
	}
	
	/**
	 * Read an int from the Arduino
	 * 
	 * @return int from Arduino
	 */
	public int readInt() {
		return 0;
	}
	
	/**
	 * Read a byte from the Arduino
	 * 
	 * @return byte from Arduino
	 */
	public byte readByte() {
		return 0;
	}
	
	/**
	 * Read a line from the Arduino
	 * 
	 * @return String line from Arduino
	 */
	public String readLine() {
		return "";
	}
	
	/**
	 * Read a number of chars from the Arduino
	 * 
	 * @param numberToRead Number of chars to read
	 * @return Array of chars taken from the arduino
	 */
	public char[] readNChars (int numberToRead) {
		return null;
	}
	
	
	/**
	 * Flush the serial buffer
	 */
	public void flush() {
		
	}

	/**
	 * @return The MAC address of the Arduino bluetooth device.
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @param macAddress The MAC address of the Arduino bluetooth device
	 */
	public void setMacAddress(String macAddress_) {
		macAddress = macAddress_;
	}

	/**
	 * Get the number of bytes available to be read from the Arduino
	 * 
	 * @return Bytes available
	 */
	public int available() {
		return available;
	}

	/**
	 * @return the baudRate
	 */
	public int getBaudRate() {
		return baudRate;
	}

	/**
	 * @param baudRate the baudRate to set
	 */
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
}
