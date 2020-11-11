package nexti.ejms.util.math;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *	수식을 parse하여 그 값을 구해주는 클래스.
 *
 */
public class Expression
{
	private OperandPool operandPool = new OperandPool();
	private Vector postfix;

	/**
	 *	생성자.
	 *
	 *	@param expr			문자열 형태의 수식
	 *	@exception			해석할 수 없는 수식일 때
	 */
	public Expression(String expr)
		throws ParseException
	{
		Token[] tokens = parse(expr);
		postfix = toPostfix(tokens);
	}

	/**
	 *	주어진 수식을 parse하여 토큰의 배열을 생성한다.
	 *
	 *	@param exprr		수식 문자열
	 *	@return				토큰의 배열
	 *	@exception			해석할 수 없는 수식일 때
	 */
	private Token[] parse(String expr)
		throws ParseException
	{
		StringTokenizer st = 
			new StringTokenizer(expr, "+-*/%() \t", true);

		Token[] result = new Token[st.countTokens()];
		try
		{
			for (int i = 0; st.hasMoreTokens(); i++)
			{
				String token = st.nextToken();

				// unary minus 처리
				if (token.equals("-"))
				{
					if (i >= 1 && !(result[i - 1] instanceof Operand) || i == 0)
					{
						String tempToken = st.nextToken();
						boolean isNumber = true;
						try {
							Integer.parseInt(tempToken, 10);
						} catch ( Exception e ) {
							isNumber = false;
						}
						
						if ( isNumber || ( isNumber && !Token.LEFT_PAREN.equals(tempToken) ) ) {
							result[i++] = Operator.getOperator("+");
							result[i] = operandPool.getOperand(token + tempToken);
							continue;
						} else {
							result[i++] = Operator.getOperator(token);
							token = tempToken;
						}
					}
				}
				if (Operator.isOperator(token))
					result[i] = Operator.getOperator(token);
				else if (Function.isFunction(token))
					result[i] = new Function(token);
				else if (Token.LEFT_PAREN.equals(token))
					result[i] = Token.LEFT_PAREN;
				else if (Token.RIGHT_PAREN.equals(token))
					result[i] = Token.RIGHT_PAREN;
				else if (!token.equals(" ") && !token.equals("\t"))
					result[i] = operandPool.getOperand(token);
			}
				
			return result;
		}
		catch (Exception ex)
		{
			throw new ParseException();
		}
	}
	
	/**
	 *	토큰의 배열을 infix에서 postfix로 바꾸어 
	 *	<code>Vector</code>로 돌려준다.
	 *
	 *	@param tokens		토큰의 배열
	 *	@return				postfix 방식의 <code>Vector</code>
	 *	@exception			해석할 수 없는 수식일 때
	 */
	private Vector toPostfix(Token[] tokens)
		throws ParseException
	{
		Stack stack = new Stack();
		Vector postfix = new Vector();

		try
		{
			stack.push(Token.NULL);
			for (int i = 0; i < tokens.length; i++)
			{
				Token token = tokens[i];

				if (token instanceof Operand)
					postfix.addElement(token);
				else if (token == Token.LEFT_PAREN)
					stack.push(token);
				else if (token == Token.RIGHT_PAREN)
				{
					Token t = (Token) stack.pop();
					while (t != Token.LEFT_PAREN)
					{
						postfix.addElement(t);
						t = (Token) stack.pop();
					}
				}
				else if (token instanceof Operator ||
					token instanceof Function)
				{
					Token t = (Token) stack.pop();
					while (t.isPriorTo(token))
					{
						postfix.addElement(t);
						t = (Token) stack.pop();
					}
					stack.push(t);
					stack.push(token);
				}
			}

			while (stack.peek() != Token.NULL)
				postfix.addElement(stack.pop());

			return postfix;
		}
		catch (Exception ex)
		{
			throw new ParseException();
		}
	}

	/**
	 *	수식의 값을 계산하여 돌려준다.
	 *
	 *	@return				수식의 값
	 *	@exception			값을 계산할 수 없을 때
	 */
	public double evaluate()
		throws EvaluateException
	{
		Stack stack = new Stack();

		for (int i = 0; i < postfix.size(); i++)
		{
			Token t = (Token) postfix.elementAt(i);

			if (t instanceof Operand)
				stack.push(t.evaluate());
			else if (t instanceof Function)
			{
				Double a = (Double) stack.pop();
				stack.push(t.evaluate(a));
			}
			else if (t instanceof Operator)
			{
				Double b = (Double) stack.pop();
				Double a = (Double) stack.pop();
				stack.push(t.evaluate(a, b));
			}
		}

		return ((Double)stack.pop()).doubleValue();
	}

	/**
	 *	수식의 값을 계산하여 돌려준다.
	 *
	 *	@param values		변수값을 담고있는 <code>Hashtable</code>
	 *	@return				수식의 값
	 *	@exception			값을 계산할 수 없을 때
	 */
	public double evaluate(Hashtable values)
		throws EvaluateException
	{
		if (values != null)
		{
			Enumeration en = values.keys();
			while (en.hasMoreElements())
			{
				String name = (String) en.nextElement();
				String value = (String) values.get(name);

				operandPool.setValue(name, value);
			}
		}
	
		return evaluate();
	}

	public static void main(String[] args)
		throws Exception
	{
		Expression expr = new Expression(args[0]);
		System.out.println(expr.evaluate());
	}
}
