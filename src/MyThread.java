public class MyThread {

    String text;

    public MyThread(String text) {
        this.text = text;
    }

    public String cutSpace() {
        System.out.println(" Получено:\n" + text);
        String[] before = text.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : before) {
            sb.append(s);
        }
        return sb.toString();
    }
}

