package Experiiments;

import com.gibran.MaximumFlow.FileRead;
import com.gibran.MaximumFlow.Graph;

import java.io.FileNotFoundException;
import java.util.*;

public class GlobalMembers {

    //This is the adjacency matrix#include<iostream>
    private static final int maxn = 24;
    private static final int inf = 0x3f3f3f;
    private static int N;
    private static int[] depth = new int[maxn];
    private static int[][] a;
    private static boolean bfs(int s,int e) //Search for depth
    {
        LinkedList<Integer> q = new LinkedList<Integer>();
//C++ TO JAVA CONVERTER TODO TASK: The memory management function 'memset' has no equivalent in Java:
//        memset(depth,-1,(Integer.SIZE / Byte.SIZE)); //Initial -1
        Arrays.fill(depth, -1);

        depth[s] = 0;
        q.offer(s);
        while (!q.isEmpty())
        {
            int now = q.peek();
            q.poll();
            for (int i = 1;i <= N;i++)
            {
                if (depth[i] == -1 && a[now][i] != 0) //There is a way at two points and has not been searched
                {
                    depth[i] = depth[now] + 1;
                    q.offer(i);
                }
            }
        }
        return depth[e] > 0;
    }
    private static int dfs(int now,int e,int nowflow)
    {
        if (now == N)
        {
            return nowflow; //If the search reaches the end
        }

        int findflow = 0;
        for (int i = 1;i <= N;i++)
        {
            if (a[now][i] != 0 && depth[i] == depth[now] + 1) //There is a way, and the next node
            {
                findflow = dfs(i, e, Math.min(nowflow,a[now][i])); //Continue dfs
                if (findflow != 0)
                {
                    a[now][i] -= findflow;
                    a[i][now] += findflow;
                    return findflow;
                }
            }
        }
        if (findflow == 0)
        {
            depth[now] = -2; //Explosion point optimization
        }
        return findflow;
    }
    private static int dinic(int s,int e)
    {
        int maxflow = 0;
        while (bfs(s, e))
        {
            maxflow += dfs(s, e, 1 << 20);
        }

        return maxflow;
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileRead fr  =  new FileRead();
        Graph graph = fr.graphReader("ladder_3.txt");
        a = graph.getAdjacencyMatrix();
        int dincin = dinic(0, 23);
        System.out.println();
        System.out.println(dincin);



    }

}