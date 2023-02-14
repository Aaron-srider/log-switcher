package fit.wenchao.logswithertest;

import fit.wenchao.logswithertest.test.TestClass2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestClass {
    public TestClass() {
        log.debug("construct TestClass");
        TestClass2 testClass2 = new TestClass2();
    }
}
