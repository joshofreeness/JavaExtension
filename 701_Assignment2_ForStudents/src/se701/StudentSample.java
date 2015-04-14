package se701;

public class StudentSample extends ExtendMe6 {

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

class ExtendMe3 extends ExtendMe2 {

    protected static int otherOtherParents = 5;
}

class ExtendMe4 extends ExtendMe3 {

    protected static int otherOtherParents = 5;
}

class ExtendMe5 extends ExtendMe4 {

    protected static int otherOtherParents = 5;
}

class ExtendMe6 extends ExtendMe5 {

    protected static int otherOtherParents = 5;
}

class ExtendMeAlso {

    protected static int grandparents = 5;
}

class ExtendMeSometimes {

    protected static int otherGrandparents = 7;
}
