package cn.org.orion.concurrent.program.Chapter04;

import java.util.AbstractList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/*
 * It is a thread safe and lock-free vector
 * This class implement algorithm from:<br>
 * 
 * lock-free Dynamically Resizable Arrays <br>
 * 
 * Damian Dechev, Peter Pirkelbauer, and Bjarne Stroustrup<br>
 * Texas A&M University College Station, TX 77843-3112<br>
 * {dechev, peter.pirkelbauer}@tamu.edu, bs@cs.tamu.edu
 * 
 * @link https://www.cnblogs.com/lizhiyan-world/p/6892003.html
 * 
 * @author Zhi Gan
 * 
 * @param <E> type of element in the vector
 * 
 */
public class LockFreeVector<E> extends AbstractList<E> {
	private static final boolean debug = false;
	
	private static final int FIRST_BUCKET_SIZE = 8;
	
	private static final int N_BUCKET = 30;
	
	private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;
	
	static class WriteDescriptor<E> {
		public E oldV;
		public E newV;
		public AtomicReferenceArray<E> addr;
		public int addr_ind;
		
		public WriteDescriptor(AtomicReferenceArray<E> addr, int addr_ind,
				E oldV, E newV) {
			this.addr = addr;
			this.addr_ind = addr_ind;
			this.oldV = oldV;
			this.newV = newV;
		}
		
		public void doIt() {
			addr.compareAndSet(addr_ind, oldV, newV);
		}
	}
	
	static class Descriptor<E> {
		public int size;
		volatile WriteDescriptor<E> writeop;
		
		public Descriptor(int size, WriteDescriptor<E> writeop) {
			this.size = size;
			this.writeop = writeop;
		}
		
		public void completeWrite() {
			WriteDescriptor<E> tmpOp = writeop;
			if(tmpOp != null) {
				tmpOp.doIt();
				writeop = null;
			}
		}
	}
	
	private AtomicReference<Descriptor<E>> descriptor;
	private static final int zeroNumFirst = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE);;
	
	public LockFreeVector() {
		buckets = new AtomicReferenceArray<AtomicReferenceArray<E>>(N_BUCKET);
		buckets.set(0, new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
		descriptor = new AtomicReference<Descriptor<E>>(new Descriptor<E>(0,
				null));
	}

	public void push_back(E e) {
		Descriptor<E> desc;
		Descriptor<E> newd;
		do {
			desc = descriptor.get();
			desc.completeWrite();
			//desc.size   Vector 本身的大小
			//FIRST_BUCKET_SIZE  第一个一位数组的大小
			int pos = desc.size + FIRST_BUCKET_SIZE;
			int zeroNumPos = Integer.numberOfLeadingZeros(pos);  // 取出pos 的前导领
			//zeroNumFirst  为FIRST_BUCKET_SIZE 的前导领
			int bucketInd = zeroNumFirst - zeroNumPos;  //哪个一位数组
			//判断这个一维数组是否已经启用
			if(buckets.get(bucketInd) == null) {
				//newLen  一维数组的长度
				int newLen = 2 * buckets.get(bucketInd - 1).length();
				if(debug)
					System.out.println("New Length is:" + newLen);
				buckets.compareAndSet(bucketInd, null, new AtomicReferenceArray<E>(newLen));
			}
			
			int idx = (0x80000000>>>zeroNumPos) ^ pos;  //异或
			newd = new Descriptor<E>(desc.size + 1, new WriteDescriptor<E>(
					buckets.get(bucketInd), idx, null, e));
		}while(!descriptor.compareAndSet(desc, newd));
		descriptor.get().completeWrite();
	}
	
	public E pop_back() {
		Descriptor<E> desc;
		Descriptor<E> newd;
		E elem;
		do {
			desc = descriptor.get();
			desc.completeWrite();
			
			int pos = desc.size + FIRST_BUCKET_SIZE - 1;
			int bucketInd = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
					- Integer.numberOfLeadingZeros(pos);
			int idx = Integer.highestOneBit(pos);
			elem = buckets.get(bucketInd).get(idx);
			newd = new Descriptor<E>(desc.size - 1, null);
		}while(!descriptor.compareAndSet(desc, newd));
		
		return elem;
	}
	
	public E get(int index) {
		int pos = index + FIRST_BUCKET_SIZE;
		int zeroNumPos = Integer.numberOfLeadingZeros(pos);
		int bucketInd = zeroNumFirst - zeroNumPos;
		int idx = (0x80000000>>>zeroNumPos) ^ pos;
		return buckets.get(bucketInd).get(idx);
	}
	
	public E set(int index, E e) {
		int pos = index + FIRST_BUCKET_SIZE;
		int bucketInd = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
				- Integer.numberOfLeadingZeros(pos);
		int idx = Integer.highestOneBit(pos) ^ pos;
		AtomicReferenceArray<E> bucket = buckets.get(bucketInd);
		while(true) {
			E oldV = bucket.get(idx);
			if(bucket.compareAndSet(idx, oldV, e))
				return oldV;
		}
	}
	
	public void reserve(int newSize) {
		int size = descriptor.get().size;
		int pos = size + FIRST_BUCKET_SIZE - 1;
		int i = Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
				- Integer.numberOfLeadingZeros(pos);
		if(i < 1)
			i = 1;
		
		int initialSize = buckets.get(i - 1).length();
		while(i < Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE)
				- Integer.numberOfLeadingZeros(newSize + FIRST_BUCKET_SIZE - 1)) {
			i++;
			initialSize *= FIRST_BUCKET_SIZE;
			buckets.compareAndSet(i, null, new AtomicReferenceArray<E>(
					initialSize));
		}
	}

	public int size() {
		return descriptor.get().size;
	}
	
	@Override
	public boolean add(E object) {
		push_back(object);
		return true;
	}

}
