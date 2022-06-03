import java.util.Stack;

public class ImplementShuntingYard<E> {
	public static void main(String[] args) {
		String input = "3+(2+1)*2^3^2-8/(5-1*2/2)";
		String inputPostfixed = reversePolishNotation(input);		
		System.out.println(inputPostfixed);
		System.out.println(evaluateRPN(inputPostfixed));
	}
	
	private static int getPrecedence(char c) {
		if (c == '+' || c == '-') {
			return 11;
		} else if (c == '*'|| c == '/') {
			return 12;
		} else if (c == '^') {
			return 13;
		} 
		return -1;
	}
	
	public static String getAsoc(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/') {
			return "left";	
		}
		return "right";
	}	
	
	public static String reversePolishNotation(String input) {
		StringBuilder output = new StringBuilder();
		Stack<Character> stack = new Stack<>();
		char currentOperator;
		
		for (int i = 0; i < input.length(); i++) {	
			currentOperator = input.charAt(i);
			if (Character.isDigit(currentOperator)) {			
				output.append(currentOperator);
			} else if (currentOperator == '(') {
				stack.push(currentOperator);
			} else if (currentOperator == ')') {
				while (!stack.isEmpty() && stack.peek() != '(') {
					output.append(stack.pop());
				}
				stack.pop();
			} else {
				while (!stack.isEmpty() && getPrecedence(currentOperator) <= getPrecedence(stack.peek()) && getAsoc(currentOperator) == "left" ) {
					output.append(stack.pop());			
				}
				stack.push(currentOperator);
			}
		}
		
		while (!stack.isEmpty()) {
			if (stack.peek() == '(') {
				return "The input is invalid";
			} 
			output.append(stack.pop());
		}
				
		return output.toString();
		
	}	
	
	public static int evaluateRPN(String input) {
		int result = 0;
		Stack<Integer> stack = new Stack<Integer>();	
		char c;
		
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (Character.isDigit(c)) {
				stack.push(Character.getNumericValue(c));
			} else {
				int op1 = 0, op2 = 0;
				if (!stack.isEmpty()) {
					op1 = stack.pop();
				} else {
					System.out.println("The postfixed expression is incorrect");
					break;
				}
				
				if (!stack.isEmpty()) {
					op2 = stack.pop();
				} else {
					System.out.println("The postfixed expression is incorrect");
					break;
				}
				
				switch (c) {
				case '+':
					result = op1 + op2;
					break;
				case '-':
					result = op2 - op1;
					break;
				case '*':
					result = op1 * op2;
					break;
				case '/':
					result = op2 / op1;
					break;
				case '^':
					result = (int) Math.pow(op2, op1);
					break;
				}
				stack.push(result);
			}
		}
		
		result = stack.pop();
		
		if (!stack.isEmpty()) {
			System.out.println("The postfixed expression is incorrect");
			return -1;
		}
		
		return result;
	}	
}
	
	
	
