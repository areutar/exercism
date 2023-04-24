import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Tournament {
    private static final String HEADER = "Team                           | MP |  W |  D |  L |  P\n";
    private final List<TeamResults> resultRepo = new ArrayList<>();

    String printTable() {
        return HEADER + resultRepo.stream()
                .map(TeamResults::toString).collect(Collectors.joining());
    }

    void applyResults(String resultString) {
        Arrays.stream(resultString.split("\n")).forEach(this::parseResultString);
        Collections.sort(resultRepo);
    }

    private void parseResultString(String s) {
        String[] tokens = s.split(";");
        TeamResults teamResults1 = getTeamResultsByName(tokens[0]);
        TeamResults teamResults2 = getTeamResultsByName(tokens[1]);
        add(teamResults1);
        add(teamResults2);
        TeamResults.Result result = TeamResults.Result.findResult(tokens[2]);

        teamResults1.setMp(teamResults1.getMp() + 1);
        teamResults2.setMp(teamResults2.getMp() + 1);

        switch (result) {
            case WIN: {
                teamResults1.setW(teamResults1.getW() + 1);
                teamResults1.setP(teamResults1.getP() + 3);

                teamResults2.setL(teamResults2.getL() + 1);
                break;
            }

            case LOSS: {
                teamResults1.setL(teamResults1.getL() + 1);

                teamResults2.setW(teamResults2.getW() + 1);
                teamResults2.setP(teamResults2.getP() + 3);
                break;
            }

            case DRAW: {
                teamResults1.setD(teamResults1.getD() + 1);
                teamResults1.setP(teamResults1.getP() + 1);

                teamResults2.setD(teamResults2.getD() + 1);
                teamResults2.setP(teamResults2.getP() + 1);
                break;
            }
        }
    }

    private void add(TeamResults teamResults) {
        if (!resultRepo.contains(teamResults)) {
            resultRepo.add(teamResults);
        }
    }

    private TeamResults getTeamResultsByName(String token) {
        return resultRepo.stream()
                .filter(teamResults -> teamResults.getName().equals(token))
                .findAny().orElse(new TeamResults(token));
    }
}
