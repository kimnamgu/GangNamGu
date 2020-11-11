package nexti.ejms.util.math;

/**
 *	수식 parsing의 기본단위가 되는 클래스.
 *	<code>Operator</code>, <code>Operand</code>, 
 *	<code>Function</code>의 부모클래스가 된다.
 *
 */
class Token
{
	// 공통적으로 사용하는 상수 토큰들
	static final Token NULL = new Token("", 0);
	static final Token LEFT_PAREN = new Token("(", 1);
	static final Token RIGHT_PAREN = new Token(")", 1);

	String symbol;
	int priority;
	String className;

	/**
	 *	생성자.
	 *
	 *	@param symbol		토큰의 식별자(이름)
	 */
	protected Token(String symbol)
	{
		this.symbol = symbol;
	}

	/**
	 *	생성자.
	 *
	 *	@param symbol		토큰의 식별자(이름)
	 *	@param priority		parsing의 우선순위
	 */
	protected Token(String symbol, int priority)
	{
		this(symbol);
		this.priority = priority;
	}
	
	/**
	 *	이 토큰이 주어진 문자열과 같은가를 알려준다.
	 *
	 *	@param str			비교할 문자열
	 *	@return				같다면 참
	 */
	protected boolean equals(String str)
	{
		return symbol.equals(str);
	}

	/**
	 *	이 객체의 내용을 문자열화한다.
	 *
	 *	@return				문자열화된 객체의 내용
	 */
	public String toString()
	{
		if (className == null)
		{
			String className = getClass().getName();
			className = className.substring(className.lastIndexOf('.') + 1);
		}
		return className + "('" + symbol + "')";
	}

	/**
	 *	주어진 토큰보다 우선순위가 높은가를 알려준다.
	 *
	 *	@param token		우선순위를 비교할 토큰
	 *	@return				이 토큰이 우선순위가 높다면 참
	 */
	protected boolean isPriorTo(Token token)
	{
		return (priority >= token.priority);
	}

	// Operand, Function, Operator 객체에서 상속받아 사용할 method들.
	protected Double evaluate() throws EvaluateException { return null; }
	protected Double evaluate(Double a) throws EvaluateException { return null; }
	protected Double evaluate(Double a, Double b) { return null; }
}
