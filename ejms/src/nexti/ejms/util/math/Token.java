package nexti.ejms.util.math;

/**
 *	���� parsing�� �⺻������ �Ǵ� Ŭ����.
 *	<code>Operator</code>, <code>Operand</code>, 
 *	<code>Function</code>�� �θ�Ŭ������ �ȴ�.
 *
 */
class Token
{
	// ���������� ����ϴ� ��� ��ū��
	static final Token NULL = new Token("", 0);
	static final Token LEFT_PAREN = new Token("(", 1);
	static final Token RIGHT_PAREN = new Token(")", 1);

	String symbol;
	int priority;
	String className;

	/**
	 *	������.
	 *
	 *	@param symbol		��ū�� �ĺ���(�̸�)
	 */
	protected Token(String symbol)
	{
		this.symbol = symbol;
	}

	/**
	 *	������.
	 *
	 *	@param symbol		��ū�� �ĺ���(�̸�)
	 *	@param priority		parsing�� �켱����
	 */
	protected Token(String symbol, int priority)
	{
		this(symbol);
		this.priority = priority;
	}
	
	/**
	 *	�� ��ū�� �־��� ���ڿ��� �������� �˷��ش�.
	 *
	 *	@param str			���� ���ڿ�
	 *	@return				���ٸ� ��
	 */
	protected boolean equals(String str)
	{
		return symbol.equals(str);
	}

	/**
	 *	�� ��ü�� ������ ���ڿ�ȭ�Ѵ�.
	 *
	 *	@return				���ڿ�ȭ�� ��ü�� ����
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
	 *	�־��� ��ū���� �켱������ �������� �˷��ش�.
	 *
	 *	@param token		�켱������ ���� ��ū
	 *	@return				�� ��ū�� �켱������ ���ٸ� ��
	 */
	protected boolean isPriorTo(Token token)
	{
		return (priority >= token.priority);
	}

	// Operand, Function, Operator ��ü���� ��ӹ޾� ����� method��.
	protected Double evaluate() throws EvaluateException { return null; }
	protected Double evaluate(Double a) throws EvaluateException { return null; }
	protected Double evaluate(Double a, Double b) { return null; }
}
