package nexti.ejms.util.math;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *	피연산자(Operand)의 목록을 관리하는 클래스.
 *
 */
class OperandPool
{
	static final Operand PI = new Operand("PI", Math.PI);
	Hashtable operands = new Hashtable();

	Double value;

	/**
	 *	생성자.
	 *
	 */
	OperandPool()
	{
		operands.put("PI", PI);
	}

	/**
	 *	주어진 이름을 갖는 피연산자를 돌려준다.
	 *
	 *	@param symbol		피연산자의 이름
	 *	@return				피연산자
	 */
	protected Operand getOperand(String symbol)
	{
		Operand op;
		if (operands.containsKey(symbol))
		{
			op = (Operand) operands.get(symbol);
		}
		else
		{
			op = new Operand(symbol);
			operands.put(symbol, op);
		}
		
		return op;
	}

	/**
	 *	특정 피연산자에 값을 할당해준다. (가령, x = 3)
	 *
	 *	@param symbol		피연산자의 이름
	 *	@param value		값
	 *	@exception			잘못된 값(수자가 아닌)이 할당되었을 때
	 */
	protected void setValue(String symbol, String value)
		throws EvaluateException
	{
		Enumeration en = operands.keys();
		while (en.hasMoreElements())
		{
			String name = (String) en.nextElement();
			Operand op = (Operand) operands.get(name);

			if (name.equals(symbol)) 
			{
				op.init();
				op.setValue(value);
			}
		}
	}
}
