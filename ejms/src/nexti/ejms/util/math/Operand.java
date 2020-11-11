/**
 *	피연산자(Operand)를 추상화한 클래스
 *
 */
package nexti.ejms.util.math;

class Operand extends Token
{
	Double value;

	/**
	 *	생성자.
	 *
	 *	@param symbol		피연산자의 이름
	 */
	protected Operand(String symbol)
	{
		super(symbol);
	}

	/**
	 *	생성자.
	 *
	 *	@param symbol		피연산자의 이름
	 *	@param value		피연산자의 값
	 */
	protected Operand(String symbol, double value)
	{
		this(symbol);
		this.value = new Double(value);
	}

	/**
	 *	이 피연산자에 할당된 값을 초기화한다.
	 *
	 */
	protected void init()
	{
		value = null;
	}

	/**
	 *	이 피연산자에 특정한 값을 할당한다.
	 *
	 *	@param value		피연산자에 할당할 값
	 *	@exception			잘못된 값(수자가 아닌)이 할당되었을 때
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
	 *	이 피연산자에 할당된 값을 구한다.
	 *	피연산자가 상수(가령, 3)인 경우에는 symbol의 값을,
	 *	변수(가령, x)일 경우에는 할당된 값을 돌려준다.
	 *
	 *	@param value		피연산자에 할당할 값
	 *	@exception			잘못된 값(수자가 아닌)이 할당되었을 때
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
