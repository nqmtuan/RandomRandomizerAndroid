package com.zimerick.zmrklibrary.zmrkdatasync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ZMRKDataRequestQueue extends Observable
{

	
	public static enum QueuePriority {QUEUE_PRIORITY_LOW, QUEUE_PRIORITY_MEDIUM, QUEUE_PRIORITY_HIGH};
	
	private List <ZMRKDataRequestQueueObject> highPriorityQueue;
	private List <ZMRKDataRequestQueueObject> medPriorityQueue;
	private List <ZMRKDataRequestQueueObject> lowPriorityQueue;
	
	
	public ZMRKDataRequestQueue() {
		highPriorityQueue = new ArrayList<ZMRKDataRequestQueueObject>();
		medPriorityQueue = new ArrayList<ZMRKDataRequestQueueObject>();
		lowPriorityQueue = new ArrayList<ZMRKDataRequestQueueObject>();
	}
	
	
	public void push (ZMRKDataRequestQueueObject obj, QueuePriority priority)
	{
		if (priority == QueuePriority.QUEUE_PRIORITY_HIGH)
			highPriorityQueue.add(obj);
		if (priority == QueuePriority.QUEUE_PRIORITY_MEDIUM)
			medPriorityQueue.add(obj);		
		if (priority == QueuePriority.QUEUE_PRIORITY_LOW)
			lowPriorityQueue.add(obj);
		this.setChanged();
		this.notifyObservers();
	}
	

	public ZMRKDataRequestQueueObject pop ()
	{
		ZMRKDataRequestQueueObject obj = null;
		if (highPriorityQueue.size() > 0)
		{
			obj = highPriorityQueue.get(0);
			highPriorityQueue.remove(0);
		}
		else if (medPriorityQueue.size() > 0)
		{
			obj = medPriorityQueue.get(0);
			medPriorityQueue.remove(0);
		}
		else if (lowPriorityQueue.size() > 0)
		{
			obj = lowPriorityQueue.get(0);
			lowPriorityQueue.remove (0);
		}
		
		if (obj != null)
		{
			this.setChanged();
			this.notifyObservers();
		}
		return obj;
	}
	
	public ZMRKDataRequestQueueObject peek ()
	{
		ZMRKDataRequestQueueObject obj = null;
		if (highPriorityQueue.size() > 0)
			obj = highPriorityQueue.get(0);
		else if (medPriorityQueue.size() > 0)
			obj = medPriorityQueue.get(0);
		else if (lowPriorityQueue.size() > 0)
			obj = lowPriorityQueue.get(0);		
		return obj;		
	}
	
	
	//Empty the whole queue
	public void empty ()
	{
		if (highPriorityQueue.size() + medPriorityQueue.size() + lowPriorityQueue.size() > 0)
		{
			this.setChanged();
			this.notifyObservers();
		}	
		highPriorityQueue.clear();
		medPriorityQueue.clear();
		lowPriorityQueue.clear();		
	}
	
	public int size ()
	{		
		int size1 = highPriorityQueue.size();
		int size2 = medPriorityQueue.size();
		int size3 = lowPriorityQueue.size();
		return size1 + size2 + size3;
	}
	
	@Override
	public void addObserver (Observer observer)
	{
		super.addObserver(observer);
	}
	
	
	@Override
	public void notifyObservers ()
	{
		HashMap <String, Object> notification = new HashMap<String, Object> ();
		notification.put("Sender", "ZMRKDataRequestQueue");
		notification.put("Size", size());
		super.notifyObservers(notification);
	}
	
}
