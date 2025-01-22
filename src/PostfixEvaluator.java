import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class PostfixEvaluator {

	private String expression;
	private String postfix;
	private final Deque<Character> operatorStack;

	public PostfixEvaluator(String expression) {
		this.expression = expression.trim().replaceAll("\\s+", "");
		this.operatorStack = new LinkedList<>();

		generateNotation();
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
		generateNotation();
	}

	private int precedence(char o) {
		if (o == '+') {
			return 11;
		} else if (o == '-') {
			return 11;
		} else if (o == '*') {
			return 12;
		} else if (o == '/') {
			return 12;
		} else {
			return 13;
		}
	}

	private boolean conditionForOperator(Deque<Character> operatorSTack, char sign) {
		return !this.operatorStack.isEmpty() && this.operatorStack.peek() != '('
				&& (precedence(sign) < precedence(this.operatorStack.peek())
						|| precedence(sign) == precedence(this.operatorStack.peek())
								&& this.operatorStack.peek() != '^');
	}

	private boolean isOperator(char sign) {
		return sign == '+' || sign == '-' || sign == '*' || sign == '/' || sign == '^';
	}

	private void generateNotation() {
		StringBuilder sb = new StringBuilder();
		int sizeExp = this.expression.length();
		
		for (int i = 0; i < sizeExp; i++) {
			char sign = this.expression.charAt(i);

			if (Character.isDigit(sign)) {
				sb.append(sign);
			} else if (isOperator(sign)) {
				while (conditionForOperator(this.operatorStack, sign)) {
					sb.append(this.operatorStack.pop());
				}
				this.operatorStack.push(sign);
			} else if (sign == '(') {
				this.operatorStack.push(sign);
			} else if (sign == ')') {
				boolean found = false;
				while (this.operatorStack.peek() != '(') {
					sb.append(this.operatorStack.pop());
					found = true;
				}
				if (found == false) {
					System.err.println("Expresia avea paranteze gresite!");
					break;
				}
				this.operatorStack.pop();
			}
		}

		while (!this.operatorStack.isEmpty()) {
			char toAdd = this.operatorStack.pop();
			if (toAdd == '(') {
				System.err.println("Expresia avea paranteze gresite!");
				break;
			} else {
				sb.append(toAdd);
			}
		}

		this.postfix = sb.toString();
	}

	public static int evaluateNotation(String postfix) {
		final Deque<String> resultStack = new LinkedList<>();
		int sizeString = postfix.length();

		for (int i = 0; i < sizeString; i++) {
			char entity = postfix.charAt(i);
			if (Character.isWhitespace(entity)) {
				continue;
			}
			if (Character.isDigit(entity)) {
				resultStack.push(String.valueOf(entity));
			} else {
				if (resultStack.size() >= 2) {
					int op1 = Integer.valueOf(String.valueOf(resultStack.pop())).intValue();
					int op2 = Integer.valueOf(String.valueOf(resultStack.pop())).intValue();

					int resultOfOp1Op2 = compute(entity, op1, op2);
					resultStack.push(String.valueOf(resultOfOp1Op2));
				} else {
					System.err.println("Expresia postfixata este gresita!");
					break;
				}
			}
		}

		int result = Integer.valueOf(resultStack.pop()).intValue();

		if (resultStack.size() != 0) {
			System.err.println("Expresia postfixata este gresita");
		}

		return result;
	}

	private static int compute(char entity, int op1, int op2) {
		if (entity == '+') {
			return op2 + op1;
		} else if (entity == '-') {
			return op2 - op1;
		} else if (entity == '*') {
			return op2 * op1;
		} else if (entity == '/') {
			return op2 / op1;
		} else {
			return (int) Math.pow(op2, op1);
		}
	}

	public String getPostfix() {
		return postfix;
	}
}