package adelier.fastexpalgs.impl;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import adelier.fastexpalgs.ExpAlg;

public class FloatingWindow implements ExpAlg {

	int windowSize;

	public FloatingWindow() {
		this(3);
	}

	public FloatingWindow(int windowSize) {
		this.windowSize = windowSize;
	}

	@Override
	public BigInteger exp(BigInteger x, BigInteger n, BigInteger p) {
		List<BigInteger> oddBaseDegrees = calculateOddBaseDegrees(x, p);
		BigInteger y = BigInteger.ONE;
		int l = n.bitLength();

		int i = l - 1;
		int ibegin, iend;
		while (i >= 0) {
			while (i >= 0 && !n.testBit(i)) {
				i--;
				y = y.multiply(y).mod(p);
			}
			if (i < 0)
				break;
			ibegin = i;
			iend = ibegin - windowSize + 1;
			if (iend < 0)
				iend = 0;
			while (!n.testBit(iend)) {
				iend++;
			}
			int window = 0;
			for (int j = ibegin; j >= iend; j--) {
				window = (window << 1) ^ (n.testBit(j) ? 1 : 0);
				y = y.multiply(y).mod(p);
			}
			y = y.multiply(oddBaseDegrees.get((window - 1) / 2)).mod(p);
			i = iend - 1;
		}

		oddBaseDegrees.clear();
		return y;
	}

	private List<BigInteger> calculateOddBaseDegrees(final BigInteger x, final BigInteger p) {
		BigInteger y = x;
		BigInteger xsqr = x.multiply(x).mod(p);
		List<BigInteger> res = new LinkedList<>();
		res.add(x);
		for (int i = 3; i < Math.pow(2, windowSize); i += 2) {
			y = y.multiply(xsqr).mod(p);
			res.add(y);
		}
		return res;
	}

}
