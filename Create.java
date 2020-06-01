package 四则运算2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Create {
	Expression expression = new Expression();
	Calc cal = new Calc();
	HashMap<String, String> map = new HashMap<String, String>();

	// 在当前目录下生成练习题和答案
	public void cr(final int n, final int r) throws IOException {
		final BufferedWriter btex = new BufferedWriter(new FileWriter(".\\Exercises.txt"));
		final BufferedWriter btan = new BufferedWriter(new FileWriter(".\\Answers.txt"));
		for (int i = 1; i < n + 1; i++) {
			String exps = expression.getexp(r);

			List<String> cnkiexp1 = cal.getcnki(exps);
			String cnki1 = cal.list2String(cnkiexp1);
			boolean contains2 = false;
			if (cnkiexp1.get(0).equals("+") || cnkiexp1.get(0).equals("×")) {
				final List<String> cnkiexp2 = cnkiexp1;
				final String t1 = cnkiexp2.get(1);
				final String t2 = cnkiexp2.get(2);
				cnkiexp2.set(1, t2);
				cnkiexp2.set(2, t1);
				final String cnki2 = cal.list2String(cnkiexp2);
				contains2 = map.containsKey(cnki2);
			}
			if (i == 1) {
				map.put(cnki1, "");
			}
			final boolean contains = map.containsKey(cnki1);
			if (contains == true || contains2 == true) {
				exps = expression.getexp(r);
				cnkiexp1 = cal.getcnki(exps);
				cnki1 = cal.list2String(cnkiexp1);
				contains2 = false;
				if (cnkiexp1.get(0).equals("+") || cnkiexp1.get(0).equals("×")) {
					final List<String> cnkiexp2 = cnkiexp1;
					final String t1 = cnkiexp2.get(1);
					final String t2 = cnkiexp2.get(2);
					cnkiexp2.set(1, t2);
					cnkiexp2.set(2, t1);
					final String cnki2 = cal.list2String(cnkiexp2);
					contains2 = map.containsKey(cnki2);
				}
			}
			map.put(cnki1, "");

			final Fraction answer = cal.calculate(exps);
			final String answers = answer.toString();
			btex.write(i + ". " + exps + "\r\n");
			btan.write(i + ". " + answers + "\r\n");
		}
		btex.flush();
		btan.flush();
		btex.close();
		btan.close();
	}
}
