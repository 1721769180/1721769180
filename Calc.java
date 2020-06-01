package 四则运算2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calc {
	char[] oper6 = { '+', '-', '×', '÷', '(', ')' };
	String[] oper4 = { "+", "-", "×", "÷" };
	private static int ADDITION = 1;
	private static int SUBTRACTION = 1;
	private static int MULTIPLICATION = 2;
	private static int DIVISION = 2;

	public static int getValue(final String op) {
		int value;
		switch (op) {
		case "+":
			value = ADDITION;
			break;
		case "-":
			value = SUBTRACTION;
			break;
		case "×":
			value = MULTIPLICATION;
			break;
		case "÷":
			value = DIVISION;
			break;
		default:
			value = 0;
		}
		return value;
	}

	// 判断是否是操作符
	public boolean isoper(final char c) {
		for (final char op : oper6) {
			if (op == c)
				return true;
		}
		return false;
	}

	public boolean isoper(final String s) {
		for (final String op1 : oper4) {
			if (s.equals(op1))
				return true;
		}
		return false;
	}

	// 判断是否是数字
	public boolean isfra(final char c) {
		if (c >= '0' && c <= '9') {
			return true;
		}
		return false;
	}

	public boolean isfraop(final char c) {
		if (c == '\'' || c == '/') {
			return true;
		}
		return false;
	}

	// 将String类型表达式转换成List<String>类型
	public List<String> Stringtolist(String str) {
		final List<String> infix = new ArrayList<String>();
		str = str.replace(" ", "");
		char op;
		String temp = "";
		for (int i = 0; i < str.length(); i++) {
			op = str.charAt(i);
			if (isfra(op) || isfraop(op)) {
				temp += op;
			} else if (isoper(op)) {
				if (!temp.isEmpty()) {
					infix.add(temp);
					temp = "";
				}
				infix.add(op + "");
			}
		}
		if (!temp.isEmpty()) {
			infix.add(temp);
			temp = "";
		}
		return infix;
	}

	// 将中缀表达式转换为后缀表达式
	public List<String> infixtopostfix(final List<String> infix) {
		final List<String> postfix = new ArrayList<String>();
		final Stack<String> s1 = new Stack<String>();
		for (final String str : infix) {
			if (str.equals("(")) {
				s1.push(str);
			} else if (str.equals(")")) {
				while (!s1.peek().equals("(")) {
					postfix.add(s1.pop());
				}
				s1.pop();
			} else if (str.equals("+") || str.equals("-") || str.equals("×") || str.equals("÷")) {
				while (s1.size() != 0 && getValue(s1.peek()) >= getValue(str)) {
					postfix.add(s1.pop());
				}
				s1.push(str);
			} else {
				postfix.add(str);
			}
		}
		while (s1.size() != 0) {
			postfix.add(s1.pop());
		}
		return postfix;
	}

	// 对后缀表达式进行计算
	public Fraction calculate(final String str) {
		final List<String> linfix = Stringtolist(str);
		final List<String> lpostfix = infixtopostfix(linfix);
		final Stack<Fraction> s2 = new Stack<Fraction>();
		Fraction f1;
		Fraction f2;
		Fraction answer;
		for (final String cal : lpostfix) {
			if (!isoper(cal)) {
				final Fraction f = Fraction.tofraction(cal);
				s2.push(f);
			} else {
				switch (cal) {
				case "+":
					f1 = s2.pop();
					f2 = s2.pop();
					answer = f2.add(f1);
					s2.push(answer);
					break;
				case "-":
					f1 = s2.pop();
					f2 = s2.pop();
					answer = f2.sub(f1);
					s2.push(answer);
					break;
				case "×":
					f1 = s2.pop();
					f2 = s2.pop();
					answer = f2.mult(f1);
					s2.push(answer);
					break;
				case "÷":
					f1 = s2.pop();
					f2 = s2.pop();
					answer = f2.div(f1);
					s2.push(answer);
					break;
				}
			}
		}
		return s2.pop();
	}

	// 查重表达式的生成
	public List<String> getcnki(final String str) {
		final List<String> linfix = Stringtolist(str);
		final List<String> lpostfix = infixtopostfix(linfix);
		final List<String> cnkiexp = new ArrayList<String>();
		final Stack<String> st = new Stack<String>();
		String top1 = null;
		String top2 = null;
		for (final String cal : lpostfix) {
			if (!isoper(cal)) {
				st.push(cal);
			} else if (cal.equals("+") || cal.equals("-") || cal.equals("×")) {
				cnkiexp.add(cal);
				top1 = st.pop();
				top2 = st.pop();
				if (top2 != "@") {
					cnkiexp.add(top2);
				}
				if (top1 != "@") {
					cnkiexp.add(top1);
				}
				st.push("@");
			} else if (cal.equals("÷")) {
				cnkiexp.add(cal);
				top1 = st.pop();
				top2 = st.pop();
				cnkiexp.add(top2);
				cnkiexp.add(top1);
				st.push("@");
			}
		}
		return cnkiexp;
	}

	// 将List<String>类型表达式转换成String类型
	public String list2String(final List<String> list) {
		String str1 = "";
		for (final String s : list) {
			str1 = str1 + s + " ";
		}
		return str1;
	}
}
