package pertsev.VCS.Commit;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class CommitQueue implements Queue<Commit> {
    Queue<Commit> queue = new LinkedList<>();

    void load(Iterable<Commit> iterable) {
        queue.clear();

        for (Commit commit : iterable)
            queue.add(commit);
    }

    public Commit getPenultCommit() {
        if (queue.isEmpty()) return null;
        Queue<Commit> buffer = new LinkedList<>();
        Commit penultCommit = null;

        while (!queue.isEmpty()) {
            if (queue.size() == 2) {
                penultCommit = queue.peek();
            }
            buffer.add(queue.poll());
        }

        while (!buffer.isEmpty()) queue.add(buffer.poll());

        return penultCommit;
    }

    public Commit getLastCommit() {
        if (queue.isEmpty()) return null;
        Queue<Commit> buffer = new LinkedList<>();
        Commit lastCommit = null;

        while (!queue.isEmpty()) {
            if (queue.size() == 1) {
                lastCommit = queue.peek();
            }
            buffer.add(queue.poll());
        }

        while (!buffer.isEmpty()) queue.add(buffer.poll());

        return lastCommit;
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }


    @Override
    public Iterator<Commit> iterator() {
        return queue.iterator();
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

    @Override
    public boolean add(Commit commit) {
        return queue.add(commit);
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Commit> c) {
        return queue.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public boolean offer(Commit commit) {
        return queue.offer(commit);
    }

    @Override
    public Commit remove() {
        return queue.remove();
    }

    @Override
    public Commit poll() {
        return queue.poll();
    }

    @Override
    public Commit element() {
        return queue.element();
    }

    @Override
    public Commit peek() {
        return queue.peek();
    }
}
