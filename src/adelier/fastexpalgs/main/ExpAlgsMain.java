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

	public static void main(String[] args) throws Exception {
		System.out.println("type anything when jvisialvm ready to profile");
		new Scanner(System.in).nextLine();

		List<ExpAlg> algs = new LinkedList<>();
		algs.add(new RightToLeft());
		algs.add(new LeftToRight());
		algs.add(new FloatingWindow(8));
		algs.add(new JavaModPow());
		algs.add(new Euclid(20, new JavaModPow()));

		double bistOnTest = 16*1024;
		Random rand = new SecureRandom();
		List<Integer> testLengths = Arrays.asList(8, 64, 512, 2048, 4*1024, 8*1024);
		for (int bitLength : testLengths) {
			BigInteger p = new BigInteger(bitLength, rand);//.nextProbablePrime();
			int testBasesCount = (int) Math.floor(Math.sqrt(bistOnTest / bitLength));
			int testExponentsCount = (int) Math.ceil(Math.sqrt(bistOnTest / bitLength));
			List<BigInteger> bases = randomBigIntegerList(
					testBasesCount, bitLength, rand);
			List<BigInteger> exponents = randomBigIntegerList(
					testExponentsCount, bitLength, rand);
			for (ExpAlg alg : algs)
				profile(alg, bases, exponents, p);
		}

		// new Scanner(System.in).nextLine();
	}

	private static List<BigInteger> randomBigIntegerList(int length,
			int bitLength, Random rand) {
		List<BigInteger> exponents = new LinkedList<>();
		for (int i = 0; i < length; i++) {
			exponents.add(new BigInteger(bitLength, rand));
		}
		return exponents;
	}
	
	
	

	private static BigInteger profile(ExpAlg alg, List<BigInteger> bases,
			List<BigInteger> exponents, BigInteger p) {
		BigInteger res;

		/*if (ExpAlgFixedBase.class.isAssignableFrom(alg.getClass())) {
			
			res = profileFixedBase((ExpAlgFixedBase) alg, bases, exponents, p,
					testBasesCount, testExponentsCount);
		} else if (ExpAlgFixedExponent.class.isAssignableFrom(alg.getClass())) {
			
			res = profileExpAlgFixedExponent((ExpAlgFixedExponent) alg, bases,
					exponents, p, testBasesCount, testExponentsCount);
		} else */{
			res = profileNormal(alg, bases, exponents, p);
		}
		return res;
	}

	private static BigInteger profileNormal(ExpAlg alg, List<BigInteger> bases,
			List<BigInteger> exponents, BigInteger p) {
		BigInteger res = BigInteger.ONE;
		for (BigInteger x : bases) {
			for (BigInteger n : exponents) {
				res = alg.exp(x, n, p);
			}
		}
		return res;
	}

	private static BigInteger profileFixedBase(ExpAlgFixedBase alg,
			List<BigInteger> bases, List<BigInteger> exponents, BigInteger p) {
		BigInteger res = BigInteger.ONE;
		for (BigInteger x : bases) {
			alg.precalculate(x, p);
			for (BigInteger n : exponents) {
				res = alg.exp(n);
			}
		}
		return res;
	}

	private static BigInteger profileExpAlgFixedExponent(
			ExpAlgFixedExponent alg, List<BigInteger> bases,
			List<BigInteger> exponents, BigInteger p) {
		// TODO Auto-generated method stub
		return null;
	}
}
