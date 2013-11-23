package adelier.fastexpalgs.tests;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import adelier.fastexpalgs.ExpAlg;
import adelier.fastexpalgs.impl.Euclid;
import adelier.fastexpalgs.impl.FloatingWindow;
import adelier.fastexpalgs.impl.JavaModPow;
import adelier.fastexpalgs.impl.LeftToRight;
import adelier.fastexpalgs.impl.LeftToRight;
import adelier.fastexpalgs.impl.RightToLeft;
import adelier.fastexpalgs.impl.RightToLeft;

public class FastExpAlgTests {

	@Test
	public void testRightToLeft(){
		ExpAlg alg = new RightToLeft();
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testLeftToRight(){
		ExpAlg alg = new LeftToRight();
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testJavaModPow(){
		ExpAlg alg = new JavaModPow();
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testFloatingWindow1(){
		ExpAlg alg = new FloatingWindow(1);
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testFloatingWindow2(){
		ExpAlg alg = new FloatingWindow(2);
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testFloatingWindow7(){
		ExpAlg alg = new FloatingWindow(7);
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testFloatingWindow12(){
		ExpAlg alg = new FloatingWindow(12);
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testFloatingWindow18(){
		ExpAlg alg = new FloatingWindow(18);
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testEuclid4() throws Exception{
		ExpAlg alg = new Euclid(4, new JavaModPow());
		testSmall(alg);
		testBig(alg);
	}
	@Test
	public void testEuclid40() throws Exception{
		ExpAlg alg = new Euclid(40, new JavaModPow());
		testSmall(alg);
		testBig(alg);
	}

	public void testSmall(ExpAlg alg) {
		BigInteger p = BigInteger.valueOf(59);
		for (int x = 0; x < 1300; x += 100) {
			for (int n = x; n <= x; n++) {
				BigInteger testx = BigInteger.valueOf(x);
				BigInteger testn = BigInteger.valueOf(n);
				testx = testx.mod(p);
				testn = testn.mod(p.subtract(BigInteger.ONE));
				assertEquals(String.format("x=%d ", x), testx.modPow(testn, p),
						alg.exp(testx, testn, p));
			}
		}

	}

	public void testBig(ExpAlg alg) {
		BigInteger p = new BigInteger(
				"27712789691413846856012333153970297296163904004968269562890174449688286689257199609606535023277510515406034761982956594518529310511546197362568094872418982726657993701492763179666043542495989488079404320326261147722413208737192692418855589011153981546533455140457076112131239236203432902115682930366048558529408451428808206998758788180262272085883410424636537719739093395540455527338386935943856295506908799163300014272396102579730407670957135454497986490504948008147820336881023710843768633407741391118690537112243688304714186312631347293412881026493085011018061051821439480590001356880342976195464741900137237920847");
		BigInteger testx = new BigInteger("1000000000000011");
		BigInteger testn = new BigInteger("600000000000000000");

		BigInteger actual = alg.exp(testx, testn, p);
		assertEquals(
				new BigInteger(
						"13087491684241113015517525582381613551928185857402745136182486373380571629891992887325360809621036323435147995753689131058003871403129905512753230328864264094195812407844859696667522469379932692152002017028617447019139080656538413749656533169945448023321252644854812765964963906168540691779057780799153488537885375483903168455749477368549139733097079453946133105379277716581209170464138999046593339063443707166012212912759386371444948066493750462047668008126003102475499988244119660377327710182302219921296757289124472606698113754579466279830808416998732593942224585948797878307937826716821824061030588235929695917435"),
				actual);
	}
}
