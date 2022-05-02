package chess.dao;

import chess.domain.Team;
import chess.dto.request.GameIdRequest;
import chess.dto.request.RoomRequest;
import chess.entity.RoomEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakeRoomDao implements RoomDao {

    private final Map<Long, RoomEntity> games = new HashMap<>();

    private long id = 0L;

    @Override
    public void makeGame(Team team, RoomRequest roomRequest) {
        id++;
        games.put(id, new RoomEntity(id, team, roomRequest.getName(), roomRequest.getPassword()));
    }

    @Override
    public List<RoomEntity> getGames() {
        return games.keySet()
                .stream()
                .map(games::get)
                .collect(Collectors.toList());
    }

    @Override
    public RoomEntity findById(RoomRequest roomRequest) {
        RoomEntity room = games
                .keySet()
                .stream()
                .filter(key -> games.get(key).getName()
                        .equals(roomRequest.getName()))
                .map(games::get)
                .findAny()
                .orElse(null);
        if (room != null) {
            return new RoomEntity(room.getId(), room.getStatus(), room.getName(), room.getPassword());
        }
        return null;
    }

    @Override
    public RoomEntity findById(GameIdRequest gameIdRequest) {
        return games.keySet()
                .stream()
                .filter(key -> Objects.equals(games.get(key).getId(), gameIdRequest.getId()))
                .map(games::get)
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean isExistId(GameIdRequest gameIdRequest) {
        return games.containsKey(gameIdRequest.getId());
    }

    @Override
    public void updateStatus(Team team, long roomId) {
        for (Long idx : games.keySet()) {
            updateIfAvailable(team, roomId, idx);
        }
    }

    private void updateIfAvailable(Team team, long roomId, Long idx) {
        if (games.get(idx).getId() == roomId) {
            games.put(idx, new RoomEntity(games.get(idx).getId(),
                    team, games.get(idx).getName(), games.get(idx).getPassword()));
        }
    }

    @Override
    public void deleteGame(long roomId) {
        games.remove(roomId);
    }
}
