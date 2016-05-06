package Implementation;

import Interfaces.Executor;
import Interfaces.Task;
import Interfaces.Validator;

import java.util.ArrayList;
import java.util.List;


public class ExecutorImp<T> implements Executor<T> {


    private class Entry<E> {

        Task<E> task;
        Validator<E> validator;

        Entry(Task<E> task, Validator<E> validator) {
            this.task = task;
            this.validator = validator;
        }
    }


    private List<Entry<T>> tasks = new ArrayList<>();
    private List<T> validResults = new ArrayList<>();
    private List<T> invalidResults = new ArrayList<>();
    private boolean wasExecuted;



    @Override
    public void addTask(Task task) {
        tasks.add(new Entry<>(task, null));
    }

    @Override
    public void addTask(Task task, Validator validator) {
        tasks.add(new Entry<>(task, validator));
    }

    @Override
    public void execute() {
        if (wasExecuted) return;
        wasExecuted = true;

        validResults = new ArrayList<>();
        invalidResults = new ArrayList<>();

        for (Entry<T> task: tasks) {

            if (task.validator == null) {

                task.task.execute();
                validResults.add(task.task.getResult());

            } else {

                task.task.execute();

                if (task.validator.isValid(task.task.getInput())) {
                    validResults.add(task.task.getResult());
                } else {
                    invalidResults.add(task.task.getResult());
                }
            }
        }
    }

    @Override
    public List getValidResults() {
        if (wasExecuted) {
            return validResults;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public List getInvalidResults() {
        if (wasExecuted) {
            return invalidResults;
        } else {
            throw new IllegalStateException();
        }
    }
}
