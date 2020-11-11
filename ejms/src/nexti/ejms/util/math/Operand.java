/**
 *	�ǿ�����(Operand)�� �߻�ȭ�� Ŭ����
 *
 */
package nexti.ejms.util.math;

class Operand extends Token
{
	Double value;

	/**
	 *	������.
	 *
	 *	@param symbol		�ǿ������� �̸�
	 */
	protected Operand(String symbol)
	{
		super(symbol);
	}

	/**
	 *	������.
	 *
	 *	@param symbol		�ǿ������� �̸�
	 *	@param value		�ǿ������� ��
	 */
	protected Operand(String symbol, double value)
	{
		this(symbol);
		this.value = new Double(value);
	}

	/**
	 *	�� �ǿ����ڿ� �Ҵ�� ���� �ʱ�ȭ�Ѵ�.
	 *
	 */
	protected void init()
	{
		value = null;
	}

	/**
	 *	�� �ǿ����ڿ� Ư���� ���� �Ҵ��Ѵ�.
	 *
	 *	@param value		�ǿ����ڿ� �Ҵ��� ��
	 *	@exception			�߸��� ��(���ڰ� �ƴ�)�� �Ҵ�Ǿ��� ��
	 */
	protected void setValue(String value)
		throws EvaluateException
	{
		try
		{
			this.value = Double.valueOf(value);
		}
		catch (Exception ex)
		{
			throw new EvaluateException();
		}
	}

	/**
	 *	�� �ǿ����ڿ� �Ҵ�� ���� ���Ѵ�.
	 *	�ǿ����ڰ� ���(����, 3)�� ��쿡�� symbol�� ����,
	 *	����(����, x)�� ��쿡�� �Ҵ�� ���� �����ش�.
	 *
	 *	@param value		�ǿ����ڿ� �Ҵ��� ��
	 *	@exception			�߸��� ��(���ڰ� �ƴ�)�� �Ҵ�Ǿ��� ��
	 */
	protected Double evaluate()
		throws EvaluateException
	{
		try
		{
			return (value != null) ? value : new Double(symbol);
		}
		catch (Exception ex)
		{
			throw new EvaluateException();
		}
	}
}
