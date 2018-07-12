package computor;

public class Main {
	public static void main(String[] args) {
		try {
			ArgumentsManagement management = new ArgumentsManagement();
			management.checkArgumentPresent(args.length);
//		String s = "5.3 * X^1 + 4 * X^1 + 3 * X^3 - 9 * X^2 + -100 * X^0 = 55 * X^0 + +3 * X^3";
//		String s = "5 * X^0 + 4 * X^1 - 9.3 * X^2 = 1 * X^0";
//		String s = "5 * X^0 + 4 * X^1 = 4 * X^0";
//		String s = "8 * X^0 - 6 * X^1 + 0 * X^2 - 5.6 * X^3 = 3 * X^0";
//			String s = "42 * X^0 = 42 * X^1";
			management.checkArgumentValid(args[0]);
//			System.out.println(management.getMapOfPowers(s));
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
	}
}
