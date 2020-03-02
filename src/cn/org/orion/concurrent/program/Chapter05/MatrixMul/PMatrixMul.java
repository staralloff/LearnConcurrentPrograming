package cn.org.orion.concurrent.program.Chapter05.MatrixMul;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;

public class PMatrixMul {
	public static final int granularity=3;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Matrix m1 = MatrixFactory.getRandomMatrix(4, 2, null);
		Matrix m2 = MatrixFactory.getRandomMatrix(2, 4, null);
		MatrixMulTask task = new MatrixMulTask(m1,m2,null);
		ForkJoinTask<Matrix> result = forkJoinPool.submit(task);
		Matrix pr = result.get();
		System.out.println(pr);
	}

}
