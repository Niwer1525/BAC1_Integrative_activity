package hexmo.views.effect;

import org.helmo.swing.effect.CompletableTask;

import java.util.function.BooleanSupplier;

public class DelegateEffect implements CompletableTask {
    private final Runnable action;
    private final BooleanSupplier predicate;

    public DelegateEffect(Runnable action, BooleanSupplier predicate) {
        this.predicate = predicate;
        this.action = action;
    }

    @Override
    public void update() {
        if(!isDone()) {
            action.run();
        }
    }

    @Override
    public boolean isDone() {
        return predicate.getAsBoolean();
    }
}
