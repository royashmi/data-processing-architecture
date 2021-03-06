package msc_thesis.diogo_anjos.util;

/*
 * @author Diogo Anjos (diogo.silva.anjos@tecnico.ulisboa.pt)
 * MScThesis Solution:  Real-Time Data Processing Architecture 
 * 						for Energy Management Applications
 */

public class AppUtil {
	  
	public static long getConsumedMemory(boolean preRunGarbageCollector){
		Runtime rt = getRuntimeAndRunGC(preRunGarbageCollector);
		return bytesToMegabytes(rt.totalMemory()-rt.freeMemory());
	}
	
	public static long getTotalMemory(boolean preRunGarbageCollector){
		Runtime rt = getRuntimeAndRunGC(preRunGarbageCollector);
		return bytesToMegabytes(rt.totalMemory());
	}
	
	public static long getTotalMemory(){
		return bytesToMegabytes(Runtime.getRuntime().totalMemory());
	}
	
	public static long getMaxMemory(){
		return bytesToMegabytes(Runtime.getRuntime().maxMemory());
	}
	
	public static long getFreeMemory(boolean preRunGarbageCollector){
		Runtime rt = getRuntimeAndRunGC(preRunGarbageCollector);
		return bytesToMegabytes(rt.freeMemory());
	}

	public static String getMemoryStatus(boolean preRunGarbageCollector){
		getRuntimeAndRunGC(preRunGarbageCollector);
		return "# Memory(MB): Total="+getTotalMemory(false)+", Used="+getConsumedMemory(false)+", Free="+getFreeMemory(false); 
	}
	
	public static String getMemoryStatus(){
		return "# Memory(MB): MaxMem="+getMaxMemory()+", Used="+getConsumedMemory(false); 
	}	

	private static Runtime getRuntimeAndRunGC(boolean preRunGarbageCollector){
		Runtime rt = Runtime.getRuntime();
		if(preRunGarbageCollector){
			//long startTime = System.currentTimeMillis(); 										//DEBUG
			rt.gc();
			//System.out.println("GC takes "+(System.currentTimeMillis()-startTime)+" ms.");	//DEBUG
		}
		return rt;
	}
	
	private static long bytesToMegabytes(long bytes) {
		  long MEGABYTE = 1024L * 1024L;
		  return bytes / MEGABYTE;
	  }
}
