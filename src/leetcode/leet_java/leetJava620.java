package leetcode.leet_java;/*
 * @author xulei
 * @date 2020-06-15 17:00
 * 概要：
 *     XXXXX
 *
 */

import java.util.Arrays;
import java.util.PriorityQueue;

public class leetJava620 {
   static int[][]  courses = {{100, 200}, {200, 1300}, {1000, 1250}, {2000, 3200}};

    public static void main(String[] args){
        System.out.println(scheduleCourse(courses));
    }

    public static int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b)->a[1]-b[1]);
        System.out.println(Arrays.deepToString(courses));
        PriorityQueue<Integer> priorityQueue =  new PriorityQueue<>((a,b)->(b-a));
        int time =0;
        for (int[] c : courses){
            if (time+c[0]<=c[1]){
                priorityQueue.add(c[0]);
                time += c[0];
            }else if(!priorityQueue.isEmpty() && priorityQueue.peek()>c[0]){
                priorityQueue.add(c[0]);
                time += c[0]-priorityQueue.poll();
            }
        }
        return priorityQueue.size();
    }
}
