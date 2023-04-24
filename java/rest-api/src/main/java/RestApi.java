import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class RestApi {
    private final List<User> users = new ArrayList<>();

    public RestApi(User... users) {
        this.users.addAll(Arrays.asList(users));
    }

    public String get(String url) {
        JSONArray ja = new JSONArray();
        users.sort(Comparator.comparing(User::name));
        users.stream().map(this::userToJson).forEach(ja::put);
        JSONObject jo = new JSONObject();
        jo.put(url.substring(1), ja);
        return jo.toString();
    }

    public String get(String url, JSONObject payload) {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        JSONArray payloadArray = (JSONArray) payload.get(url.substring(1));
        payloadArray.forEach(o -> ja.put(userToJson(getUserByName((String) o))));
        jo.put("users", ja);
        return jo.toString();
    }

    public String post(String url, JSONObject payload) {
        switch (url) {
            case "/add": {
                return addUser(payload);
            }
            case "/iou": {
                return addIou(payload);
            }
            default: {
                throw new IllegalArgumentException("Unknown endpoint!");
            }
        }
    }

    private String addIou(JSONObject payload) {
        User lender = getUserByName(payload.get("lender").toString());
        User borrower = getUserByName(payload.get("borrower").toString());

        double amount = (double) payload.get("amount");
        double balance = getBalance(lender, borrower) + amount;

        lender = updateWithNewBalance(lender, borrower, balance);
        borrower = updateWithNewBalance(borrower, lender, -balance);

        JSONObject jo = new JSONObject();
        List<JSONObject> list = Arrays.asList(userToJson(lender), userToJson(borrower));
        list.sort(Comparator.comparing(o -> o.get("name").toString()));
        JSONArray ja = new JSONArray(list);
        jo.put("users", ja);
        return jo.toString();
    }

    private User updateWithNewBalance(User user1, User user2, double balance) {
        User.Builder builder = User.builder();
        user1.owes().stream()
                .filter(iou -> !iou.name.equals(user2.name()))
                .forEach(iou -> builder.owes(iou.name, iou.amount));
        user1.owedBy().stream()
                .filter(iou -> !iou.name.equals(user2.name()))
                .forEach(iou -> builder.owedBy(iou.name, iou.amount));
        if (balance > 0) {
            builder.owedBy(user2.name(), balance);
        }
        if (balance < 0) {
            builder.owes(user2.name(), -balance);
        }
        return builder.setName(user1.name()).build();
    }

    // balance of mutual settlements
    private double getBalance(User user1, User user2) {
        Optional<Iou> iouOptional = user1.owes().stream()
                .filter(iou -> iou.name.equals(user2.name()))
                .findFirst();
        if (iouOptional.isPresent()) {
            return -iouOptional.get().amount;
        }
        iouOptional = user2.owes().stream()
                .filter(iou -> iou.name.equals(user1.name()))
                .findFirst();
        if (iouOptional.isPresent()) {
            return iouOptional.get().amount;
        }
        return 0;
    }

    private String addUser(JSONObject payload) {
        User user = User.builder().setName(payload.opt("user").toString()).build();
        return userToJson(user).toString();
    }

    private double getBalance(User user) {
        return user.owedBy().stream().mapToDouble(value -> value.amount).sum() -
                user.owes().stream().mapToDouble(value -> value.amount).sum();
    }

    private JSONObject userToJson(User user) {
        JSONObject jo = new JSONObject();
        jo.put("name", user.name());

        JSONObject owes = new JSONObject();
        user.owes().forEach(iou -> owes.put(iou.name, iou.amount));
        jo.put("owes", owes);

        JSONObject owedBy = new JSONObject();
        user.owedBy().forEach(iou -> owedBy.put(iou.name, iou.amount));
        jo.put("owedBy", owedBy);

        jo.put("balance", getBalance(user));
        return jo;
    }

    private User getUserByName(String name) {
        return users.stream()
                .filter(user -> user.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can not find user with name " + name));
    }
}