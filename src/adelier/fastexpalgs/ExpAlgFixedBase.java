package adelier.fastexpalgs;

import java.math.BigInteger;

public abstract class ExpAlgFixedBase implements ExpAlg {

	public abstract void precalculate(BigInteger x, BigInteger p);
	public abstract BigInteger exp(BigInteger n, BigInteger p);
	
	@Override
	public BigInteger exp(BigInteger x, BigInteger n, BigInteger p) {
		precalculate(x, p);
		return exp(n, p);
	}
}
