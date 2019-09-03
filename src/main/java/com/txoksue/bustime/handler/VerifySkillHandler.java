package com.txoksue.bustime.handler;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.txoksue.bustime.interceptor.LogRequestInterceptor;
import com.txoksue.bustime.interceptor.LogResponseInterceptor;

public class VerifySkillHandler extends SkillStreamHandler {
	
	//Forcing to load the logging file configuration lambda-log4j2.xml
	static {
	    System.setProperty("log4j.configurationFile", "lambda-log4j2.xml");
	}

	private static Skill getSkill() {
        return Skills.standard()
        			.withSkillId("amzn1.ask.skill.bd3b7784-8b70-484a-8415-61892c5a8ab9")
        			.addRequestInterceptor(new LogRequestInterceptor())
        			.addResponseInterceptor(new LogResponseInterceptor())
        			.addExceptionHandler(new ExceptionsHandler())
                .addRequestHandlers(
                	    new LaunchRequestHandler(), 
                	    new HelpIntentHandler(),
                    new CancelandStopIntentHandler(),
                    new SessionEndedRequestHandler(),
                    new BusTimeIntentHandler())
                .build();
    }

    public VerifySkillHandler() {
        super(getSkill());
    }


}
