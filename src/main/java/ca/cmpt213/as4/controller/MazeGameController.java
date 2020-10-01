package ca.cmpt213.as4.controller;

import ca.cmpt213.as4.model.GameCode;
import ca.cmpt213.as4.model.MazeGame;
import ca.cmpt213.as4.restapi.ApiBoardWrapper;
import ca.cmpt213.as4.restapi.ApiGameWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class MazeGameController {
    private List<MazeGame> games = new ArrayList<>();
    private AtomicInteger nextId = new AtomicInteger();

    @GetMapping("/api/about")
    @ResponseStatus(HttpStatus.OK)
    public String getAboutMessage() {
        return "CMPT 213 - Jason Nguyen";
    }

    @GetMapping("/api/games")
    @ResponseStatus(HttpStatus.OK)
    public List<ApiGameWrapper> getAllGames() {
        List<ApiGameWrapper> list = new ArrayList<>();
        for (MazeGame game: games) {
            list.add(ApiGameWrapper.makeFromGame(game, game.getGameId()));
        }

        return list;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createNewGame() {
        MazeGame game = new MazeGame(nextId.incrementAndGet());
        games.add(game);

        return ApiGameWrapper.makeFromGame(game, game.getGameId());
    }

    @GetMapping("api/games/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiGameWrapper getGame(@PathVariable("id") int gameId) {
        for (MazeGame game: games) {
            if (game.getGameId() == gameId) {
                return ApiGameWrapper.makeFromGame(game, game.getGameId());
            }
        }

        throw new IllegalArgumentException();
    }

    @GetMapping("api/games/{id}/board")
    @ResponseStatus(HttpStatus.OK)
    public ApiBoardWrapper getBoard(@PathVariable("id") int gameId) {
        for (MazeGame game: games) {
            if (game.getGameId() == gameId) {
                return ApiBoardWrapper.makeFromGame(game);
            }
        }

        throw new IllegalArgumentException();
    }

    @PostMapping("api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeMoves(@PathVariable("id") int gameId, @RequestBody String move) {
        for (MazeGame game: games) {
            if (game.getGameId() == gameId) {
                int result;
                switch (move) {
                    case "MOVE_UP":
                        result = game.makeMoves(-1, 0);
                        if (result == GameCode.INVALID_MOVE_THROUGH_WALLS) {
                            throw new IllegalStateException();
                        }
                        return;
                    case "MOVE_DOWN":
                        result = game.makeMoves(+1, 0);
                        if (result == GameCode.INVALID_MOVE_THROUGH_WALLS) {
                            throw new IllegalStateException();
                        }
                        return;
                    case "MOVE_LEFT":
                        result = game.makeMoves(0, -1);
                        if (result == GameCode.INVALID_MOVE_THROUGH_WALLS) {
                            throw new IllegalStateException();
                        }
                        return;
                    case "MOVE_RIGHT":
                        result = game.makeMoves(0, +1);
                        if (result == GameCode.INVALID_MOVE_THROUGH_WALLS) {
                            throw new IllegalStateException();
                        }
                        return;
                    case "MOVE_CATS":
                        game.moveCats();
                        return;
                    default:
                        throw new IllegalStateException();
                }
            }
        }

        throw new IllegalArgumentException();
    }

    @PostMapping("api/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void useCheat(@PathVariable("id") int gameId, @RequestBody String cheat) {
        for (MazeGame game: games) {
            if (game.getGameId() == gameId) {
                switch (cheat) {
                    case "1_CHEESE":
                        game.getMazeCtrl().setNumCheese(1);
                        return;
                    case "SHOW_ALL":
                        game.getUI().revealAll();
                        return;
                    default:
                        throw new IllegalStateException();
                }

            }
        }

        throw new IllegalArgumentException();
    }

    // Create Exception Handle
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {}

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal moves.")
    @ExceptionHandler(IllegalStateException.class)
    public void badMovesExceptionHandler() {}
}
