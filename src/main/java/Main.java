public class Main {
	public static void main(String[] args) {
		ArgumentsManagement management = new ArgumentsManagement();
//		String s = "5.3 * X^1 + 4 * X^1 + 3 * X^3 - 9 * X^2 + -100 * X^0 = 55 * X^0 + +3 * X^3";
		String s = "5 * X^0 + 4 * X^1 - 9.3 * X^2 = 1 * X^0";
		management.checkArgumentValid(s);
		try {
//			System.out.println(management.getMapOfPowers(s));
		} catch (RuntimeException e) {
			throw new ArgumentsException("The input is bad formatted");
		}
	}
}
