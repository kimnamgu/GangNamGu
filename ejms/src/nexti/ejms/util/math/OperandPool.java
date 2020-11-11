package nexti.ejms.util.math;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *	�ǿ�����(Operand)�� ����� �����ϴ� Ŭ����.
 *
 */
class OperandPool
{
	static final Operand PI = new Operand("PI", Math.PI);
	Hashtable operands = new Hashtable();

	Double value;

	/**
	 *	������.
	 *
	 */
	OperandPool()
	{
		operands.put("PI", PI);
	}

	/**
	 *	�־��� �̸��� ���� �ǿ����ڸ� �����ش�.
	 *
	 *	@param symbol		�ǿ������� �̸�
	 *	@return				�ǿ�����
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
	 *	Ư�� �ǿ����ڿ� ���� �Ҵ����ش�. (����, x = 3)
	 *
	 *	@param symbol		�ǿ������� �̸�
	 *	@param value		��
	 *	@exception			�߸��� ��(���ڰ� �ƴ�)�� �Ҵ�Ǿ��� ��
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
