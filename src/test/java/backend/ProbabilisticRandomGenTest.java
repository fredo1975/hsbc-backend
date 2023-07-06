package backend;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import backend.proba.ProbabilisticRandomGen;
import backend.proba.ProbabilisticRandomGen.NumAndProbability;
import backend.proba.ProbabilisticRandomGenImpl;

class ProbabilisticRandomGenTest {

	@Test
	void test() {
		var c1 = new ProbabilisticRandomGen.NumAndProbability(1, 50f);
		var c2 = new ProbabilisticRandomGen.NumAndProbability(2, 20f);
		var c3 = new ProbabilisticRandomGen.NumAndProbability(3, 15f);
		var c4 = new ProbabilisticRandomGen.NumAndProbability(4, 10f);
		var c5= new ProbabilisticRandomGen.NumAndProbability(5, 5f);
		
		List<NumAndProbability> list = new ArrayList<>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		
		var impl = new ProbabilisticRandomGenImpl.ProbabilisticRandomGenImplBuilder(list).build();
		int nb50 = 0;
		int nb5 = 0;
		for(int i=0;i<100;i++) {
			if(impl.nextFromSample() == 50) {
				nb50++;
			}
			if(impl.nextFromSample() == 5) {
				nb5++;
			}
		}
		// assume that c1 will be drawn surely more than 30 times
		assertTrue(nb50>30);
		// assume that c5 will be drawn surely less than 20 times
		assertTrue(nb5<20);
	}

}
