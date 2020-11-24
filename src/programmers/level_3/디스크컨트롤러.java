package programmers.level_3;

import java.util.*;

import static java.util.Comparator.*;

/**
 * lv3
 * 종류 : #힙, #다시풀기(완)
 * 시작일시 : 2020-11-09 20:17
 * 성공/실패 : 실패
 * 소요시간 :
 * 실패 사유
 * - 우선순위 큐는, 정렬 기준을 세워야 하는데 세우지 못함.
 * - 다시 생각해보면 간단하다. 요청~처리 까지 걸리는 시간의 평균이 낮아야 하니까, 요청부터 처리까지 걸리는 시간이 가장 최소인것부터 처리하면 된다.
 */
public class 디스크컨트롤러 {

    public static void main(String[] args) {

        int n = new 디스크컨트롤러().solution(new int[][]{
                {0, 3}, {1, 9}, {2, 6}
        });

        System.out.println(n);
    }


    public int solution(int[][] jobs) {

        int answer = 0;
        int end = 0; // 수행되고난 직후의 시간
        int jobsIdx = 0; // jobs 배열의 인덱스
        int count = 0; // 수행된 요청 갯수

        // 원본 배열 오름차순 정렬 (요청시간 오름차순)
        Arrays.sort(jobs, comparingInt(o -> o[0]));

        // 처리 시간 오름차순으로 정렬되는 우선순위 큐(Heap)
        PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(o -> o[1]));

        // 요청이 모두 수행될 때까지 반복
        while (count < jobs.length) {

            // 하나의 작업이 완료되는 시점(end)까지 들어온 모든 요청을 큐에 넣음
            while (jobsIdx < jobs.length && jobs[jobsIdx][0] <= end) {
                pq.add(jobs[jobsIdx++]);
            }

            // 큐가 비어있다면 작업 완료(end) 이후에 다시 요청이 들어온다는 의미
            // (end를 요청의 가장 처음으로 맞춰줌)
            if (pq.isEmpty()) {
                end = jobs[jobsIdx][0];

                // 작업이 끝나기 전(end 이전) 들어온 요청 중 가장 수행시간이 짧은 요청부터 수행
            } else {

                int[] temp = pq.poll();
                answer += temp[1] + end - temp[0];
                end += temp[1];
                count++;
            }
        }

        return (int) Math.floor(answer / jobs.length);
    }
}
