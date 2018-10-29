package estructuras;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Heap<T> implements PriorityQueue<T> {

	private ArrayList<T> data;
	private Comparator<T> comp;

	public ArrayList<T> getData()
	{
		return data;
	}
	public Heap(Comparator<T> comp) {
		data = new ArrayList<>();
		this.comp = comp;
	}

	@Override
	public void add(T elemento) {
		data.add(elemento);
		siftUp(size() - 1);
	}

	@Override
	public T peek() {
		return data.get(0);
	}

	@Override
	public T poll() {
		if (isEmpty())
			return null;
		if (size() == 1)
			return data.remove(0);

		T max = data.get(0);
		data.set(0, data.remove(size() - 1));
		siftDown(0);
		return max;
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}
		
	public void recalculate(T obj){
		int i;
		for(i = 0; i < size(); i++){
			if(data.get(i).equals(obj)){
				siftUp(i);
				siftDown(i);
				break;
			}
		}
		if(i == size())
			add(obj);
	}

	private void siftUp(int pos) {
		while (pos > 0 && comp.compare(data.get(pos), data.get((pos - 1) / 2)) > 0) {
			swap(pos, (pos - 1) / 2);
			pos = (pos - 1) / 2;
		}
	}

	private void siftDown(int pos) {
		while (2 * pos + 2 < size()) {
			int max = 2 * pos + 1;
			if (comp.compare(data.get(max), data.get(max + 1)) < 0)
				max++;
			if (comp.compare(data.get(pos), data.get(max)) > 0)
				break;
			swap(pos, max);
			pos = max;
		}
	}

	public T[] toArray(T[] type) {
		return data.toArray(type);
	}

	private void swap(int i, int j) {
		T temp = data.get(i);
		data.set(i, data.get(j));
		data.set(j, temp);
	}
	
	public String toString() {
		List<T> list = heapSort(size());
		String ans = "";
		for(T obj : list)
			ans += obj.toString() + "\n";
		return ans;
	}
	
	public List<T> heapSort(int size) {
		List<T> temp = data.subList(0, size);
		Heap<T> heap = new Heap<>(comp);
		for(T item : temp) heap.add(item);
		temp = new ArrayList<>(size);
		while(!heap.isEmpty()) temp.add(heap.poll());
		return temp;
	}
}
