package adelier.fastexpalgs.impl;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import adelier.fastexpalgs.ExpAlg;
import adelier.fastexpalgs.ExpAlgFixedBase;
import adelier.fastexpalgs.ExpAlgFixedExponent;

public class Euclid extends ExpAlgFixedBase {
	

	private int radix;
	private List<BigInteger> repr;
	
	public Euclid() {
		this(2);
	}
	public Euclid(int radix) {
		this.radix = radix;
	}
	
	@Override
	public void precalculate(BigInteger x, BigInteger p) {
		this.repr = new LinkedList<>();
		
		int wordLength = 0;
		BigInteger bigRadix = BigInteger.valueOf(radix);
		BigInteger tmp = p;
		while (!tmp.equals(BigInteger.ZERO)){
			BigInteger[] divideAndRemainder = tmp.divideAndRemainder(bigRadix);
			tmp = divideAndRemainder[0];
			repr.add( divideAndRemainder[1] );
		}
		
		for (int i = 0; i < wordLength; i++) {
			
		}
		
	}

	@Override
	public BigInteger exp(BigInteger n, BigInteger p) {
		// TODO Auto-generated method stub
		return null;
	}

}
