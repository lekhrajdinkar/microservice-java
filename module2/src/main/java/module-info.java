module module2
{
    // require
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;

    requires module1; // ◀️

    //opens
    opens client; //to spring.core, spring.beans, spring.aop;
    //opens client to spring.core, spring.beans, spring.aop;

}