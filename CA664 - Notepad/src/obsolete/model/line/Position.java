package obsolete.model.line;

public class Position {
	public int lineNo;
	public int charNo;
	
	public Position() {
		this.lineNo = 0;
		this.charNo = 0;
	}
	
	public Position(int lineNo, int charNo) {
		this.lineNo = lineNo;
		this.charNo = charNo;
	}
}
