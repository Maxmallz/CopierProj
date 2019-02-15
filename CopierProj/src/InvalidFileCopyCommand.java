
public class InvalidFileCopyCommand extends Exception{
	private String msg;
	
	public InvalidFileCopyCommand(String message) {
		this.msg = message;
	}
	
	@Override
	public String getMessage() {
		return this.msg;
	}
}
