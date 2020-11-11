package nexti.ejms.util.math;

import java.lang.reflect.Method;

/**
 *	���Ŀ��� �����Լ��� ����� �� �ֵ��� �Լ��� �߻�ȭ�� Ŭ����.
 *	<code>java.lang.Math</code> Ŭ������ wrapper ������ ����ϸ�,
 *	reflection API�� �̿��Ͽ� <code>java.lang.Math</code> Ŭ������
 *	��� method�� ���� ������ �����ϴ�.
 *
 */
class Function extends Token
{
	private Method method;
	private static Method[] methods;

	/**
	 *	���� �ʱ�ȭ ���.
	 *	<code>java.lang.Math</code> Ŭ������ method���� ���س��´�.
	 */
	static
	{
		try
		{
			Class mathClass = Class.forName("java.lang.Math");
			methods = mathClass.getMethods();
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (SecurityException ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 *	�־��� ���ڿ��� �Լ��� �̸��ΰ��� �˷��ش�.
	 *
	 *	@param token		��ȣ
	 *	@return				�Լ���� ��
	 */
	protected static boolean isFunction(String token)
	{
		for (int i = 0; i < methods.length; i++)
			if (methods[i].getName().equals(token))
				return true;
		return false;
	}

	/**
	 *	������.
	 *
	 *	@param name			�Լ��� �̸�(����, sin, cos)
	 */
	protected Function(String name)
	{
		// �Լ��� �켱������ ���� ����(4)
		super(name, 4);

		try
		{
			for (int i = 0; i < methods.length; i++)
			{
				if (methods[i].getName().equals(name))
				{
					method = methods[i];
					break;
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 *	�Լ��� ������� ���Ѵ�.
	 *	
	 *	@param value		�Լ��� �μ���
	 *	@return				�����
	 *	@exception			����� ���� �� ���� ���
	 */
	protected Double evaluate(Double value)
		throws EvaluateException
	{
		try
		{
			Double[] args = new Double[] { value };
			Double result = (Double) method.invoke(null, args);
			return result;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new EvaluateException();
		}
	}
}
