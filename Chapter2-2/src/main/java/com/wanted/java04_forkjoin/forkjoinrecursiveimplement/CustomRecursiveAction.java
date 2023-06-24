package com.wanted.java04_forkjoin.forkjoinrecursiveimplement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private final String workLoad;

    public CustomRecursiveAction(final String workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {
        if (workLoad.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(workLoad);
        }
    }


    private Collection<CustomRecursiveAction> createSubtasks() {
        final List<CustomRecursiveAction> subtasks = new ArrayList<>();

        final String partOne = workLoad.substring(0, workLoad.length() / 2);
        final String partTwo = workLoad.substring(workLoad.length() / 2, workLoad.length());

        subtasks.add(new CustomRecursiveAction(partOne));
        subtasks.add(new CustomRecursiveAction(partTwo));

        return subtasks;
    }

    private void processing(String work) {
        final String result = work.toUpperCase();
        final String currentThreadName = Thread.currentThread().getName();

        System.out.println("This result - (" + result + ") - was processed by " + currentThreadName);
    }
}
