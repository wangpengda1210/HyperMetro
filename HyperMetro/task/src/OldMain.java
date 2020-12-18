import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

public class OldMain {

    static HashMap<String, Metro> metros = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("ERROR");
            return;
        }

        JsonObject input = Util.readJsonData(args[0]);
        if (input == null) {
            System.out.println("Incorrect file");
            return;
        }

        getStations(input);

        showMenu();

    }

    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        menuLoop:while (true) {
            String[] command = scanner.nextLine().split(" ");
            if (command.length == 0) {
                System.out.println("Invalid command");
                continue;
            }

            ArrayList<String> params = parseCommand(command);

            switch (command[0].toLowerCase()) {
                case "/append":
                    if (params.size() != 2 && params.size() != 3) {
                        System.out.println("Invalid command");
                    } else {
                        String key = params.get(0);
                        if (!metros.containsKey(key)) {
                            System.out.println("Invalid command");
                        } else {
                            Metro metro = metros.get(key);
                            if (params.size() == 2) {
                                metro.getStations().addLast(new Station(params.get(1), metro.getName(), 0));
                            } else {
                                metro.getStations().addLast(new Station(params.get(1), metro.getName(),
                                        Integer.parseInt(params.get(2))));
                            }
                        }
                    }
                    break;
                case "/add-head":
                    if (params.size() != 2 && params.size() != 3) {
                        System.out.println("Invalid command");
                    } else {
                        String key = params.get(0);
                        if (!metros.containsKey(key)) {
                            System.out.println("Invalid command");
                        } else {
                            Metro metro = metros.get(key);
                            if (params.size() == 2) {
                                metro.getStations().addFirst(new Station(params.get(1), metro.getName(), 0));
                            } else {
                                metro.getStations().addFirst(new Station(params.get(1), metro.getName(),
                                        Integer.parseInt(params.get(2))));
                            }
                        }
                    }
                    break;
                case "/remove":
                    if (params.size() != 2) {
                        System.out.println("Invalid command");
                    } else {
                        String key = params.get(0);
                        if (!metros.containsKey(key)) {
                            System.out.println("Invalid command");
                        } else {
                            Metro metro = metros.get(key);
                            DoubleLinkedList<Station> metroStations = metro.getStations();
                            DoubleLinkedList.Node<Station> station = metroStations
                                    .find(new Station(params.get(1), metro.getName(), 0));
                            if (station == null) {
                                System.out.println("Invalid command");
                            } else {
                                metroStations.remove(station);
                            }
                        }
                    }
                    break;
                case "/output":
                    if (params.size() != 1) {
                        System.out.println("Invalid command");
                    } else {
                        String key = params.get(0);
                        if (!metros.containsKey(key)) {
                            System.out.println("Invalid command");
                        } else {
                            printStations(metros.get(key).getStations());
                        }
                    }
                    break;
                case "/connect":
                    if (params.size() != 4) {
                        System.out.println("Invalid command");
                    } else {
                        Metro firstMetro = metros.get(params.get(0));
                        Metro secondMetro = metros.get(params.get(2));

                        if (firstMetro == null || secondMetro == null) {
                            System.out.println("Invalid command");
                        } else {
                            DoubleLinkedList.Node<Station> firstStation = firstMetro.getStations().
                                    find(new Station(params.get(1), firstMetro.getName(), 0));
                            DoubleLinkedList.Node<Station> secondStation = secondMetro.getStations().
                                    find(new Station(params.get(3), secondMetro.getName(), 0));

                            if (firstStation == null || secondStation == null) {
                                System.out.println("Invalid command");
                            } else {
                                firstStation.getValue()
                                        .addTransfer(new Transfer(params.get(2), params.get(3)));
                                secondStation.getValue()
                                        .addTransfer(new Transfer(params.get(0), params.get(1)));
                            }
                        }
                    }
                    break;
                case "/route":
                    if (params.size() != 4) {
                        System.out.println("Invalid command");
                    } else {
                        Metro firstMetro = metros.get(params.get(0));
                        Metro secondMetro = metros.get(params.get(2));

                        if (firstMetro == null || secondMetro == null) {
                            System.out.println("Invalid command");
                        } else {
                            DoubleLinkedList.Node<Station> firstStation = firstMetro.getStations().
                                    find(new Station(params.get(1), firstMetro.getName(), 0));
                            DoubleLinkedList.Node<Station> secondStation = secondMetro.getStations().
                                    find(new Station(params.get(3), secondMetro.getName(), 0));

                            if (firstStation == null || secondStation == null) {
                                System.out.println("Invalid command");
                            } else {
                                DoubleLinkedList<String> route = findRoute(firstStation, secondStation);
                                if (route == null) {
                                    System.out.println("No route found");
                                } else {
                                    DoubleLinkedList.Node<String> current = route.getHead();
                                    while (current != null) {
                                        System.out.println(current.getValue());
                                        current = current.getNext();
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "/fastest-route":
                    if (params.size() != 4) {
                        System.out.println("Invalid command");
                    } else {
                        Metro firstMetro = metros.get(params.get(0));
                        Metro secondMetro = metros.get(params.get(2));

                        if (firstMetro == null || secondMetro == null) {
                            System.out.println("Invalid command");
                        } else {
                            DoubleLinkedList.Node<Station> firstStation = firstMetro.getStations().
                                    find(new Station(params.get(1), firstMetro.getName(), 0));
                            DoubleLinkedList.Node<Station> secondStation = secondMetro.getStations().
                                    find(new Station(params.get(3), secondMetro.getName(), 0));

                            if (firstStation == null || secondStation == null) {
                                System.out.println("Invalid command");
                            } else {
                                Pair<DoubleLinkedList<String>, Integer> route =
                                        findFastestRoute(firstStation, secondStation);
                                if (route == null) {
                                    System.out.println("No route found");
                                } else {
                                    DoubleLinkedList.Node<String> current = route.getFirst().getHead();
                                    while (current != null) {
                                        System.out.println(current.getValue());
                                        current = current.getNext();
                                    }
                                    if (route.getSecond() != 0) {
                                        System.out.println("Total: " + route.getSecond() + " minutes in the way");
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "/exit":
                    break menuLoop;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private static Pair<DoubleLinkedList<String>, Integer> findFastestRoute(DoubleLinkedList.Node<Station> firstStation,
                                                                            DoubleLinkedList.Node<Station> secondStation) {

        HashMap<Station, Pair<Integer, Station>> fastestRoute = new HashMap<>();

        for (Metro metro : metros.values()) {
            DoubleLinkedList.Node<Station> stationNode = metro.getStations().getHead();
            while (stationNode != null) {
                fastestRoute.put(stationNode.getValue(), new Pair<>(Integer.MAX_VALUE, null));
                stationNode = stationNode.getNext();
            }
        }

        if (fastestRoute.keySet().stream().allMatch(station -> station.getTime() == 0)) {
            return new Pair<>(findRoute(firstStation, secondStation), 0);
        }

        ArrayList<Station> notSearched = new ArrayList<>(fastestRoute.keySet());

        fastestRoute.put(firstStation.getValue(), new Pair<>(0, null));
        notSearched.remove(firstStation.getValue());

        DoubleLinkedList.Node<Station> currentStation = firstStation;

        while (notSearched.contains(secondStation.getValue())) {
            int timeToCurrentStation = fastestRoute.get(currentStation.getValue()).getFirst();

            if (currentStation.getNext() != null && notSearched.contains(currentStation.getNext().getValue()) &&
                    timeToCurrentStation + currentStation.getValue().getTime() <
                            fastestRoute.get(currentStation.getNext().getValue()).getFirst()) {
                fastestRoute.put(currentStation.getNext().getValue(),
                        new Pair<>(timeToCurrentStation + currentStation.getValue().getTime(),
                                currentStation.getValue()));
            }

            if (currentStation.getPrev() != null && notSearched.contains(currentStation.getPrev().getValue()) &&
                    timeToCurrentStation + currentStation.getPrev().getValue().getTime() <
                            fastestRoute.get(currentStation.getPrev().getValue()).getFirst()) {
                fastestRoute.put(currentStation.getPrev().getValue(),
                        new Pair<>(timeToCurrentStation + currentStation.getPrev().getValue().getTime(),
                                currentStation.getValue()));
            }

            for (Transfer transfer : currentStation.getValue().getTransfers()) {
                DoubleLinkedList.Node<Station> transferStation = metros.get(transfer.getLine())
                        .getStations().find(new Station(transfer.getStation(), transfer.getLine(), 0));

                if (transferStation == null) {
                    return null;
                } else {
                    if (notSearched.contains(transferStation.getValue()) &&
                            timeToCurrentStation + 5 < fastestRoute.get(transferStation.getValue()).getFirst()) {
                        fastestRoute.put(transferStation.getValue(),
                                new Pair<>(timeToCurrentStation + 5, currentStation.getValue()));
                    }
                }
            }

            DoubleLinkedList.Node<Station> minStation = metros.get(notSearched.get(0).getLine())
                    .getStations().find(notSearched.get(0));
            for (Station station : notSearched) {
                if (fastestRoute.get(station).getFirst() < fastestRoute.get(minStation.getValue()).getFirst()) {
                    minStation = metros.get(station.getLine()).getStations().find(station);
                }
            }
            currentStation = minStation;
            notSearched.remove(minStation.getValue());
        }

        if (fastestRoute.get(secondStation.getValue()).getFirst() == Integer.MAX_VALUE) {
            return null;
        } else {

            DoubleLinkedList<String> route = new DoubleLinkedList<>();
            Pair<Integer, Station> step = fastestRoute.get(secondStation.getValue());

            route.addFirst(secondStation.getValue().getName());

            while (step.getSecond() != null) {
                route.addFirst(step.getSecond().getName());

                Pair<Integer, Station> lastStep = fastestRoute.get(step.getSecond());

                if (lastStep.getSecond() != null &&
                        !lastStep.getSecond().getLine().equals(step.getSecond().getLine())) {
                    route.addFirst(step.getSecond().getLine());
                }

                step = lastStep;
            }

            return new Pair<>(route, fastestRoute.get(secondStation.getValue()).getFirst());
        }

    }

    private static DoubleLinkedList<String> findRoute(DoubleLinkedList.Node<Station> firstStation,
                                                      DoubleLinkedList.Node<Station> secondStation) {
        LinkedList<OldStep> oldSteps = new LinkedList<>();
        ArrayList<Station> searched = new ArrayList<>();

        oldSteps.offer(new OldStep(firstStation, null));
        searched.add(firstStation.getValue());

        OldStep resultOldStep = null;

        while (oldSteps.peek() != null) {
            OldStep currentOldStep = oldSteps.poll();
            if (currentOldStep.getCurrentStation().getValue().equals(secondStation.getValue())) {
                resultOldStep = currentOldStep;
                break;
            }

            DoubleLinkedList.Node<Station> nextStation = currentOldStep.getCurrentStation().getNext();
            DoubleLinkedList.Node<Station> prevStation = currentOldStep.getCurrentStation().getPrev();
            if (nextStation != null && !searched.contains(nextStation.getValue())) {
                oldSteps.offer(new OldStep(nextStation, currentOldStep));
                searched.add(nextStation.getValue());
            }

            if (prevStation != null && !searched.contains(prevStation.getValue())) {
                oldSteps.offer(new OldStep(prevStation, currentOldStep));
                searched.add(prevStation.getValue());
            }

            for (Transfer transfer : currentOldStep.getCurrentStation().getValue().getTransfers()) {
                DoubleLinkedList.Node<Station> transferStation = metros.get(transfer.getLine())
                        .getStations().find(new Station(transfer.getStation(), transfer.getLine(), 0));

                if (transferStation == null) {
                    return null;
                } else {
                    if (!searched.contains(transferStation.getValue())) {
                        oldSteps.offerFirst(new OldStep(transferStation, currentOldStep));
                        searched.add(transferStation.getValue());
                    }
                }
            }
        }

        if (resultOldStep == null) {
            return null;
        } else {
            DoubleLinkedList<String> route = new DoubleLinkedList<>();

            while (resultOldStep != null) {
                route.addFirst(resultOldStep.getCurrentStation().getValue().getName());
                OldStep lastOldStep = resultOldStep.getLastStep();
                if (lastOldStep != null && !resultOldStep.getCurrentStation().getValue().getLine()
                        .equals(lastOldStep.getCurrentStation().getValue().getLine())) {
                    route.addFirst(resultOldStep.getCurrentStation().getValue().getLine());
                }
                resultOldStep = lastOldStep;
            }

            return route;
        }
    }

    private static void printStations(DoubleLinkedList<Station> stations) {
        DoubleLinkedList.Node<Station> curr = stations.getHead();

        StringBuilder sb = new StringBuilder();

        while (curr != null) {
            sb.append(curr.getPrev() == null ? "depot" : curr.getPrev().getValue().getName())
                    .append(" - ").append(curr.getValue().getName()).append(" - ")
                    .append(curr.getNext() == null ? "depot" : curr.getNext().getValue().getName());

            for (Transfer transfer : curr.getValue().getTransfers()) {
                sb.append(" - ").append(transfer.getStation()).append(" (").append(transfer.getLine()).append(")");
            }

            sb.append("\n");
            curr = curr.getNext();
        }

        System.out.println(sb.toString());
    }

    private static ArrayList<String> parseCommand(String[] command) {
        ArrayList<String> params = new ArrayList<>();
        if (command.length < 2) {
            return params;
        }

        int endOfLastParam = 1;
        while (endOfLastParam != command.length) {
            if (command[endOfLastParam].startsWith("\"")) {
                if (command[endOfLastParam].endsWith("\"")) {
                    params.add(command[endOfLastParam].replace("\"", ""));
                } else {
                    StringBuilder sb = new StringBuilder(command[endOfLastParam]).append(" ");
                    while (endOfLastParam < command.length - 1) {
                        endOfLastParam++;
                        sb.append(command[endOfLastParam]).append(" ");
                        if (command[endOfLastParam].endsWith("\"")) {
                            break;
                        }
                    }
                    if (!sb.toString().endsWith("\" ")) {
                        return params;
                    }
                    params.add(sb.toString().trim().replace("\"", ""));
                }
            } else {
                params.add(command[endOfLastParam].replace("\"", ""));
            }
            endOfLastParam++;
        }

        return params;
    }

    private static void getStations(JsonObject input) {
        for (String entry : input.keySet()) {
            Metro metro = new Metro(entry);
            JsonObject metroJo = input.get(entry).getAsJsonObject();
            int stationPosition = 1;

            while (metroJo.get(String.valueOf(stationPosition)) != null) {
                JsonObject stationJo = metroJo.get(String.valueOf(stationPosition)).getAsJsonObject();
                String stationName = stationJo.get("name").getAsString();
                JsonElement timeJe = stationJo.get("time");
                int transferTime = 0;
                if (timeJe != null && timeJe.isJsonPrimitive()) {
                    transferTime = stationJo.get("time").getAsInt();
                }
                Station station = new Station(stationName, entry, transferTime);

                JsonElement stationTransfers = stationJo.get("transfer");
                if (stationTransfers.isJsonArray()) {
                    for (JsonElement transferJe : stationTransfers.getAsJsonArray()) {
                        JsonObject transferJo = transferJe.getAsJsonObject();
                        Transfer transfer = new Transfer(transferJo.get("line").getAsString(),
                                transferJo.get("station").getAsString());
                        station.addTransfer(transfer);
                    }
                } else if (stationTransfers.isJsonObject()) {
                    JsonObject transferJo = stationTransfers.getAsJsonObject();
                    Transfer transfer = new Transfer(transferJo.get("line").getAsString(),
                            transferJo.get("station").getAsString());
                    station.addTransfer(transfer);
                }

                metro.getStations().addLast(station);
                stationPosition++;
            }

            metros.put(entry, metro);
        }
    }

}
