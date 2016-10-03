package agency.tango.databindingrxjava;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class MainViewModelTests {

    @Test
    public void mainViewModel_firstName_lastName_helloButtonEnabledSetToTrue() throws Exception {
        MainActivity.MainViewModel mainViewModel = new MainActivity.MainViewModel();

        assertFalse(mainViewModel.helloButtonEnabled.get());

        mainViewModel.firstName.set("a");
        mainViewModel.lastName.set("b");

        assertTrue(mainViewModel.helloButtonEnabled.get());
    }

    @Test
    public void mainViewModel_firstName_lastNameEmpty_helloButtonEnabledSetToFalse() throws Exception {
        MainActivity.MainViewModel mainViewModel = new MainActivity.MainViewModel();

        mainViewModel.firstName.set("a");
        mainViewModel.lastName.set("");

        assertFalse(mainViewModel.helloButtonEnabled.get());

    }
}