package ui;

import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Set;

public class CustomExpectedConditions {
    public static ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return driver -> {
            assert driver != null;
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(oldWindows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }
}
