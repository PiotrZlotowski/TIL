package junit.extension;

import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class ApacheMqExtension implements BeforeAllCallback, AfterAllCallback  {


    private static final Namespace NAMESPACE = Namespace.create(ApacheMqExtension.class);
    private BrokerService brokerService;


    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        brokerService.stop();
        brokerService.waitUntilStopped();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.getManagementContext().setCreateConnector(false);
        brokerService.setUseShutdownHook(true);
        brokerService.setPersistent(false);
//        brokerService.setBrokerName("embedded-broker");
        brokerService.start();
        brokerService.waitUntilStarted();

    }
}
