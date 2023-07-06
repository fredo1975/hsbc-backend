package backend.proba;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

public class ProbabilisticRandomGenImpl implements ProbabilisticRandomGen {
	
	private final List<NumAndProbability> list;
	private SplittableRandom random = new SplittableRandom();
	private ProbabilisticRandomGenImpl(ProbabilisticRandomGenImplBuilder builder) {
		super();
		this.list = builder.list;
	}

	public List<NumAndProbability> getList() {
		return list;
	}

	public int nextFromSample() {
		if(list == null || list.size() == 0) {
			return 0;
		}
		var r = random.nextInt(100);
		var probs = list.stream().map(n->n.getProbabilityOfSample()).sorted().sorted((p1,p2)->p2.compareTo(p1)).collect(Collectors.toList());
		Iterator<Float> it = probs.iterator();
		int res = 0;
		float p = 0f;
		while(it.hasNext()) {
			Float probability = it.next();
			p=p+probability;
			if(r<=p) {
				res = list.stream().filter(n->n.getProbabilityOfSample() == probability).map(n->n.getProbabilityOfSample()).findFirst().get().intValue();
				break;
			}
		}
		return res;
	}

	@Override
	public String toString() {
		return "ProbabilisticRandomGenImpl [list=" + list + "]";
	}

	public static void main(String args[]) {
		var c1 = new ProbabilisticRandomGen.NumAndProbability(1, 45f);
		var c2 = new ProbabilisticRandomGen.NumAndProbability(2, 25f);
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

		for(int i=0;i<20;i++) {
			System.out.println("nextFromSample="+impl.nextFromSample());
		}
		
	}
	// use of a builder to have an immutable object
	public static class ProbabilisticRandomGenImplBuilder{
		private final List<NumAndProbability> list;
		
		public ProbabilisticRandomGenImplBuilder(List<NumAndProbability> list) {
			this.list = list;
		}
		public ProbabilisticRandomGenImpl build() {
			return new ProbabilisticRandomGenImpl(this);
		}
	}
}
