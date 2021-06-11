@FlowParams
(idTest = "T0001_Sample",
goal = "Validate user authentication",
suiteClass = SampleSuite.class)

public class T001SampleFlow extends BaseFlow {

	@Override
	protected void addFlowStages() {
		addStage(new SampleStage());
		//... Add new Stages here
	}
}