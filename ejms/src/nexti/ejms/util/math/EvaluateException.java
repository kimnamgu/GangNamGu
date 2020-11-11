package nexti.ejms.util.math;

public class EvaluateException extends Exception
{
	private static final long serialVersionUID = 1L;
	String message = "";
	public EvaluateException() {}
	public EvaluateException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
