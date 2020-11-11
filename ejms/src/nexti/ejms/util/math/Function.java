package nexti.ejms.util.math;

import java.lang.reflect.Method;

/**
 *	수식에서 수학함수를 사용할 수 있도록 함수를 추상화한 클래스.
 *	<code>java.lang.Math</code> 클래스의 wrapper 역할을 담당하며,
 *	reflection API를 이용하여 <code>java.lang.Math</code> 클래스의
 *	모든 method에 대한 접근이 가능하다.
 *
 */
class Function extends Token
{
	private Method method;
	private static Method[] methods;

	/**
	 *	정적 초기화 블록.
	 *	<code>java.lang.Math</code> 클래스의 method들을 구해놓는다.
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
	 *	주어진 문자열이 함수의 이름인가를 알려준다.
	 *
	 *	@param token		기호
	 *	@return				함수라면 참
	 */
	protected static boolean isFunction(String token)
	{
		for (int i = 0; i < methods.length; i++)
			if (methods[i].getName().equals(token))
				return true;
		return false;
	}

	/**
	 *	생성자.
	 *
	 *	@param name			함수의 이름(가령, sin, cos)
	 */
	protected Function(String name)
	{
		// 함수의 우선순위가 가장 높다(4)
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
	 *	함수의 결과값을 구한다.
	 *	
	 *	@param value		함수의 인수값
	 *	@return				결과값
	 *	@exception			결과를 구할 수 없는 경우
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
