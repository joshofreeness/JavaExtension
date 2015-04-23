public class Test3 extends ExtendMe2 {

    public void foo(boolean x) {
    }

    public void bar() {
        int b = 456;
    }
}

class ExtendMe1 extends Test {
}

class ExtendMe2 extends ExtendMe1 {
}

class Test {
}
