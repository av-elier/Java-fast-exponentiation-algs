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
		//algs.add(new Euclid(200, new JavaModPow()));

		//completeProf(algs);
		completeProfOld(algs);
	}
	private static void completeProfOld(List<ExpAlg> algs) {
		for (ExpAlg expAlg : algs) {
			long time = profileOld(expAlg);
			System.out.println(time/1000.0 + "\t" + expAlg);
		}
	}
	
	private static long profileOld(ExpAlg algo){
		int testsCount = 3000;
		BigInteger res;
		BigInteger p = new BigInteger("27712789691413846856012333153970297296163904004968269562890174449688286689257199609606535023277510515406034761982956594518529310511546197362568094872418982726657993701492763179666043542495989488079404320326261147722413208737192692418855589011153981546533455140457076112131239236203432902115682930366048558529408451428808206998758788180262272085883410424636537719739093395540455527338386935943856295506908799163300014272396102579730407670957135454497986490504948008147820336881023710843768633407741391118690537112243688304714186312631347293412881026493085011018061051821439480590001356880342976195464741900137237920847");
        
		BigInteger samplex = new BigInteger("1000000000000011");
		BigInteger samplen = new BigInteger("600000000000000000");
		
		long st = System.currentTimeMillis();
		
		for (int i = 0; i < testsCount; ++i)
			res = algo.exp(samplex, samplen, p);
		
		return System.currentTimeMillis() - st;
	}

	private static void completeProf(List<ExpAlg> algs) {
		double bistOnTest = 4*1024;
		Random rand = new SecureRandom();
		List<Integer> testLengths = Arrays.asList(8, 64, 512, 2048, 4*1024);
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
		BigInteger res = BigInteger.ONE;
		for (BigInteger n : exponents) {
			alg.precalculate(n, p);
			for (BigInteger x : bases) {
				res = alg.exp(x);
			}
		}
		return null;
	}
}
