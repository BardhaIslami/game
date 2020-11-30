package com.assignment.app.maingame.gamemoves.playinglogic;

import java.util.function.Function;

import com.assignment.app.maingame.gamedata.activity.Output;

@FunctionalInterface
public interface WinningLogicInterface extends Function<Output, Boolean> {

}
