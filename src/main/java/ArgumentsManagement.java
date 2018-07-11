import java.util.*;

public class ArgumentsManagement {
	public void checkArgumentPresent(int count) {
		if (count == 1) throw new ArgumentsException("Please, input something");
	}

	public void checkArgumentValid(String line) {
		line = line.trim();
		if (line.isEmpty()) throw new ArgumentsException("Empty argument");
		checkArgumentContainsOnlyRightCharacters(line);
		if (line.chars().filter(ch -> ch == '=').count() != 1)
			throw new ArgumentsException("Wrong count of '='");
		String[] split = line.split("=");
		for (int i = 0; i < split.length; i++) {
			try {
				split[i] = split[i].trim();
				checkPartArgumentValid(split[i]);
			} catch (RuntimeException e) {
				throw new ArgumentsException((i == 0 ? "Left" : "Right") + " part of equation is wrong");
			}
		}
		TreeMap<Integer, Double> generalPowers = getGeneralMap(split);
		int maximumPowerOfEquation = getMaximumPowerOfEquation(generalPowers);
		if (generalPowers.firstEntry().getValue() < 0) revertAllValues(generalPowers);
		System.out.println("Reduced form: " + getReducedForm(generalPowers));
		System.out.println("Polynomial degree: " + maximumPowerOfEquation);
		if (maximumPowerOfEquation == 2)
			getRootsOfQuadraticEquation(generalPowers);
		else if (maximumPowerOfEquation == 1)
			solveLinearEquation(generalPowers);
	}

	private TreeMap<Integer, Double> getGeneralMap(String[] split) {
		TreeMap<Integer, Double> generalPowers = new TreeMap<>();
		Map<Integer, Double> mapOfPowersLeftPart = getMapOfPowers(split[0]);
		Map<Integer, Double> mapOfPowersRightPart = getMapOfPowers(split[1]);
		for (Map.Entry<Integer, Double> entry : mapOfPowersRightPart.entrySet()) {
			Double leftPower = mapOfPowersLeftPart.get(entry.getKey());
			double power = (leftPower == null ? 0 : leftPower) - entry.getValue();
			if (power != 0) generalPowers.put(entry.getKey(), power);
		}
		for (Map.Entry<Integer, Double> entry : mapOfPowersLeftPart.entrySet()) {
			if (mapOfPowersRightPart.containsKey(entry.getKey())) continue;
			if (entry.getValue() != 0) generalPowers.put(entry.getKey(), entry.getValue());
		}
		return generalPowers;
	}

	public Map<Integer, Double> getMapOfPowers(String line) throws RuntimeException {
		Map<Integer, Double> powers = new TreeMap<>();
		String[] split = line.split(" - | \\+ ");
		boolean[] isPositive = new boolean[split.length - 1];
		int k = 0;
		for (int i = 0; i < line.length() - 2; i++) {
			String possibleSign = line.substring(i, i + 3);
			if (possibleSign.equals(" + "))
				isPositive[k++] = true;
			else if (possibleSign.equals(" - "))
				isPositive[k++] = false;
		}
		k = -1;
		for (String s : split) {
			String[] doubleAndPower = s.split(" \\* ");
			if (doubleAndPower.length != 2) throw new ArgumentsException();
			double coefficient = Double.parseDouble(doubleAndPower[0]);
			if (k != -1 && !isPositive[k]) coefficient *= -1;
			String[] xAndPower = doubleAndPower[1].split("\\^");
			if (xAndPower.length != 2 || !xAndPower[0].equals("X"))
				throw new ArgumentsException();
			int power = Integer.parseInt(xAndPower[1]);
			if (power < 0) throw new ArgumentsException();
			if (powers.containsKey(power))
				powers.put(power, powers.get(power) + coefficient);
			else
				powers.put(power, coefficient);
			k++;
		}
		if (powers.isEmpty()) throw new ArgumentsException();
		return powers;
	}

	public void checkPartArgumentValid(String line) throws RuntimeException {
		if (line.isEmpty()) throw new ArgumentsException();
	}

	public void checkArgumentContainsOnlyRightCharacters(String line) {
		if (!line.matches("[0-9. *+-^=X]*")) throw new ArgumentsException("Argument has wrong characters");
	}

	public int getMaximumPowerOfEquation(TreeMap<Integer, Double> powers) {
		int result = powers.lastKey();
		if (result > 2) {
			System.out.println("Polynomial degree: " + result);
			System.out.println("The polynomial degree is stricly greater than 2, I can't solve.");
			System.exit(0);
		}
		return result;
	}

	public void revertAllValues(Map<Integer, Double> map) {
		map.replaceAll((k, v) -> (v * -1));
	}

	public String getReducedForm(TreeMap<Integer, Double> map) {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<Integer, Double> entry : map.entrySet()) {
			Double value = entry.getValue();
			int doubleToInt = value.intValue();
			result.append(value > 0 ? " + " : " - ")
					.append((value % 1 == 0) ? (doubleToInt > 0 ? doubleToInt : -doubleToInt) : (value > 0 ? value : -value))
					.append(" * ")
					.append("X^")
					.append(entry.getKey());
		}
		result.append(" = 0");
		result.delete(0, 3);
		return result.toString();
	}

	public void getRootsOfQuadraticEquation(TreeMap<Integer, Double> map) {
		Double[] result = new Double[2];
		Double d = map.get(1) * map.get(1) - 4 * map.get(0) * map.get(2);
		if (d > 0) {
			result[0] = (-map.get(1) + Math.sqrt(d)) / (2 * map.get(2));
			result[1] = (-map.get(1) - Math.sqrt(d)) / (2 * map.get(2));
			System.out.println("Discriminant is strictly positive, the two solutions are:");
			System.out.println(result[0]);
			System.out.println(result[1]);
		} else if (d == 0) {
			result[0] = -map.get(1) / (2 * map.get(2));
			System.out.println("Discriminant is equals zero, the one solution is:");
			System.out.println(result[0]);
		} else {
			System.out.println("Discriminant is strictly negative, there are no solutions");
		}
		System.exit(0);
	}

	public void solveLinearEquation(TreeMap<Integer, Double> map) {
		double result = -map.get(0) / map.get(1);
		System.out.println("The solution is:");
		System.out.println(result);
		System.exit(0);
	}

	public boolean isEquation(String line) {
		return line.contains("X");
	}

}
