package com.txoksue.bustime.handler;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class VerifySkillHandler extends SkillStreamHandler {

	private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                    new CancelandStopIntentHandler(),
                    new BusTimeIntentHandler(),
                    new LaunchRequestHandler())
                .build();
    }

    public VerifySkillHandler() {
        super(getSkill());
    }


}
