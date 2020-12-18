public class OldStep {

    private final DoubleLinkedList.Node<Station> currentStation;
    private final OldStep lastStep;

    public OldStep(DoubleLinkedList.Node<Station> currentStation, OldStep lastStep) {
        this.currentStation = currentStation;
        this.lastStep = lastStep;
    }

    public DoubleLinkedList.Node<Station> getCurrentStation() {
        return currentStation;
    }

    public OldStep getLastStep() {
        return lastStep;
    }

}
