package jump.utils;

public class RandomString {

    //生成随机字符串
    public static String randomString(String alpha, int number) {
        StringBuilder format = new StringBuilder(alpha);
        for(int i = 0; i < number; i++){
            format.append((int)(Math.random()*10));
        }
        return format.toString();
    }

    public static void main(String[] args) {
        String string = randomString("CH", 7);
        System.out.println(string);
    }
}
