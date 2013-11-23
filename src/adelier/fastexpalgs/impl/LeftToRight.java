package adelier.fastexpalgs.impl;

import java.math.BigInteger;

import adelier.fastexpalgs.ExpAlg;

public class LeftToRight implements ExpAlg {

	/**
	 * ZZ_p y = conv<ZZ_p>(1);
	 * 
	 * long l = NumBits(exponent); for(int i = 0; i < l; ++i){ if (bit(exponent,
	 * i) == 1) y*=x; x*=x; } return y;
	 */
	public BigInteger exp(final BigInteger x, BigInteger n, BigInteger p) {
		BigInteger res = BigInteger.ONE;

		for (int i = n.bitLength() - 1; i >= 0; i--) {
			res = res.multiply(res).mod(p);
			if (n.testBit(i)) {
				res = res.multiply(x).mod(p);
			}
		}
		return res;
	}

}
