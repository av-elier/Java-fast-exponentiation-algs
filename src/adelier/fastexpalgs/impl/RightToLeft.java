package adelier.fastexpalgs.impl;

import java.math.BigInteger;

import adelier.fastexpalgs.ExpAlg;

public class RightToLeft implements ExpAlg {

	
	
	/**
	 * ZZ_p y = conv<ZZ_p>(1);
	 * 
	 * 	long l = NumBits(exponent);
	 * 	for(int i = 0; i < l; ++i){
	 * 		if (bit(exponent, i) == 1)
	 * 			y*=x;
	 * 		x*=x;
	 * 	}
	 * 	return y;
	 */
	public BigInteger exp(final BigInteger x, BigInteger n, BigInteger p) {
		BigInteger res = BigInteger.ONE;
		BigInteger y = x;
		
		byte[] nbytes = n.toByteArray();
		
		for (int i = nbytes.length - 1; i >= 0 ; i--) {
			for (byte j = 0; j < 8; j++){
				if ((( (0xff&nbytes[i]) >>> j) & 1) == 1) {
					res = res.multiply(y).mod(p);
				}
				y = y.multiply(y).mod(p);
			}
		}
		
		return res;
	}

}
