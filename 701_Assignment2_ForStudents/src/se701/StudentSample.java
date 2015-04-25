package se701;

public class StudentSample extends ExtendMeSometimes implements testing {

    public ExtendMe1 hi;

    public static void main(String[] args) {
        System.out.println(parents + " " + otherParents + " " + grandparents + " " + otherGrandparents);
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

class ExtendMeSometimes extends ExtendMe2 {

    protected static int otherGrandparents = 7;
}

interface testing {
}
