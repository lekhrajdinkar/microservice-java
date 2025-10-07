module module1
{
    // ✔️Requires
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;

    // ✔️Exports
    exports server; // expose service API // ◀️

    // ✔️Opens
    // Open packages containing beans
    opens server to spring.core, spring.beans;  // allow reflection (Spring needs it)
    //opens server // open to everything, not recommended in prod
    // Once you opens the correct packages, Spring will be able to generate proxies and the IllegalAccessException will disappear
}