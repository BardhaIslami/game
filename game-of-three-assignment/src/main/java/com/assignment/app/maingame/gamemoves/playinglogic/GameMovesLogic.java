package com.assignment.app.maingame.gamemoves.playinglogic;

import java.util.function.Function;

import com.assignment.app.maingame.gamedata.activity.Output;
import com.assignment.app.maingame.gamemoves.activity.InputState;
import com.assignment.app.maingame.gamemoves.playinglogic.validation.CustomizedValidators;

// Interface that defines game moves or rounds logic.

public interface GameMovesLogic extends Function<InputState, Output>, CustomizedValidators<InputState>  {

}
