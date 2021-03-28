package Experiiments;

import java.util.*;

public class Test5 {
    private int[][] w = new int[205][205];
    private int[] deep = new int[205];
    private int n;
    private int m;
    private int ans = 0;
    private LinkedList<Integer> q = new LinkedList<Integer>();
    private boolean DinicBfs()
    {
        while (!q.isEmpty())
        {
            q.poll();
        }
//C++ TO JAVA CONVERTER TODO TASK: The memory management function 'memset' has no equivalent in Java:
//        memset(deep,0,(Integer.SIZE / Byte.SIZE));
        Arrays.fill(deep, 0);
        q.offer(1);
        deep[1] = 1;
        while (!q.isEmpty())
        {
            int root = q.peek();
            q.poll();
            for (int i = 1 ; i <= n ; i++)
            {
                if (w[root][i] > 0 && deep[i] == 0)
                {
                    deep[i] = deep[root] + 1;
                    q.offer(i);
                }
            }
        }
        return deep[n] != 0;

    }
    private int DinicDfs(int root, int flow)
    {
        if (root == n)
        {
            return flow;
        }
        int newflow = 0;
        for (int i = 1 ; i <= n ; i++)
        {
            if ((w[root][i] > 0) && ((deep[root] + 1) == deep[i]) && (newflow == DinicDfs(i, Math.min(flow,w[root][i])))) //newflow Cannot be 0, 0 means there is no augmenting road in the current hierarchical network, and it is less than the end point
            {
                w[root][i] -= newflow;
                w[i][root] += newflow;
                return newflow;
            }
        }
        return 0;
    }
    private void Dinic()
    {
        while (DinicBfs())
        {
            ans += DinicDfs(1, Integer.MAX_VALUE);

        }
    }

    public static void main(String[] args) {

    }

}
