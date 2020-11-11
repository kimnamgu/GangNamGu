package nexti.ejms.util.math;

import java.util.Vector;

/**
 *	������(Operator)�� �߻�ȭ�� Ŭ����.
 *	Adapter ���Ͽ� ���� �����Ͽ���. Adapter ������ ���� �ʱ�ȭ ��Ͽ���
 *	��Ģ������ ��ü�� �����ϰ� �����ϴµ� ������ ���� �����Ѵ�.
 */
abstract class Operator extends Token
{
	static Vector operators = new Vector();
	static final String symbols = "+-*/%";

	/**
	 *	���� �ʱ�ȭ ���.
	 *	�߿��� ��Ģ�����ڸ� �ʱ�ȭ�Ѵ�. 
	 */
	static
	{
		operators.addElement(new Operator("+", 2) 
				{ double evaluate(double a, double b) { return a + b; } });
		operators.addElement(new Operator("-", 2) 
				{ double evaluate(double a, double b) { return a - b; } });
		operators.addElement(new Operator("*", 3) 
				{ double evaluate(double a, double b) { return a * b; } });
		operators.addElement(new Operator("/", 3) 
				{ double evaluate(double a, double b) { return a / b; } });
		operators.addElement(new Operator("%", 3) 
				{ double evaluate(double a, double b) { return a % b; } });
	}

	/**
	 *	�־��� �����ȣ�� �ش��ϴ� ������ ��ü�� �����ش�.
	 *
	 *	@param symbol		�����ȣ
	 *	@return				������ ��ü
	 */
	protected static Operator getOperator(String symbol)
	{
		return (Operator) operators.elementAt(symbols.indexOf(symbol));
	}

	/**
	 *	�־��� ��ȣ�� �����ȣ������ �˷��ش�.
	 *
	 *	@param token		�������ΰ��� Ȯ���� ��ȣ
	 *	@return				�����ȣ��� ��
	 */
	protected static boolean isOperator(String token)
	{
		return (symbols.indexOf(token) >= 0);
	}

	/**
	 *	������.
	 *
	 *	@param symbol		������ ��ȣ
	 *	@param priority		�켱����(���� ���� ����)
	 */
	protected Operator(String symbol, int priority)
	{
		super(symbol, priority);
	}
	
	/**
	 *	�μ��� �Ѱܹ޾� ������ ������� �����ش�.
	 *
	 *	@param a		ù��° �μ�
	 *	@param b		�ι�° �μ�
	 *	@return			������
	 */
	protected Double evaluate(Double a, Double b)
	{
		return new Double(evaluate(a.doubleValue(), b.doubleValue()));
	}

	abstract double evaluate(double a, double b);
}
