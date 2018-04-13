package com.example.dinhtrieu.tictactoenegamaxab.dm;

/**
 * Created by dinhtrieu on 4/13/18.
 */

public class ServerMessage {
    private Move move;
    private GameStatus gameStatus;
    private RolePlayer rolePlayer;

    public ServerMessage() {
    }

    public ServerMessage(String value) {
        String[] parts = value.split("-");
        int status = Integer.parseInt(parts[0]);
        String role = parts[1];
        this.move = new Move(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));

        switch (status) {
            case 0:
                this.gameStatus = GameStatus.CREATED;
                break;
            case 1:
                this.gameStatus = GameStatus.DOING;
                break;
            case 2:
                this.gameStatus = GameStatus.WIN;
                break;
            case 3:
                this.gameStatus = GameStatus.LOOSE;
                break;
            case 4:
                this.gameStatus = GameStatus.DRAW;
                break;
        }

        switch (role.toLowerCase()) {
            case "playera":
                rolePlayer = RolePlayer.PLAYERA;
                break;
            case "playerb":
                rolePlayer = RolePlayer.PLAYERB;
                break;
        }
    

    }

    public ServerMessage(Move move, GameStatus gameStatus, RolePlayer rolePlayer) {
        this.move = move;
        this.gameStatus = gameStatus;
        this.rolePlayer = rolePlayer;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public RolePlayer getRolePlayer() {
        return rolePlayer;
    }

    public void setRolePlayer(RolePlayer rolePlayer) {
        this.rolePlayer = rolePlayer;
    }
}
