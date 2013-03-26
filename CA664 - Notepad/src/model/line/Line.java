package model.line;

public class Line {
	private static final int CAP = 200;
	public char[] chars;
	private int logLength;
	
	public Line() {
		chars = new char[CAP];
		logLength = 0;
	}
	
	public Line(String input) {
		chars = new char[CAP];
		char[] inputArray = input.toCharArray();
		logLength = inputArray.length;
		for (int i = 0; i < logLength; i++) {
			chars[i] = inputArray[i];
		}
	}
	
	public int getLength() {
		return logLength;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder() ;
		for (int i = 0; i < logLength; i++) {
			sb.append(chars[i]);
		}	
		
		return sb.toString();
	}
}
