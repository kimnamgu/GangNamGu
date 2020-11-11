package nexti.ejms.util.math;

import java.util.Vector;

/**
 *	연산자(Operator)를 추상화한 클래스.
 *	Adapter 패턴에 따라 설계하였다. Adapter 패턴은 정적 초기화 블록에서
 *	사칙연산자 객체를 간단하게 생성하는데 강력한 힘을 발휘한다.
 */
abstract class Operator extends Token
{
	static Vector operators = new Vector();
	static final String symbols = "+-*/%";

	/**
	 *	정적 초기화 블록.
	 *	중요한 사칙연산자를 초기화한다. 
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
	 *	주어진 연산기호에 해당하는 연산자 객체를 돌려준다.
	 *
	 *	@param symbol		연산기호
	 *	@return				연산자 객체
	 */
	protected static Operator getOperator(String symbol)
	{
		return (Operator) operators.elementAt(symbols.indexOf(symbol));
	}

	/**
	 *	주어진 기호가 연산기호인지를 알려준다.
	 *
	 *	@param token		연산자인가를 확인할 기호
	 *	@return				연산기호라면 참
	 */
	protected static boolean isOperator(String token)
	{
		return (symbols.indexOf(token) >= 0);
	}

	/**
	 *	생성자.
	 *
	 *	@param symbol		연산자 기호
	 *	@param priority		우선순위(높을 수록 높음)
	 */
	protected Operator(String symbol, int priority)
	{
		super(symbol, priority);
	}
	
	/**
	 *	인수를 넘겨받아 연산한 결과값을 돌려준다.
	 *
	 *	@param a		첫번째 인수
	 *	@param b		두번째 인수
	 *	@return			연산결과
	 */
	protected Double evaluate(Double a, Double b)
	{
		return new Double(evaluate(a.doubleValue(), b.doubleValue()));
	}

	abstract double evaluate(double a, double b);
}
