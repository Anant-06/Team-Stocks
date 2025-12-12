import java.util.*;

class Solution {
    public int[] countMentions(int numberOfUsers, List<List<String>> events) {
        // Event holder
        class Ev { String type; int t; String payload; Ev(String type, int t, String p){this.type=type;this.t=t;this.payload=p;} }
        // group events by timestamp (sorted)
        TreeMap<Integer, List<Ev>> byTime = new TreeMap<>();
        for (List<String> e : events) {
            String type = e.get(0);
            int t = Integer.parseInt(e.get(1));
            String p = e.get(2);
            byTime.computeIfAbsent(t, k->new ArrayList<>()).add(new Ev(type,t,p));
        }

        int[] mentions = new int[numberOfUsers];
        boolean[] online = new boolean[numberOfUsers];
        Arrays.fill(online, true);
        PriorityQueue<int[]> reactPQ = new PriorityQueue<>(Comparator.comparingInt(a->a[0])); // [reactTime, uid]
        int allCount = 0;

        for (int time : byTime.keySet()) {
            // 1) reactivate users whose offline period ended at or before 'time'
            while (!reactPQ.isEmpty() && reactPQ.peek()[0] <= time) {
                online[reactPQ.poll()[1]] = true;
            }
            List<Ev> group = byTime.get(time);

            // 2) process OFFLINE events first
            for (Ev ev : group) {
                if (ev.type.equals("OFFLINE")) {
                    int uid = Integer.parseInt(ev.payload);
                    if (uid >= 0 && uid < numberOfUsers) {
                        online[uid] = false;
                        reactPQ.offer(new int[]{time + 60, uid});
                    }
                }
            }

            // 3) then process MESSAGE events
            for (Ev ev : group) {
                if (!ev.type.equals("MESSAGE")) continue;
                String payload = ev.payload.trim();
                if (payload.equals("ALL")) {
                    allCount++;
                } else {
                    String[] tokens = payload.split("\\s+");
                    for (String tok : tokens) {
                        if (tok.isEmpty()) continue;
                        if (tok.equals("ALL")) {
                            allCount++;
                        } else if (tok.equals("HERE")) {
                            for (int u = 0; u < numberOfUsers; u++) if (online[u]) mentions[u]++;
                        } else if (tok.startsWith("id")) {
                            try {
                                int uid = Integer.parseInt(tok.substring(2));
                                if (uid >= 0 && uid < numberOfUsers) mentions[uid]++;
                            } catch (NumberFormatException ignored) {}
                        }
                    }
                }
            }
        }

        // add ALL mentions to every user
        for (int i = 0; i < numberOfUsers; i++) mentions[i] += allCount;
        return mentions;
    }
}
