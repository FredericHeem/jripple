package org.opencoin.client;

import org.slf4j.Logger;

import com.stateforge.statemachine.listener.IObserver;

public class StateMachineObserver implements IObserver {

	private Logger log;
	
	public StateMachineObserver(Logger log){
		this.log = log;
	}
	@Override
	public void onEntry(String context, String stateName) {
		log.debug("onEntry " + context + ", state " + stateName);
	}

	@Override
	public void onExit(String context, String stateName) {
		log.debug("onExit " + context + ", state " + stateName);
	}

	@Override
	public void onTimerStart(String arg0, String arg1, long arg2) {
	}

	@Override
	public void onTimerStop(String arg0, String arg1) {
	}

	@Override
	public void onTransitionBegin(String context, String from, String to,
			String transition) {
		log.debug("onTransitionBegin " + context +
				", from " + from + " to " + to + ", transition: " + transition);;
	}

	@Override
	public void onTransitionEnd(String arg0, String arg1, String arg2,
			String arg3) {
	}

}
