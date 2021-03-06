package adelier.fastexpalgs.impl;

import java.math.BigInteger;

import adelier.fastexpalgs.ExpAlg;

public class RightToLeft implements ExpAlg {

	/**
	 * ZZ_p y = conv<ZZ_p>(1);
	 * 
	 * long l = NumBits(exponent); for(int i = 0; i < l; ++i){ if (bit(exponent,
	 * i) == 1) y*=x; x*=x; } return y;
	 */
	public BigInteger exp(BigInteger x, BigInteger n, BigInteger p) {
		BigInteger res = BigInteger.ONE;

		for (int i = 0; i < n.bitLength(); i++) {
			if (n.testBit(i)) {
				res = res.multiply(x).mod(p);
			}
			x = x.multiply(x).mod(p);
		}
		return res;
	}

}
