package adelier.fastexpalgs.impl;

import java.math.BigInteger;

import adelier.fastexpalgs.ExpAlg;

public class JavaModPow implements ExpAlg {

	@Override
	public BigInteger exp(BigInteger x, BigInteger n, BigInteger p) {
		return x.modPow(n, p);
	}

}
