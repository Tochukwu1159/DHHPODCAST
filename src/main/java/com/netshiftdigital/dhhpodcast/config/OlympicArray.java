package com.netshiftdigital.dhhpodcast.config;

public class OlympicArray {

    public static int isStepped(int[] arr) {
        if (arr.length < 3) {
            return 0;
        }

        int prevValue = arr[0];
        int count = 1;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < prevValue) {
                return 0;
            } else if (arr[i] == prevValue) {
                count++;
            } else {
                if (count < 3) {
                    return 0;
                }
                prevValue = arr[i];
                count = 1;
            }
        }
        return count >= 3 ? 1 : 0;
    }


    public static int isOnionArray(int[] arr) {
        if (arr.length < 2) {
            return 1;
        }

        int n = arr.length;

        for (int i = 0, j = n - 1; i < j; i++, j--) {
            if (arr[i] + arr[j] > 10) {
                return 0;
            }
        }

        return 1;
    }






    public static int isOlympic(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        for (int i = 0; i < arr.length; i++) {
            int sumLessThan = 0;
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    sumLessThan += arr[j];
                }
            }
            if (arr[i] < sumLessThan) {
                return 0;
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        int[] arr1 = {3, 2, 1};
        int[] arr2 = {2, 2, 1, 1};
        int[] arr3 = {1, 1000, 100, 10000, 2};
        int[] arr4 = {1, 99, 99, 1000, 100, 10000, 2};
        int[] arr5 = {1, 2, 1, 3, 2};
        int[] arr6 = {1, 2, -1, 2, 2};

        System.out.println(isOlympic(arr1)); // Output: 1
        System.out.println(isOlympic(arr2)); // Output: 1
        System.out.println(isOlympic(arr3)); // Output: 1
        System.out.println(isOlympic(arr4)); // Output: 0
        System.out.println(isOlympic(arr5)); // Output: 0
        System.out.println(isOlympic(arr6)); // Output: 0
    }
}

//1 An array is defined to be stepped if it is in ascending order and there are 3 or more occurrences of each distinct value in the array. Note that ascending order means a[n]<=a[n+1]. It does not mean a[n] (this is strictly ascending). Write a function named isStepped that returns 1 if its array argument is stepped, otherwise return 0.
//
//        If you are programming in Java or C#, the signature is
//        int isStepped(int[ ] a)
//
//        If you are programming in C or C++, the signature is
//        int isStepped(int a[ ], int len) where len is the number of elements in the array.
//
//        Examples
//
//        If the array is	return	reason
//        {1, 1, 1, 5, 5, 5, 5, 8, 8, 8}	1	It is in ascending order. The distinct values of the array are 1, 5, 8 and there are three or more occurrences of each of these values.
//        {1, 1, 5, 5, 5, 5, 8, 8, 8}	0	Even though it is in ascending order, there are only two occurrences of the value 1.
//        {5, 5, 5, 15}	0	Even though it is in ascending order, there is only one occurrence of the value 15.
//        {3, 3, 3, 2, 2, 2, 5, 5, 5}	0	It is not in ascending order
//        {3, 3, 3, 2, 2, 2, 1, 1, 1}	0	It is not in ascending order
//        {1, 1, 1}	1	It is in ascending order and there are three or more occurrences of each distinct value. In this case there is only one distinct value.
//        {1, 1, 1, 1, 1, 1, 1}	1	It is in ascending order and there are three or more occurrences of each distinct value. In this case there is only one distinct value.
//
//
//        2. An onion array is an array that satisfies the following condition for all values of j and k:
//        if j>=0 and k>=0 and j+k+1=length of array and j!=k then a[j]+a[k] <= 10
//
//        Write a function named isOnionArray that returns 1 if its array argument is an onion array and returns 0 if it is not.
//
//        Your solution must not use a nested loop (i.e., a loop executed from inside another loop). Furthermore, once you determine that the array is not an onion array your function must return 0; no wasted loops cycles please!
//
//        If you are programming in Java or C#, the function signature is
//        int isOnionArray(int[ ] a)
//
//        If you are programming in C or C++, the function signature is
//        int isOnionArray(int a[ ], int len) where len is the number of elements in the array a.
//
//        Examples
//
//        a is	then function returns	reason
//        {1, 2, 19, 4, 5}	1	because 1+5 <= 10, 2+4 <=10
//        {1, 2, 3, 4, 15}	0	because 1+15 > 10
//        {1, 3, 9, 8}	0	because 3+9 > 10
//        {2}	1	because there is no j, k where a[j]+a[k] > 10 and j+k=length of array and j!=k
//        {}	1	because there is no j, k where a[j]+a[k] > 10 and j+k=length of array and j!=k
//        {-2, 5, 0, 5, 12}	1	because -2+12 <= 10 and 5+5 <= 10
//
//        3. An Olympic array is defined to be an array in which every value is greater than or equal to the sum of the values less than it. The sum of the values less than the minimum value in the array is defined to be 0.
//
//        For example, {3, 2, 1} is an Olympic array because
//
//        1 is the minimum value and by definition the sum of the values less than it is 0. Since 1 is greater than 0, it satisfies the condition.
//        There is only one value less than 2 and 2 is greater than it, so the value 2 satisfies the condition.
//        The values 1 and 2 are less than 3 and 3 is equal to their sum, so the value 3 satisfies the condition.
//        Hence all elements of the array satisfy the conditions and the array is an Olympic array.
//
//        {2, 2, 1, 1} is also an Olympic array because the values less than 2 sum to 2.
//
//        {1, 1000, 100, 10000, 2} is also an Olympic array. However, {1, 99, 99, 1000, 100, 10000, 2} is not an Olympic array because the sum of the numbers less than 100 (99+99+1) is greater than 100. Please be sure that your function detects that this is not an Olympic array!
//
//        {1, 2, 1, 3, 2} is not an Olympic array because 3 is not greater than or equal to 1+2+1+2.
//
//        {1, 2, -1, 2, 2} is not an Olympic array because -1 is the minimum value but it is not greater than or equal to 0.
//
//        Write a function named isOlympic that returns 1 if its array argument is an Olympic array, otherwise it returns 0.
//
//        If you are writing in Java or C#, the function signature is
//        int isOlympic (int[ ] a)
//
//        If you are writing in C or C++, the function signature is
//        int isOlympic(int a[ ], int len) where len is the number of elements in the array.
//
//        Hint: use a nested loop.
//
//        Copy and paste your answer here and click the "Submit answer" button