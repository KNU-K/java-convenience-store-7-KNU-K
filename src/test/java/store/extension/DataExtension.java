package store.extension;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import store.common.initializer.DataInitializer;

public class DataExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DataInitializer dataInitializer= new DataInitializer();
        dataInitializer.initializeData();
    }
}
