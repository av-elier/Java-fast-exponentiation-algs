package adelier.fastexpalgs.main;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import adelier.fastexpalgs.ExpAlg;
import adelier.fastexpalgs.ExpAlgFixedBase;
import adelier.fastexpalgs.ExpAlgFixedExponent;
import adelier.fastexpalgs.impl.Euclid;
import adelier.fastexpalgs.impl.FloatingWindow;
import adelier.fastexpalgs.impl.JavaModPow;
import adelier.fastexpalgs.impl.LeftToRight;
import adelier.fastexpalgs.impl.RightToLeft;

public class ExpAlgsMain {

	public static void main(String[] args) {		
		System.out.println("type anything when jvisialvm ready to profile");
		new Scanner(System.in).nextLine();

		List<ExpAlg> algs = new LinkedList<>();
//		algs.add(new JavaModPow());
		algs.add(new RightToLeft());
//		algs.add(new LeftToRight());
		algs.add(new FloatingWindow(8));
		algs.add(new Euclid(250, new FloatingWindow(5)));


		completeProf(algs);
	}
	
	private static void completeProf(List<ExpAlg> algs) {
		long st = System.currentTimeMillis();
		double bistOnTest = 16*1024;
		Random rand = new SecureRandom();
		List<Integer> testLengths = Arrays.asList(8, 64, 512, 2048);
		for (int bitLength : testLengths) {
			BigInteger p = new BigInteger(bitLength, /*certainty*/80, rand);
			int testBasesCount = (int) Math.floor(Math.sqrt(bistOnTest / bitLength));
			int testExponentsCount = (int) Math.ceil(Math.sqrt(bistOnTest / bitLength));
			List<BigInteger> bases = randomBigIntegerList(
					testBasesCount, bitLength, rand);
			List<BigInteger> exponents = randomBigIntegerList(
					testExponentsCount, bitLength, rand);
			for (ExpAlg alg : algs)
				profileNormal(alg, bases, exponents, p);
		}
		System.out.println( System.currentTimeMillis() - st );
	}

	private static List<BigInteger> randomBigIntegerList(int length,
			int bitLength, Random rand) {
		List<BigInteger> exponents = new LinkedList<>();
		for (int i = 0; i < length; i++) {
			exponents.add(new BigInteger(bitLength, rand));
		}
		return exponents;
	}

	private static BigInteger profileNormal(ExpAlg alg, List<BigInteger> bases,
			List<BigInteger> exponents, BigInteger p) {
		System.gc();
		BigInteger res = BigInteger.ONE;
		for (BigInteger x : bases) {
			for (BigInteger n : exponents) {
				res = alg.exp(x, n, p);
			}
		}
		return res;
	}
}
