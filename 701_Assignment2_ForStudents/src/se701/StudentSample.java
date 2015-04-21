package se701;

public class StudentSample extends ExtendMe2 {

    public ExtendMe1 hi;

    public static void main(String[] args) {
        System.out.println(parents + " " + otherParents);
    }
}

class ExtendMe1 extends ExtendMeAlso {

    protected static int otherParents = 1;
}

class ExtendMe2 extends ExtendMe1 {

    protected static int parents = 2;
}

class ExtendMeAlso {

    protected static int grandparents = 5;
}

class ExtendMeSometimes {

    protected static int otherGrandparents = 7;
}
