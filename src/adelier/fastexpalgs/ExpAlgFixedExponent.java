package adelier.fastexpalgs;

import java.math.BigInteger;

public abstract class ExpAlgFixedExponent implements ExpAlg {
	
	
	public abstract BigInteger precalculate(BigInteger n, BigInteger p);
	public abstract BigInteger exp(BigInteger x, BigInteger p);

	@Override
	public BigInteger exp(BigInteger x, BigInteger n, BigInteger p) {
		precalculate(n, p);
		return exp(x, p);
	}
}
