package edu.baoss.orderservice.model;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class Flow implements Iterable<ProvisioningAction> {
    private List<ProvisioningAction> actionList;

    public ProvisioningAction get(String taskName) {
        return actionList.stream()
                .filter(action -> action.getTask().getTaskTemplate().getName().equals(taskName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterator<ProvisioningAction> iterator() {
        return new FlowIterator();
    }

    private class FlowIterator implements Iterator<ProvisioningAction> {
        private final Flow copy;
        private int currentIndex;

        public FlowIterator() {
            this.copy = new Flow(new ArrayList<>(actionList));
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return !copy.actionList.isEmpty();
        }

        @Override
        public ProvisioningAction next() {
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            copy.getActionList().remove(currentIndex);
            int taskWithoutDepsIndex = copy.getActionList().stream()
                    .filter(action -> CollectionUtils.isEmpty(action.getTask().getTaskTemplate().getDependencies()))
                    .findFirst()
                    .map(candidate -> actionList.indexOf(candidate))
                    .orElse(-1);
            if (taskWithoutDepsIndex != -1) {
                currentIndex = taskWithoutDepsIndex;
                return actionList.get(taskWithoutDepsIndex);
            }
            int candidateIndex = copy.getActionList().stream()
                    .filter(action -> action.getTask().getDependencies().stream()
                            .noneMatch(dep -> copy.getActionList().contains(copy.get(dep.getTaskTemplate().getName()))))
                    .findFirst()
                    .map(candidate -> actionList.indexOf(candidate))
                    .orElse(-1);
            if (candidateIndex == -1) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return actionList.get(candidateIndex);
        }

    }

}
