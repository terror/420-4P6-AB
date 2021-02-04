import java.util.ArrayList;

class test {
    public static void main(String[] args) {
        int n = 10;

        int[] arr = new int[n];
        ArrayList<int> x = new ArrayList<int>();
        for(int i = 0; i < n; ++i) 
            arr[i] = 10;

        for(int i = 1; i < n; ++i) 
            arr[i] += arr[i-1];

        int ans = 0;
        for(int i = 0; i < n; ++i) 
            ans += arr[i];

        System.out.println(ans);
    }
}
