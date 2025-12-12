package Day9;

public class Array_FuelEfficientRouteDetection {
    static class Result {
        int start, end, cost;
        Result(int s, int e, int c) {
            start = s; end = e; cost = c;
        }
    }

    public static Result bestRoute(int[] cost, int k) {
        int n = cost.length;

        int[] prefix = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + cost[i - 1];
        }

        int bestStart = 0;
        int bestEnd = k - 1;
        int bestCost = prefix[k] - prefix[0];

        int minPrefixValue = prefix[0];
        int minPrefixIndex = 0;

        for (int r = k; r <= n; r++) {

            int curCost = prefix[r] - minPrefixValue;
            int curStart = minPrefixIndex;
            int curEnd = r - 1;

            if (curCost < bestCost ||
                (curCost == bestCost && (curEnd - curStart) < (bestEnd - bestStart)) ||
                (curCost == bestCost && (curEnd - curStart) == (bestEnd - bestStart) && curStart < bestStart)) {

                bestCost = curCost;
                bestStart = curStart;
                bestEnd = curEnd;
            }

            int allowedIndex = r - k + 1;
            if (prefix[allowedIndex] < minPrefixValue) {
                minPrefixValue = prefix[allowedIndex];
                minPrefixIndex = allowedIndex;
            }
        }

        return new Result(bestStart, bestEnd, bestCost);
    }

    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 7, 3, 6};
        int k = 2;
        Result r = bestRoute(arr, k);

        System.out.println("Start Index: " + r.start);
        System.out.println("End Index: " + r.end);
        System.out.println("Minimum Total Cost: " + r.cost);
    }
}
