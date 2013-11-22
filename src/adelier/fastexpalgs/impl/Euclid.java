package adelier.fastexpalgs.impl;

import java.math.BigInteger;

import adelier.fastexpalgs.ExpAlg;
import adelier.fastexpalgs.ExpAlgFixedBase;



public class Euclid extends ExpAlgFixedBase {

	private int radixBitCount, length;
	private BigInteger p;
	private BigInteger mask;
	private ExpAlg simpleAlg;

	/**
	 * At <i>i</i> stands <i>x^(2^radixBitCount)</i> <br>
	 * At 0 stands x
	 */
	private BigInteger[] xdegs;

	public Euclid(int radixBitCount, ExpAlg simpleAlg) throws Exception {
		if (simpleAlg.getClass().equals(this.getClass()))
			throw new Exception("Illegal exponential algorythm");
		this.setRadixBitCountAndMask(radixBitCount);
		this.simpleAlg = simpleAlg;
	}

	private void setRadixBitCountAndMask(int radixBitCount) {
		this.radixBitCount = radixBitCount;
		this.mask = BigInteger.valueOf(2).pow(radixBitCount)
				.subtract(BigInteger.ONE);
	}

	@Override
	public void precalculate(BigInteger x, BigInteger p) {
		this.p = p;
		this.length = (p.bitLength() - 1) / radixBitCount + 1;
		this.xdegs = new BigInteger[length];

		xdegs[0] = x;
		for (int i = 1; i < length; i++) {
			for (int j = 0; j < radixBitCount; j++)
				x = x.multiply(x).mod(p);
			xdegs[i] = x;
		}

	}

	@Override
	public BigInteger exp(BigInteger n) {
		BigInteger[] xdegs = this.xdegs.clone();
		BigInteger[] ndegs = toRadixRepresentation(n);

		while (true) {
			// найти индексы для двух максимальных 1-самый, 2-второй
			// k1
			int k1 = 0;
			for (int i = 1; i < ndegs.length; i++) {
				if (ndegs[i] == null)
					break;
				if (ndegs[i].compareTo(ndegs[k1]) > 0) {
					k1 = i;
				}
			}
			// k2
			int k2 = k1 == 0 ? 1 : 0;
			for (int i = 0; i < ndegs.length; i++) {
				if (ndegs[i] == null)
					break;
				if (i != k1 && ndegs[i].compareTo(ndegs[k2]) > 0) {
					k2 = i;
				}
			}

			// если второй == 0 - return x_M^nm
			if (ndegs[k2] == null || ndegs[k2].equals(BigInteger.ZERO))
				return simpleAlg.exp(xdegs[k1], ndegs[k1], p);
			// иначе 
			BigInteger[] divAndMod = ndegs[k1].divideAndRemainder(ndegs[k2]);
			// d = n_M / n_N
			BigInteger d = divAndMod[0]; // div
			// x_N = x_M * x_N
			xdegs[k2] = xdegs[k2].multiply(
					simpleAlg.exp(xdegs[k1], d, p)).mod(p);
			// n_M = n_M mod n_N
			ndegs[k1] = divAndMod[1]; // mod
		}
	}

	private BigInteger[] toRadixRepresentation(BigInteger val) {
		BigInteger[] radixRepr = new BigInteger[length];
		int k = 0;
		while (!val.equals(BigInteger.ZERO)) {
			radixRepr[k++] = val.and(mask);
			val = val.shiftRight(radixBitCount);
		}
		return radixRepr;
	}
}
