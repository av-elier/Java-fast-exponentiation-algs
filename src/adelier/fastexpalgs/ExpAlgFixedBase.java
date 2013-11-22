package adelier.fastexpalgs;

import java.math.BigInteger;

public abstract class ExpAlgFixedBase implements ExpAlg {

	public abstract void precalculate(BigInteger x, BigInteger p);
	public abstract BigInteger exp(BigInteger n);
	
	@Override
	public BigInteger exp(BigInteger x, BigInteger n, BigInteger p) {
		x = x.mod(p);
		n = n.mod(p.subtract(BigInteger.ONE));
		// stupid cases
		if (n.equals(BigInteger.ZERO))
			return BigInteger.ONE;
		if (n.equals(BigInteger.ONE))
			return x;
		if (x.equals(BigInteger.ONE) || x.equals(BigInteger.ZERO))
			return x;
		
		precalculate(x, p);
		return exp(n);
	}
}
